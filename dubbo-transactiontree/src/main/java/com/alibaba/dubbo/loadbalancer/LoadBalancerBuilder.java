package com.alibaba.dubbo.loadbalancer;

public class LoadBalancerBuilder {
	
	public static LoadBalancerBuilder newBuilder(String clientName) {
		return new LoadBalancerBuilder(clientName);
	}
	
	private String clientName;
	
	public LoadBalancerBuilder(String clientName) {
		this.clientName = clientName;
	}
	
	public LoadBalancer build() {
		return new BaseLoadBalancer();
	}
}