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
import com.alibaba.dubbo.sqlhint.annotation.Scope;
import com.alibaba.dubbo.sqlhint.dubbo.context.RouterConsts;
import com.alibaba.dubbo.sqlhint.dubbo.context.RouterContext;

/**
 * SQL路由器，消费端
 * 
 * @author Ternence
 * @date 2016年7月14日
 */
@Activate(group = Constants.CONSUMER)
public class RouterConsumerFilter implements Filter {

	private final Logger logger;

	public RouterConsumerFilter() {
		this(LoggerFactory.getLogger(RouterConsumerFilter.class));
	}

	public RouterConsumerFilter(Logger logger) {
		this.logger = logger;
	}

	/**
	 * 消费端
	 */
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		try {
			// 接收到调用的时候以传入的为准
			RouterInfo ri = RouterContext.get(RouterConsts.ROUTER_KEY);
			if (ri != null && Scope.REMOTE.equals(ri.getScope())
					&& invocation.getAttachment(RouterConsts.ROUTER_KEY) == null) {
				// 如果路由信息不为空,且作用域为remote,则执行并传递该路由
				invocation.getAttachments().put(RouterConsts.ROUTER_KEY, ri.toString());
			}

			Result result = invoker.invoke(invocation);

			// Map<String, String> attachments = result.getAttachments();
			// 如果远端返回的结果中包含了路由信息，则本地保存。
			// 这里判断，如果调用的远端服务中包含了路由信息 ，则后续的远端请求和本地SQL执行都会依照该路由信息执行 。
			// if (attachments.containsKey(RouterConsts.ROUTER_KEY)) {
			//
			// // 写入上下文
			// if (sqlrouter == null) {
			// RouterContext.put(RouterConsts.ROUTER_KEY,
			// attachments.get(RouterConsts.ROUTER_KEY));
			// }
			// }

			return result;
		} catch (RuntimeException e) {
			logger.error("Got unchecked and undeclared exception which called by "
					+ RpcContext.getContext().getRemoteHost() + ". service: " + invoker.getInterface().getName()
					+ ", method: " + invocation.getMethodName() + ", exception: " + e.getClass().getName() + ": "
					+ e.getMessage(), e);
			throw e;
		} finally {
			// 如果当前span是最前端(当前span没有提供者表明当前位置处在最前端消费者)则清理路由上下文
			// if (!isprovider()) {
			// // RouterContext.cleanup();
			// }
		}
	}

	// private boolean isprovider() {
	// String isprovider = RouterContext.get("_isprovider");
	// return isprovider != null && "true".equalsIgnoreCase(isprovider);
	// }

}
