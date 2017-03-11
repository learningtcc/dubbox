package com.alibaba.dubbo.rpc.protocol.restexpress.provider;

import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.protocol.restexpress.RestExpressProtocol;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.restexpress.Request;
import org.restexpress.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author DonneyYoung
 */
public class DubboController {

	private static DubboController singolton;

	private static final Map<String, InvokerBean<?>> SERVICE_MAPPING = new ConcurrentHashMap<String, InvokerBean<?>>();

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private DubboController() {
	}

	public static DubboController getIstance() {
		if (null == singolton) {
			synchronized (DubboController.class) {
				if (null == singolton) {
					singolton = new DubboController();
				}
			}
		}
		return singolton;
	}

	public static boolean isInited() {
		return null != singolton;
	}

	public static void remove(Class<?> type) {
		SERVICE_MAPPING.remove(type.getName());
		SERVICE_MAPPING.remove(type.getSimpleName());
	}

	public Object read(Request req, Response res) {
		return null;
	}

	public Object create(Request req, Response res) throws JsonProcessingException {
		Map<String, String> headerMap = new HashMap<String, String>();
		for (String each : req.getHeaderNames()) {
			headerMap.put(each, req.getHeader(each));
		}
		String serviceName = headerMap.remove(RestExpressProtocol.SERVICE_KEY);
		String methodName = headerMap.remove(RestExpressProtocol.METHOD_KEY);
		InvokerBean<?> invokerBean = SERVICE_MAPPING.get(serviceName);
		Result dubboResult = invokerBean.invoke(methodName, req, headerMap);
		if (dubboResult.hasException()) {
			return MAPPER.writeValueAsString(dubboResult.getException());
		}
		return MAPPER.writeValueAsString(dubboResult.getValue());
	}

	public static void newService(Class<?> type, InvokerBean<?> invokerBean) {
		SERVICE_MAPPING.put(type.getName(), invokerBean);
		SERVICE_MAPPING.put(type.getSimpleName(), invokerBean);
	}
}
