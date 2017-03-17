package com.alibaba.dubbo.loadbalancer.rule;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.loadbalancer.AbstractLoadBalancerRule;
import com.alibaba.dubbo.loadbalancer.ClientConfig;
import com.alibaba.dubbo.loadbalancer.LoadBalancer;
import com.alibaba.dubbo.loadbalancer.Server;

public class RoundRobinRule extends AbstractLoadBalancerRule {

	private static final Logger LOG = LoggerFactory.getLogger(RoundRobinRule.class);

	private AtomicInteger nextIndex = new AtomicInteger(0);

	@Override
	public Server choose(LoadBalancer lb, Object key) {
		if (lb == null) {
			LOG.warn("no load balancer");
			return null;
		}
		
		Server server = null;
		int index = 0;

		int count = 0;
		while (server == null && count++ < 10) {
			List<Server> upList = lb.getServerList(true);
			List<Server> allList = lb.getServerList(false);
			int upCount = upList.size();
			int serverCount = allList.size();

			if ((upCount == 0) || (serverCount == 0)) {
				LOG.warn("No up servers available from load balancer: " + lb);
				return null;
			}

			index = nextIndex() % upCount;
			server = upList.get(index);

			if (server == null) {
				Thread.yield();
				continue;
			}

			if (server.isAlive()) {
				return (server);
			}

			server = null;
		}
		if (count >= 10) {
			LOG.warn("No available alive servers after 10 tries from load balancer: " + lb);
		}
		
		return server;
	}
	
	@Override
	public void setClientConfig(ClientConfig clientConfig) {
		
	}
	
	private int nextIndex() {
		nextIndex.compareAndSet(Integer.MAX_VALUE, 0);
		return nextIndex.getAndIncrement();
	}
}