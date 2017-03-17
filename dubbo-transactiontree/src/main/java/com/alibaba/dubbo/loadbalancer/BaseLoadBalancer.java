package com.alibaba.dubbo.loadbalancer;

import static java.util.Collections.singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.loadbalancer.rule.RoundRobinRule;
import com.alibaba.dubbo.util.ShutdownEnabledTimer;

public class BaseLoadBalancer extends AbstractLoadBalancer implements ClientConfigAware {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected String name = "default";

	protected Rule rule = null;

	protected Ping ping = null;
	protected Timer pingTimer = null;
	protected int pingIntervalSeconds = 10;
	protected int maxTotalPingTimeSeconds = 5;
	protected AtomicBoolean pingInProgress = new AtomicBoolean(false);

	protected LoadBalancerStats loadBalancerStat = null;
	
	protected ClientConfig clientConfig = null;

	protected volatile List<Server> allServerList = new ArrayList<Server>();
	protected volatile List<Server> upServerList = new ArrayList<Server>();
	protected ReadWriteLock allServerLock = new ReentrantReadWriteLock();
	protected ReadWriteLock upServerLock = new ReentrantReadWriteLock();

	private List<ServerListChangeListener> changeListeners = new CopyOnWriteArrayList<ServerListChangeListener>();
	private List<ServerStatusChangeListener> serverStatusListeners = new CopyOnWriteArrayList<ServerStatusChangeListener>();
	
	public BaseLoadBalancer() {
		setRule(rule);
		this.loadBalancerStat = new LoadBalancerStats(name);
		setupPingTask();
	}

	public BaseLoadBalancer(String name, Rule rule, Ping ping, LoadBalancerStats loadBalancerStat) {
		this.name = name;
		setRule(rule);
		setPing(ping);
		this.loadBalancerStat = loadBalancerStat;
		setupPingTask();
		init();
	}
	
	public void init() {
		
	}

	public void addServer(Server newServer) {
		if (newServer != null) {
			try {
				List<Server> newList = new ArrayList<Server>();
				
				Lock allLock = allServerLock.readLock();
				try {
					allLock.lock();
					newList.addAll(allServerList);
				} finally {
					allLock.unlock();
				}
				
				newList.add(newServer);
				setServersList(newList);
			} catch (Exception e) {
				logger.error("Exception while adding a newServer", e);
			}
		}
	}

	@Override
	public void addServers(List<Server> newServers) {
		if (newServers != null && newServers.size() > 0) {
			try {
				List<Server> newList = new ArrayList<Server>();
				
				Lock allLock = allServerLock.readLock();
				try {
					allLock.lock();
					newList.addAll(allServerList);
				} finally {
					allLock.unlock();
				}
				
				newList.addAll(newServers);
				setServersList(newList);
			} catch (Exception e) {
				logger.error("Exception while adding Servers", e);
			}
		}
	}

	public void setServersList(List lsrv) {
		Collections.shuffle(lsrv);
		
		Lock writeLock = allServerLock.writeLock();
		if (logger.isDebugEnabled()) {
			logger.debug("LoadBalancer:  clearing server list (SET op)");
		}
		
		List<Server> newUpServerList = null;
		boolean listChanged = false;
		writeLock.lock();
		try {
			List<Server> allServers = new ArrayList<Server>();
			for (Object server : lsrv) {
				if (server == null) {
					continue;
				}

				if (server instanceof String) {
					server = new Server((String) server);
				}

				if (server instanceof Server) {
					if (logger.isDebugEnabled()) {
						logger.debug("LoadBalancer:  addServer [" + ((Server) server).getId() + "]");
					}
					allServers.add((Server) server);
				} else {
					throw new IllegalArgumentException(
							"Type String or Server expected, instead found:" + server.getClass());
				}
			}
			
			if (!allServerList.equals(allServers)) {
				listChanged = true;
				if (changeListeners != null && changeListeners.size() > 0) {
					List<Server> oldList = new ArrayList<Server>(allServerList);
					List<Server> newList = new ArrayList<Server>(allServers);
					for (ServerListChangeListener l : changeListeners) {
						try {
							l.serverListChanged(oldList, newList);
						} catch (Throwable e) {
							logger.error("Error invoking server list change listener", e);
						}
					}
				}
			}

			allServerList = allServers;
			if (canSkipPing()) {
				for (Server s : allServerList) {
					s.setAlive(true);
				}
				newUpServerList = allServerList;
			}
		} finally {
			writeLock.unlock();
		}
		
		if (newUpServerList != null) {
			Lock unLock = upServerLock.writeLock();
			try {
				unLock.lock();
				upServerList = newUpServerList;
			} finally {
				unLock.unlock();
			}
		} else if (listChanged) {
			forceQuickPing();
		}
	}

