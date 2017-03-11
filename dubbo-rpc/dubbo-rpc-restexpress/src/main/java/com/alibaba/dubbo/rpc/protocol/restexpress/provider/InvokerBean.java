package com.alibaba.dubbo.rpc.protocol.restexpress.provider;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.restexpress.Request;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author DonneyYoung
 */
public class InvokerBean<T> {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private final Invoker<T> invoker;

	private final Map<String, Class<?>[]> METHOD_MAPPING = new ConcurrentHashMap<String, Class<?>[]>();

	public InvokerBean(T impl, Class<?> type, URL url, Invoker<T> invoker) {
		this.invoker = invoker;
		while (type != null && type != Object.class) {
			for (Method method : type.getDeclaredMethods())
				METHOD_MAPPING.put(method.getName(), method.getParameterTypes());
			type = type.getSuperclass();
		}
	}

	public Result invoke(String methodName, Request req, Map<String, String> attachments) {
		Object[] arguments = new Object[1];
		Class<?>[] argumentsClass = METHOD_MAPPING.get(methodName);
		if (argumentsClass.length > 0)
			arguments[0] = req.getBodyAs(argumentsClass[0]);
		return invoker
				.invoke(new RpcInvocation(methodName, METHOD_MAPPING.get(methodName), arguments, attachments, null));
	}

	public Result invoke(String methodName, Map<String, String> requestParams, Map<String, String> attachments) {
		Object[] arguments = new Object[1];
		try {
			int i = 0;
			for (String each : requestParams.values()) {
				arguments[i] = MAPPER.readValue(each, METHOD_MAPPING.get(methodName)[0]);
				i++;
			}
		} catch (IOException e) {
			throw new IllegalArgumentException(String.format("IllegalArgument Method : %s .", methodName), e);
		}
		return invoker
				.invoke(new RpcInvocation(methodName, METHOD_MAPPING.get(methodName), arguments, attachments, null));
	}
}
