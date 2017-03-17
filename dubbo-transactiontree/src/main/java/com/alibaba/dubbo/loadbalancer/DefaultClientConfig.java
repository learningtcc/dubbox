package com.alibaba.dubbo.loadbalancer;

import java.util.Map;

public class DefaultClientConfig implements ClientConfig {

	@Override
	public String getClientName() {
		return null;
	}

	@Override
	public Map<String, Object> getProperties() {
		return null;
	}

	@Override
	public <T> T get(String key, Class<T> requiredType) {
		return null;
	}

	@Override
	public <T> T get(String key, Class<T> requiredType, T defaultValue) {
		return null;
	}

	@Override
	public <T> T get(Key<T> key) {
		return null;
	}

	@Override
	public <T> T get(Key<T> key, T defaultValue) {
		return null;
	}
}