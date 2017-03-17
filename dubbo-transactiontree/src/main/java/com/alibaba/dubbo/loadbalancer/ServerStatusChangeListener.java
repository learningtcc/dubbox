package com.alibaba.dubbo.loadbalancer;

import java.util.Collection;

public interface ServerStatusChangeListener {
	void serverStatusChanged(Collection<Server> servers);
}
