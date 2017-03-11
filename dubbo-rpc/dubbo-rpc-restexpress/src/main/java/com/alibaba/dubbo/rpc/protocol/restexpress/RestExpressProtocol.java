package com.alibaba.dubbo.rpc.protocol.restexpress;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.ProxyFactory;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.protocol.AbstractExporter;
import com.alibaba.dubbo.rpc.protocol.AbstractProxyProtocol;
import com.alibaba.dubbo.rpc.protocol.restexpress.consumer.RestExpressInvoker;
import com.alibaba.dubbo.rpc.protocol.restexpress.provider.DubboController;
import com.alibaba.dubbo.rpc.protocol.restexpress.provider.InvokerBean;
import io.netty.handler.codec.http.HttpMethod;
import org.restexpress.RestExpress;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author DonneyYoung
 */
public class RestExpressProtocol extends AbstractProxyProtocol {

	public static final String SERVICE_KEY = "service_name";

	public static final String METHOD_KEY = "method_name";

	public static final String SSL_CONTEXT_DEFAULT = "Default";

	private static final int DEFAULT_PORT = 8080;

	private static final RestExpress REST_EXPRESS_SERVER = new RestExpress();

	public RestExpressProtocol() {
		super(IOException.class, RpcException.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Exporter<T> export(final Invoker<T> invoker) throws RpcException {
		ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
		final String uri = serviceKey(invoker.getUrl());
		Exporter<T> exporter = (Exporter<T>) exporterMap.get(uri);
		if (exporter != null) {
			return exporter;
		}
		assert proxyFactory != null;
		final Class<?> serviceType = invoker.getInterface();
		if (!DubboController.isInited()) {
			int pos = invoker.getUrl().getPath().lastIndexOf("/");
			String contextPath = pos > 0 ? invoker.getUrl().getPath().substring(0, pos) + "/" : "";
			String path = contextPath + "{" + SERVICE_KEY + "}/{" + METHOD_KEY + "}";
			try {
				SSLContext defaultSSLContext = SSLContext.getDefault();
				if (!defaultSSLContext.getProtocol().equals(SSL_CONTEXT_DEFAULT))
					REST_EXPRESS_SERVER.setSSLContext(defaultSSLContext);
			} catch (NoSuchAlgorithmException ignored) {
			}
			REST_EXPRESS_SERVER.setPort(invoker.getUrl().getPort()).setKeepAlive(true)
					.uri(path, DubboController.getIstance()).method(HttpMethod.GET, HttpMethod.POST).noSerialization();
			REST_EXPRESS_SERVER.bind();
		}
		DubboController.newService(serviceType, new InvokerBean<>(proxyFactory.getProxy(invoker),
				serviceType, invoker.getUrl(), invoker));
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				DubboController.remove(serviceType);
			}
		};
		exporter = new AbstractExporter<T>(invoker) {
			public void unexport() {
				super.unexport();
				exporterMap.remove(uri);
				try {
					runnable.run();
				} catch (Throwable t) {
					logger.warn(t.getMessage(), t);
				}
			}
		};
		exporterMap.put(uri, exporter);
		return exporter;
	}

	@Override
	public <T> Invoker<T> refer(final Class<T> type, final URL url) throws RpcException {
		Invoker<T> invoker = new RestExpressInvoker<>(type, url, null);
		invokers.add(invoker);
		return invoker;
	}

	@Override
	public int getDefaultPort() {
		return DEFAULT_PORT;
	}

	@Override
	protected <T> Runnable doExport(T impl, Class<T> type, URL url) throws RpcException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected synchronized <T> T doRefer(Class<T> type, URL url) throws RpcException {
		throw new UnsupportedOperationException();
	}
}
