package com.alibaba.dubbo.loadbalancer;

import com.alibaba.dubbo.util.Pair;

public class Server {

	private String id;
	private String host;
	private int port = 80;

	private volatile boolean isAliveFlag;

	private MetaInfo simpleMetaInfo = new SimpleMetaInfo() {
		public String getInstanceId() {
			return id;
		};
	};

	public Server(String host, int port) {
		this.host = host;
		this.port = port;
		this.id = host + ":" + port;
		this.isAliveFlag = false;
	}

	public Server(String id) {
		setId(id);
		isAliveFlag = false;
	}

	public String getHostPort() {
		return host + ":" + port;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		Pair<String, Integer> hostPort = getHostPort(id);
		if (hostPort != null) {
			this.id = hostPort.first() + ":" + hostPort.second();
			this.host = hostPort.first();
			this.port = hostPort.second();
		} else {
			this.id = null;
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		if (host != null) {
			this.host = host;
			id = host + ":" + port;
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;

		if (host != null) {
			id = host + ":" + port;
		}
	}

	public boolean isAlive() {
		return isAliveFlag;
	}

	public void setAlive(boolean isAliveFlag) {
		this.isAliveFlag = isAliveFlag;
	}

	public MetaInfo getSimpleMetaInfo() {
		return simpleMetaInfo;
	}

	public void setSimpleMetaInfo(MetaInfo simpleMetaInfo) {
		this.simpleMetaInfo = simpleMetaInfo;
	}

	@Override
	public String toString() {
		return this.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Server))
			return false;
		Server svc = (Server) obj;
		return svc.getId().equals(this.getId());

	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (null == this.getId() ? 0 : this.getId().hashCode());
		return hash;
	}

	public static String normalizeId(String id) {
		Pair<String, Integer> hostPort = getHostPort(id);
		if (hostPort == null) {
			return null;
		} else {
			return hostPort.first() + ":" + hostPort.second();
		}
	}

	public static Pair<String, Integer> getHostPort(String id) {
		if (id != null) {
			String host = null;
			int port = 80;

			if (id.toLowerCase().startsWith("http://")) {
				id = id.substring(7);
			} else if (id.toLowerCase().startsWith("https://")) {
				id = id.substring(8);
			}

			if (id.contains("/")) {
				int slash_idx = id.indexOf("/");
				id = id.substring(0, slash_idx);
			}

			int colon_idx = id.indexOf(':');

			if (colon_idx == -1) {
				host = id; // default
				port = 80;
			} else {
				host = id.substring(0, colon_idx);
				try {
					port = Integer.parseInt(id.substring(colon_idx + 1));
				} catch (NumberFormatException e) {
					throw e;
				}
			}
			return new Pair<String, Integer>(host, port);
		} else {
			return null;
		}
	}

	public static interface MetaInfo {

		public String getAppName();

		public String getServerGroup();

		public String getServiceIdForDiscovery();

		public String getInstanceId();
	}

	public static class SimpleMetaInfo implements MetaInfo {

		@Override
		public String getAppName() {
			return null;
		}

		@Override
		public String getServerGroup() {
			return null;
		}

		@Override
		public String getServiceIdForDiscovery() {
			return null;
		}

		@Override
		public String getInstanceId() {
			return null;
		}
	}
}