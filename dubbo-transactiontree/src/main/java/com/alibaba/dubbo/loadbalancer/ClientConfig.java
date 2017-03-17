package com.alibaba.dubbo.loadbalancer;

import java.util.Map;

public interface ClientConfig {

	String getClientName();

	Map<String, Object> getProperties();

	<T> T get(String key, Class<T> requiredType);
	
	<T> T get(String key, Class<T> requiredType, T defaultValue);
	
	<T> T get(Key<T> key);
	
	<T> T get(Key<T> key, T defaultValue);

	public static interface Key<T> {

		Key<String> loadBalancerClassName = KeyBuilder.build("loadBalancerClassName", String.class);
		Key<String> ruleClassName = KeyBuilder.build("ruleClassName", String.class);
		Key<String> pingClassName = KeyBuilder.build("pingClassName", String.class);
		Key<Integer> pingIntervalSeconds = KeyBuilder.build("pingIntervalSeconds", Integer.class);
		Key<Integer> maxTotalPingTimeSeconds = KeyBuilder.build("maxTotalPingTimeSeconds", Integer.class);
		Key<String> loadBalancerStatsClassName = KeyBuilder.build("loadBalancerStats", String.class);
		Key<String> listOfServers = KeyBuilder.build("listOfServers", String.class);

		String key(); // String / Integer / Long / Double / Float / Boolean

		Class<T> type();
	}

	public static class KeyBuilder {
		public static <T> Key<T> build(final String key, final Class<T> requiredType) {
			return new Key<T>() {

				@Override
				public String key() {
					return key;
				}

				@Override
				public Class<T> type() {
					return requiredType;
				}
			};
		}
	}
}