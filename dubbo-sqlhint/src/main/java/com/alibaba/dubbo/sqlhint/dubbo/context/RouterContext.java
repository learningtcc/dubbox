package com.alibaba.dubbo.sqlhint.dubbo.context;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.dubbo.sqlhint.RouterInfo;

/**
 * 当前上下文路由信息
 * 
 * @author Ternence
 * @date 2016年7月15日
 */
public class RouterContext {

	private static ThreadLocal<Map<String, RouterInfo>> routerLocal = new ThreadLocal<Map<String, RouterInfo>>();

	public static void put(String key, RouterInfo info) {
		Map<String, RouterInfo> map = routerLocal.get();
		if (map == null) {
			map = new HashMap<String, RouterInfo>();
			routerLocal.set(map);
		}

		map.put(key, info);
	}

	public static RouterInfo get(String key) {
		if (routerLocal.get() == null) {
			return null;
		}
		return routerLocal.get().get(key);
	}

	public static void cleanup() {
		if (routerLocal.get() == null) {
			return;
		}
		routerLocal.remove();
	}

	public static boolean containsKey(String routerKey) {
		if (routerLocal.get() == null) {
			return false;
		}
		return routerLocal.get().containsKey(routerKey);
	}

}
