package com.alibaba.dubbo.loadbalancer;

public abstract class AbstractLoadBalancer implements LoadBalancer {

	public Server chooseServer() {
		return chooseServer(null);
	}

	public abstract LoadBalancerStats getLoadBalancerStats();
}