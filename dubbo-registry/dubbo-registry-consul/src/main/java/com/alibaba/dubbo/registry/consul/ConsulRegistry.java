package com.alibaba.dubbo.registry.consul;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import com.alibaba.dubbo.common.utils.NamedThreadFactory;
import com.alibaba.dubbo.common.utils.UrlUtils;
import com.alibaba.dubbo.registry.NotifyListener;
import com.alibaba.dubbo.registry.support.FailbackRegistry;
import com.alibaba.dubbo.rpc.RpcException;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.Member;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.health.model.HealthService;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by make on 2017/1/8.
 */
public class ConsulRegistry extends FailbackRegistry {

	private final static Logger logger = LoggerFactory.getLogger(ConsulRegistry.class);
	private final ConsulClient consulClient;
	private final long ttl;
	private static final int DEFAULT_CONSUL_PORT = 8500;
	private final static String DEFAULT_ROOT = "dubbo";
	private final String root;
	private static final String VERSION_TAG_PREFIX = "version: ";

	private final ScheduledFuture<?> keepAliveFuture;
	private final ScheduledExecutorService keepAliveExecutor = Executors.newScheduledThreadPool(1,
			new NamedThreadFactory("DubboConsulRegistryTimer", true));

	private final ConcurrentMap<String, ConsulNotifier> notifiers = new ConcurrentHashMap<String, ConsulNotifier>();

	public ConsulRegistry(URL url) {
		super(url);
		if (url.isAnyHost()) {
			throw new IllegalStateException("registry address == null");
		}
		String group = url.getParameter(Constants.GROUP_KEY, DEFAULT_ROOT);
		if (!group.startsWith(Constants.PATH_SEPARATOR)) {
			group = Constants.PATH_SEPARATOR + group;
		}
		if (!group.endsWith(Constants.PATH_SEPARATOR)) {
			group = group + Constants.PATH_SEPARATOR;
		}
		this.root = group;
		String host = url.getIp();
		int port = url.getPort() != 0 ? url.getPort() : DEFAULT_CONSUL_PORT;
		this.consulClient = new ConsulClient(host, port);
		this.ttl = 16000L;
		keepAliveFuture = keepAliveExecutor.scheduleWithFixedDelay(new Runnable() {
			public void run() {
				try {
					keepAlive(); // 确保服务在consul的status为passing
				} catch (Throwable t) { // 防御性容错
					logger.error("Unexpected exception occur at defer consul keep alive time, cause: " + t.getMessage(),
							t);
				}
			}
		}, ttl / 2, ttl / 2, TimeUnit.MILLISECONDS);
	}

	@Override
	public boolean isAvailable() {
		try {
			Response<List<Member>> servicesMap = consulClient.getAgentMembers();
			return true;
		} catch (OperationException e) {
			logger.info("Consul agent connect failed");
			return false;
		}
	}

	@Override
	protected void doRegister(URL url) {
		NewService newService = createServiceDef(url, this.ttl);
		try {
			this.consulClient.agentServiceRegister(newService);
		} catch (Exception e) {
			throw new RpcException(
					"Failed to register " + url + " to Consul " + getUrl() + ", cause: " + e.getMessage(), e);
		}
	}

	@Override
	protected void doUnregister(URL url) {
		try {
			this.consulClient.agentServiceDeregister(toCategoryPath(url));
		} catch (Exception e) {
			throw new RpcException(
					"Failed to unregister " + url + " to Consul " + getUrl() + ", cause: " + e.getMessage(), e);
		}
	}

	@Override
	protected void doSubscribe(URL url, NotifyListener listener) {
		String service = toServicePath(url);
		/*
		 * ConsulNotifier notifier = notifiers.get(service); if (notifier ==
		 * null) { ConsulNotifier newNotifier = new ConsulNotifier(service);
		 * notifiers.putIfAbsent(service, newNotifier); notifier =
		 * notifiers.get(service); if (notifier == newNotifier) {
		 * notifier.start(); } }
		 */
		Collection<NotifyListener> notifyListeners = new ConcurrentHashSet<NotifyListener>();
		notifyListeners.add(listener);
		doNotify(url, notifyListeners);
	}

	@Override
	protected void doUnsubscribe(URL url, NotifyListener listener) {

	}

	private void doNotify(URL url, Collection<NotifyListener> listeners) {
		List<URL> result = new ArrayList<URL>();
		String consumerService = url.getServiceInterface();
		try {
			Response<List<HealthService>> response = this.consulClient.getHealthServices(consumerService, true, QueryParams.DEFAULT);
			List<HealthService> healthServices = (List<HealthService>) response.getValue();
			Iterator<HealthService> iterator = healthServices.iterator();
			while (iterator.hasNext()) {
				HealthService healthService = (HealthService) iterator.next();
				HealthService.Service service = healthService.getService();
				List<URL> urls = new ArrayList<URL>();
				String serviceURL = URL.decode(service.getAddress());
				URL u = URL.valueOf(serviceURL);
				if (UrlUtils.isMatch(url, u)) {
					urls.add(u);
				}
				result.addAll(urls);
				if (logger.isInfoEnabled()) {
					logger.info("Consul notify:  = " + urls);
				}
			}
			if (result == null || result.size() == 0) {
				return;
			}
			for (NotifyListener listener : listeners) {
				notify(url, listener, result);
			}
		} catch (OperationException e) {
			throw e;
		}
	}

	private String toCategoryPath(URL url) {
		return toServicePath(url) + Constants.PATH_SEPARATOR
				+ url.getParameter(Constants.CATEGORY_KEY, Constants.DEFAULT_CATEGORY);
	}

	private String toServicePath(URL url) {
		return root + url.getServiceInterface();
	}

	private NewService createServiceDef(URL url, long ttl) {
		NewService newService = new NewService();
		newService.setId(toCategoryPath(url));
		newService.setName(url.getServiceInterface());
		newService.setAddress(URL.encode(url.toFullString()));
		newService.setPort(url.getPort());
		String version = makeVersionTag(url);
		newService.setTags(Collections.singletonList(version));
		int ttlInSeconds = (int) ttl / 1000;
		NewService.Check check = new NewService.Check();
		check.setTtl(ttlInSeconds + "s");
		newService.setCheck(check);
		return newService;
	}

	private void keepAlive() {
		for (URL url : new HashSet<URL>(getRegistered())) {
			if (url.getParameter(Constants.DYNAMIC_KEY, true)) {
				keepAlive(url);
			}
		}
	}

	public void keepAlive(URL url) {
		try {
			String checkId = getCheckId(toCategoryPath(url));
			this.consulClient.agentCheckPass(checkId);
			logger.info("keep alive success, serviceId: " + checkId);
		} catch (OperationException e) {
			throw e;
		}
	}

	private static String getCheckId(String serviceId) {
		return "service:" + serviceId;
	}

	private static String makeVersionTag(URL url) {
		String version = url.getParameter(Constants.VERSION_KEY, "1.0");
		return VERSION_TAG_PREFIX + version;
	}

	private class ConsulNotifier extends Thread {
		private String service;
		private volatile boolean running = true;

		public ConsulNotifier(String service) {
			super.setDaemon(true);
			super.setName("DubboConsulSubscribe");
			this.service = service;
		}

		@Override
		public void run() {
			logger.info("ConsulNotifier begin");

			while (running) {

			}
			logger.info("ConsulNotifier end");
		}
	}
}
