package com.alibaba.dubbo.registry.consul;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.RegistryFactory;

/**
 * Created by make on 2017/1/8.
 */
public class ConsulRegistryFactory implements RegistryFactory {
	@Override
	public Registry getRegistry(URL url) {
		return new ConsulRegistry(url);
	}
}