	public void setServers(String srvString) {
		if (srvString != null) {

			try {
				String[] serverArr = srvString.split(",");
				ArrayList<Server> newList = new ArrayList<Server>();

				for (String serverString : serverArr) {
					if (serverString != null) {
						serverString = serverString.trim();
						if (serverString.length() > 0) {
							Server svr = new Server(serverString);
							newList.add(svr);
						}
					}
				}
				setServersList(newList);
			} catch (Exception e) {
				logger.error("Exception while adding Servers", e);
			}
		}
	}

	public Server getServerByIndex(int index, boolean availableOnly) {
		try {
			return (availableOnly ? upServerList.get(index) : allServerList.get(index));
		} catch (Exception e) {
			return null;
		}
	}

	public int getServerCount(boolean onlyAvailable) {
		if (onlyAvailable) {
			return upServerList.size();
		} else {
			return allServerList.size();
		}
	}

	@Override
	public Server chooseServer(Object key) {
		if (rule == null) {
			return null;
		} else {
			try {
				return rule.choose(key);
			} catch (Throwable t) {
				return null;
			}
		}
	}

	public String choose(Object key) {
		if (rule == null) {
			return null;
		} else {
			try {
				Server svr = rule.choose(key);
				return ((svr == null) ? null : svr.getId());
			} catch (Throwable t) {
				return null;
			}
		}
	}

	@Override
	public void markServerDown(Server server) {
		if (server == null) {
			return;
		}

		if (!server.isAlive()) {
			return;
		}

		logger.error("LoadBalancer:  markServerDown called on [" + server.getId() + "]");
		server.setAlive(false);
		// forceQuickPing();

		notifyServerStatusChangeListener(singleton(server));
	}

	public void markServerDown(String id) {
		boolean triggered = false;

		id = Server.normalizeId(id);
		if (id == null) {
			return;
		}

		Lock writeLock = upServerLock.writeLock();
		try {
			writeLock.lock();
			
			final List<Server> changedServers = new ArrayList<Server>();

			for (Server svr : upServerList) {
				if (svr.isAlive() && (svr.getId().equals(id))) {
					triggered = true;
					svr.setAlive(false);
					changedServers.add(svr);
				}
			}

			if (triggered) {
				logger.error("LoadBalancer:  markServerDown called on [" + id + "]");
				notifyServerStatusChangeListener(changedServers);
			}
		} finally {
			try {
				writeLock.unlock();
			} catch (Exception e) { // NOPMD
			}
		}
	}

	@Override
	public List<Server> getServerList(boolean availableOnly) {
		return (availableOnly ? Collections.unmodifiableList(upServerList)
				: Collections.unmodifiableList(allServerList));
	}

	@Override
	public LoadBalancerStats getLoadBalancerStats() {
		return this.loadBalancerStat;
	}
	
