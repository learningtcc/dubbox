package com.alibaba.dubbo.loadbalancer;

import java.util.List;

/**
 * @see https://github.com/Netflix/ribbon
 */
public interface LoadBalancer {
	
	public void addServers(List<Server> newServers);
	
	public Server chooseServer(Object key);
	
	public void markServerDown(Server server);
	
	public List<Server> getServerList(boolean availableOnly);
}