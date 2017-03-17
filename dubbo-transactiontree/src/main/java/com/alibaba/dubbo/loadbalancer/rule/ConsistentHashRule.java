package com.alibaba.dubbo.loadbalancer.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.loadbalancer.AbstractLoadBalancerRule;
import com.alibaba.dubbo.loadbalancer.ClientConfig;
import com.alibaba.dubbo.loadbalancer.LoadBalancer;
import com.alibaba.dubbo.loadbalancer.Server;
import com.alibaba.dubbo.loadbalancer.ServerStatusChangeListener;
import com.alibaba.dubbo.util.KetamaNodeLocator;

public class ConsistentHashRule extends AbstractLoadBalancerRule {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsistentHashRule.class);
	
	private KetamaNodeLocator locator = new KetamaNodeLocator(Collections.EMPTY_LIST);
	private ReadWriteLock locatorLock = new ReentrantReadWriteLock();
	private ServerStatusChangeListener serverStatusChangeListener = new ServerStatusChangeListener() {
		@Override
		public void serverStatusChanged(Collection<Server> servers) {
			updateLocator(getLoadBalancer().getServerList(true));
		}
	};
	
	@Override
	public Server choose(LoadBalancer lb, Object key) {
		if (lb == null) {
			LOG.warn("no load balancer");
			return null;
		}
		if (key == null) {
			LOG.warn("no key");
			return null;
		}
		
		Server server = null;

		while (server == null) {
			if (Thread.interrupted()) {
				return null;
			}

			List<Server> upList = lb.getServerList(true);
			List<Server> allList = lb.getServerList(false);
			int upCount = upList.size();
			int serverCount = allList.size();

			if ((upCount == 0) || (serverCount == 0)) {
				LOG.warn("No up servers available from load balancer: " + lb);
				return null;
			}
			
			String serverId = null;
			Lock lock = locatorLock.readLock();
			try {
				lock.lock();
				String locatorKey = (key instanceof Server) ? ((Server) key).getId() : key.toString();
				serverId = locator.getPrimary(locatorKey);
			} catch (Exception e) {
				// NOP
			} finally {
				lock.unlock();
			}
			if (serverId == null) {
				return null;
			}

			try {
				for (Server upServer : upList) {
					if (serverId.equals(upServer.getId())) {
						server = upServer;
						break;
					}
				}
			} catch (Throwable t) {
				// NOP
			}

			if (server == null) {
				Thread.yield();
				continue;
			}

			if (server.isAlive()) {
				return (server);
			}

			server = null;
			Thread.yield();
		}

		return server;
	}
	
	@Override
	public void setLoadBalancer(LoadBalancer lb) {
		super.setLoadBalancer(lb);
		this.updateLocator(lb.getServerList(true));
	}
	
	@Override
	public void setClientConfig(ClientConfig clientConfig) {
		
	}
	
	private void updateLocator(Collection<Server> servers) {
		Lock lock = locatorLock.writeLock();
		try {
			lock.lock();
			locator.updateLocator(toNodes(servers));
		} finally {
			lock.unlock();
		}
	}
	
	private List<String> toNodes(Collection<Server> servers) {
		List<String> nodes = new ArrayList<String>();
		if (servers != null) {
			for (Server server : getLoadBalancer().getServerList(true)) {
				nodes.add(server.getId());
			}
		}
		return nodes;
	}

	public ServerStatusChangeListener getServerStatusChangeListener() {
		return serverStatusChangeListener;
	}
}