	@Override
	public void setClientConfig(ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	public void addServerListChangeListener(ServerListChangeListener listener) {
		changeListeners.add(listener);
	}

	public void removeServerListChangeListener(ServerListChangeListener listener) {
		changeListeners.remove(listener);
	}

	public void addServerStatusChangeListener(ServerStatusChangeListener listener) {
		serverStatusListeners.add(listener);
	}

	public void removeServerStatusChangeListener(ServerStatusChangeListener listener) {
		serverStatusListeners.remove(listener);
	}

	public void shutdown() {
		cancelPingTask();
	}

	private boolean canSkipPing() {
		if (ping == null) {
			return true;
		} else {
			return false;
		}
	}

	private void setupPingTask() {
		if (canSkipPing()) {
			return;
		}
		if (pingTimer != null) {
			pingTimer.cancel();
		}
		pingTimer = new ShutdownEnabledTimer("NFLoadBalancer-PingTimer-" + name, true);
		pingTimer.schedule(new PingTask(), 0, pingIntervalSeconds * 1000);
		forceQuickPing();
	}

	public void forceQuickPing() {
		if (canSkipPing()) {
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("LoadBalancer:  forceQuickPing invoked");
		}
		Pinger ping = new Pinger();
		try {
			ping.runPinger();
		} catch (Throwable t) {
			logger.error("Throwable caught while running forceQuickPing() for " + name, t);
		}
	}

	public void cancelPingTask() {
		if (pingTimer != null) {
			pingTimer.cancel();
		}
	}

	public Lock lockAllServerList(boolean write) {
		Lock aproposLock = write ? allServerLock.writeLock() : allServerLock.readLock();
		aproposLock.lock();
		return aproposLock;
	}

	public Lock lockUpServerList(boolean write) {
		Lock aproposLock = write ? upServerLock.writeLock() : upServerLock.readLock();
		aproposLock.lock();
		return aproposLock;
	}

	private void notifyServerStatusChangeListener(final Collection<Server> changedServers) {
		if (changedServers != null && !changedServers.isEmpty() && !serverStatusListeners.isEmpty()) {
			for (ServerStatusChangeListener listener : serverStatusListeners) {
				try {
					listener.serverStatusChanged(changedServers);
				} catch (Throwable e) {
					logger.error("Error invoking server status change listener", e);
				}
			}
		}
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		if (rule != null) {
			this.rule = rule;
		} else {
			this.rule = new RoundRobinRule();
		}
		if (this.rule.getLoadBalancer() != this) {
			this.rule.setLoadBalancer(this);
		}
	}

	public Ping getPing() {
		return ping;
	}

	public void setPing(Ping ping) {
		if (ping != null) {
			if (!ping.equals(this.ping)) {
				this.ping = ping;
				setupPingTask();
			}
			if (this.ping.getLoadBalancer() != this) {
				this.ping.setLoadBalancer(this);
			}
		} else {
			this.ping = null;
			pingTimer.cancel();
		}
	}

	public int getPingInterval() {
		return pingIntervalSeconds;
	}

	public void setPingInterval(int pingIntervalSeconds) {
		if (pingIntervalSeconds < 1) {
			return;
		}

		this.pingIntervalSeconds = pingIntervalSeconds;
		if (logger.isDebugEnabled()) {
			logger.debug("LoadBalancer:  pingIntervalSeconds set to " + this.pingIntervalSeconds);
		}

		setupPingTask();
	}

	public int getMaxTotalPingTime() {
		return maxTotalPingTimeSeconds;
	}

	public void setMaxTotalPingTime(int maxTotalPingTimeSeconds) {
		if (maxTotalPingTimeSeconds < 1) {
			return;
		}
		this.maxTotalPingTimeSeconds = maxTotalPingTimeSeconds;
		if (logger.isDebugEnabled()) {
			logger.debug("LoadBalancer: maxTotalPingTime set to " + this.maxTotalPingTimeSeconds);
		}
	}

	public AtomicBoolean getPingInProgress() {
		return pingInProgress;
	}

	public void setPingInProgress(AtomicBoolean pingInProgress) {
		this.pingInProgress = pingInProgress;
	}

	public void setLoadBalancerStats(LoadBalancerStats loadBalancerStat) {
		this.loadBalancerStat = loadBalancerStat;
	}

	class PingTask extends TimerTask {
		public void run() {
			Pinger ping = new Pinger();
			try {
				ping.runPinger();
			} catch (Throwable t) {
				logger.error("Throwable caught while running extends for " + name, t);
			}
		}
	}

	class Pinger {
		public void runPinger() {
			if (pingInProgress.get()) {
				return;
			} else {
				pingInProgress.set(true);
			}

			Object[] allServers = null;
			boolean[] results = null;

			Lock allLock = null;
			Lock upLock = null;

			try {
				allLock = allServerLock.readLock();
				allLock.lock();
				allServers = allServerList.toArray();
				allLock.unlock();

				int numCandidates = allServers.length;
				results = new boolean[numCandidates];

				if (logger.isDebugEnabled()) {
					logger.debug("LoadBalancer:  PingTask executing [" + numCandidates + "] servers configured");
				}

				for (int i = 0; i < numCandidates; i++) {
					results[i] = false;
					try {
						if (ping != null) {
							results[i] = ping.isAlive((Server) allServers[i]);
						}
					} catch (Throwable t) {
						logger.error("Exception while pinging Server:" + allServers[i], t);
					}
				}

				final List<Server> newUpList = new ArrayList<Server>();
				final List<Server> changedServers = new ArrayList<Server>();

				for (int i = 0; i < numCandidates; i++) {
					boolean isAlive = results[i];
					Server svr = (Server) allServers[i];
					boolean oldIsAlive = svr.isAlive();

					svr.setAlive(isAlive);

					if (oldIsAlive != isAlive) {
						changedServers.add(svr);
						if (logger.isDebugEnabled()) {
							logger.debug("LoadBalancer:  Server [" + svr.getId() + "] status changed to "
									+ (isAlive ? "ALIVE" : "DEAD"));
						}
					}

					if (isAlive) {
						newUpList.add(svr);
					}
				}
				upLock = upServerLock.writeLock();
				upLock.lock();
				upServerList = newUpList;
				upLock.unlock();

				notifyServerStatusChangeListener(changedServers);

			} catch (Throwable t) {
				logger.error("Throwable caught while running the Pinger-" + name, t);
			} finally {
				pingInProgress.set(false);
			}
		}
	}
}