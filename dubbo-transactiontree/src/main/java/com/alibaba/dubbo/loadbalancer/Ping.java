package com.alibaba.dubbo.loadbalancer;

public interface Ping {
	
	boolean isAlive(Server server);
	
	void setLoadBalancer(LoadBalancer lb);

	LoadBalancer getLoadBalancer();
}