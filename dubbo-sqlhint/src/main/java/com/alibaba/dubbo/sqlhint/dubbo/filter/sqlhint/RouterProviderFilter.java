package com.alibaba.dubbo.sqlhint.dubbo.filter.sqlhint;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.sqlhint.RouterInfo;
import com.alibaba.dubbo.sqlhint.dubbo.context.RouterConsts;
import com.alibaba.dubbo.sqlhint.dubbo.context.RouterContext;

/**
 * SQL路由器，服务端 ，用于做读写路由
 * 
 * @author Ternence
 * @date 2016年7月14日
 */
@Activate(group = Constants.PROVIDER)
public class RouterProviderFilter implements Filter {

	private final Logger logger;

	public RouterProviderFilter() {
		this(LoggerFactory.getLogger(RouterProviderFilter.class));
	}

	public RouterProviderFilter(Logger logger) {
		this.logger = logger;
	}

	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		try {
			// RouterContext.put(RouterConsts.IS_PROVIDER, "true");
			// 接收到调用的时候以传入的为准
			String routerInfo = invocation.getAttachment(RouterConsts.ROUTER_KEY);
			if (routerInfo != null) {
				// 如果路由信息不为空则执行并传递该路由
				RouterContext.put(RouterConsts.ROUTER_KEY, RouterInfo.parse(routerInfo));
			}
			Result result = invoker.invoke(invocation);

			// 返回结果时进行判断，如果当前调用链中包含了路由信息则往回传递该路由
			// if (routerInfo != null) {
			// result.getAttachments().put(RouterConsts.ROUTER_KEY, routerInfo);
			// }

			// 如果线程上下文包含了路由信息，则往回传递该路由
			// if (RouterContext.containsKey(RouterConsts.ROUTER_KEY)) {
			// result.getAttachments().put(RouterConsts.ROUTER_KEY,
			// RouterContext.get(RouterConsts.ROUTER_KEY));
			// }
			return result;
		} catch (RuntimeException e) {
			logger.error("Got unchecked and undeclared exception which called by "
					+ RpcContext.getContext().getRemoteHost() + ". service: " + invoker.getInterface().getName()
					+ ", method: " + invocation.getMethodName() + ", exception: " + e.getClass().getName() + ": "
					+ e.getMessage(), e);
			throw e;
		} finally {
			RouterContext.cleanup(); // server端cleanup
		}
	}

}
