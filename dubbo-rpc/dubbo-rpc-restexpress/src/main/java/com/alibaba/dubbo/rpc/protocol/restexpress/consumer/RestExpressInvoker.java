package com.alibaba.dubbo.rpc.protocol.restexpress.consumer;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.dubbo.rpc.protocol.AbstractInvoker;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.Map;

/**
 * @author DonneyYoung
 */
public class RestExpressInvoker<T> extends AbstractInvoker<T> {

	private final Class<?> serviceType;

	private final URL serviceUrl;

	private final String urlStr;

	private static final HttpClient httpclient = new DefaultHttpClient();

	private static final ObjectMapper MAPPER = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	public RestExpressInvoker(Class<T> type, URL url, Map<String, String> attachment) {
		super(type, url, attachment);
		this.serviceType = type;
		this.serviceUrl = url;
		this.urlStr = url.toIdentityString().replaceFirst("^(.*?://)", "http://");
	}

	@Override
	protected Result doInvoke(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArguments();
		byte[] request = "".getBytes();
		if (args.length > 1) {
			throw new IllegalArgumentException("Input params'quantity cannot be greater than one");
		} else if (args.length == 1 && args[0] != null) {
			request = MAPPER.writeValueAsBytes(args[0]);
		}
		RpcResult rpcResult = new RpcResult();
		try {
			byte[] response = post(urlStr + "/" + invocation.getMethodName(), request,
					RpcContext.getContext().getAttachments());
			Class<?> retType = serviceType.getMethod(invocation.getMethodName(), invocation.getParameterTypes())
					.getReturnType();
			if (retType != Void.class && retType != Void.TYPE) {
				try {
					rpcResult.setValue(MAPPER.readValue(response, retType));
				} catch (IOException e) {
					rpcResult.setException(new RpcException("Json Serializable Exception!", e));
				}
			}
		} catch (Exception e) {
			rpcResult.setException(
					new RpcException(RpcException.NETWORK_EXCEPTION, "Fail to invoke restful remote service", e));
		}
		return rpcResult;
	}

	public static byte[] post(String url, byte[] requestContent, Map<String, String> headerMap) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		if (requestContent != null) {
			HttpEntity httpEntity = new ByteArrayEntity(requestContent);
			httpPost.setEntity(httpEntity);
		}
		if (headerMap != null) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}
		HttpResponse response = httpclient.execute(httpPost);
		int responseCode = response.getStatusLine().getStatusCode();
		if (responseCode == HttpStatus.SC_OK || responseCode == HttpStatus.SC_CREATED
				|| responseCode == HttpStatus.SC_ACCEPTED || responseCode == HttpStatus.SC_NO_CONTENT) {
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				return EntityUtils.toByteArray(responseEntity);
			}
		} else if (responseCode == HttpStatus.SC_NOT_FOUND) {
			throw new RpcException(RpcException.UNKNOWN_EXCEPTION, "not found service for url [" + url + "]");
		} else if (responseCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
			throw new RpcException(RpcException.NETWORK_EXCEPTION, "occur an exception at server end.");
		} else {
			throw new RpcException(RpcException.NETWORK_EXCEPTION, "Unknow HttpStatus Code");
		}
		return null;
	}
}
