package com.alibaba.dubbo.loadbalancer;

public interface ClientConfigAware {
	
	void setClientConfig(ClientConfig clientConfig);
}