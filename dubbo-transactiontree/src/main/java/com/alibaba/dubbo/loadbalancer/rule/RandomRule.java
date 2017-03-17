package com.alibaba.dubbo.loadbalancer.rule;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.loadbalancer.AbstractLoadBalancerRule;
import com.alibaba.dubbo.loadbalancer.ClientConfig;
import com.alibaba.dubbo.loadbalancer.LoadBalancer;
import com.alibaba.dubbo.loadbalancer.Server;

public class RandomRule extends AbstractLoadBalancerRule {
	
	private static final Logger LOG = LoggerFactory.getLogger(RandomRule.class);

	private Random random;

	public RandomRule() {
		this.random = new Random();
	}

	@Override
	public Server choose(LoadBalancer lb, Object key) {
		if (lb == null) {
			LOG.warn("no load balancer");
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

			int index = random.nextInt(serverCount);
			try {
				server = upList.get(index);
			} catch (Throwable t) {
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
	public void setClientConfig(ClientConfig clientConfig) {
		
	}
}