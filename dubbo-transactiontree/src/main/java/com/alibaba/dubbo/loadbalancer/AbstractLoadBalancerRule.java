package com.alibaba.dubbo.loadbalancer;

public abstract class AbstractLoadBalancerRule implements Rule, ClientConfigAware {

	private LoadBalancer loadBalancer;

	@Override
	public Server choose(Object key) {
		return choose(getLoadBalancer(), key);
	}

	public abstract Server choose(LoadBalancer lb, Object key);

	@Override
	public void setLoadBalancer(LoadBalancer loadBalancer) {
		this.loadBalancer = loadBalancer;
	}

	@Override
	public LoadBalancer getLoadBalancer() {
		return loadBalancer;
	}
}