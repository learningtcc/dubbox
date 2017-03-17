package com.alibaba.dubbo.loadbalancer;

import java.util.List;

public interface ServerListChangeListener {
	void serverListChanged(List<Server> oldList, List<Server> newList);
}