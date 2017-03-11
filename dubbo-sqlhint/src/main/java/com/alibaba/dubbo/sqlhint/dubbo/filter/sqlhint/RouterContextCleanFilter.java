package com.alibaba.dubbo.sqlhint.dubbo.filter.sqlhint;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.sqlhint.dubbo.context.RouterContext;

/**
 * 路由上下文清理过滤器
 * @author Ternence
 * @date 2016年7月20日
 */
public class RouterContextCleanFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		Result result = invoker.invoke(invocation);

		// 清理路由信息
		RouterContext.cleanup();
		return result;
	}

}
