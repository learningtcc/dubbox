package com.alibaba.dubbo.sqlhint.mybatis.plugins;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.sqlhint.RouterInfo;
import com.alibaba.dubbo.sqlhint.dubbo.context.RouterConsts;
import com.alibaba.dubbo.sqlhint.dubbo.context.RouterContext;
import com.alibaba.dubbo.sqlhint.masterslave.MasterSlaveHint;
import com.alibaba.dubbo.sqlhint.utils.ReflectionUtil;

/**
 * 利用mybatis拦截器实现sql路由功能
 * 
 * @author Ternence
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
@Component
public class SQLRouterInterceptor implements Interceptor {

	@Autowired
	private MasterSlaveHint msHint;

	/**
	 * DML SQL PREFIX
	 */
	private static List<String> dmlSQLs = new ArrayList<String>();

	static {
		dmlSQLs.add("update");
		dmlSQLs.add("insert");
		dmlSQLs.add("delete");
		dmlSQLs.add("replace");
	}

	public Object intercept(Invocation invocation) throws Throwable {
		Object result = null;

		if (invocation.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
			StatementHandler delegate = (StatementHandler) ReflectionUtil.getFieldValue(statementHandler, "delegate");
			BoundSql boundSql = delegate.getBoundSql();

			// 拦截到的prepare方法参数是一个Connection对象
			Connection connection = (Connection) invocation.getArgs()[0];
			String sql = boundSql.getSql();

			// 1、 如果前端有指定路由则，优先采用指定的路由
			if (RouterContext.containsKey(RouterConsts.ROUTER_KEY) && !hasAnnotation(sql)) {
				RouterInfo routerInfo = RouterContext.get(RouterConsts.ROUTER_KEY);
				sql = new StringBuilder(routerInfo.getSqlrouter()).append(sql).toString();

				// 利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
				ReflectionUtil.setFieldValue(boundSql, "sql", sql);
			}

			// 2、如果没有路由信息，对只读事物添加读取从库的路由
			if (!hasAnnotation(sql) && connection.isReadOnly()) {

				// 获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
				sql = msHint.genRouteInfo(MasterSlaveHint.SLAVE, sql);

				// 利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
				ReflectionUtil.setFieldValue(boundSql, "sql", sql);
			}

			result = invocation.proceed();

			// if (isDML(sql) &&
			// !RouterContext.containsKey(RouterConsts.ROUTER_KEY)) {
			// 如果更改执行的是DML语句，则设置后续的CRUD路由到master
			// String appName = PropertiesUtil.get("app.name");
			// //TODO这里可能需要针对应用来进行主从路由，存在一条链路路由到多个应用的不同DB中
			// RouterContext.put(RouterConsts.ROUTER_KEY,
			// msHint.getRouteMasterHint());
			// }

		} else {
			result = invocation.proceed();
		}

		return result;
	}

	/**
	 * 判断是否是DML语句
	 * 
	 * @param sql
	 * @return
	 * @date 2016年7月14日
	 * @author Ternence
	 */
	private boolean isDML(String sql) {
		if (hasAnnotation(sql)) {
			sql = sql.replaceFirst("/\\*.+\\*/", "").trim(); // 去除sqlhint
		}
		sql = sql.toLowerCase();

		for (String dmlSQL : dmlSQLs) {
			if (sql.startsWith(dmlSQL)) {
				return true;
			}
		}

		return false;
	}

	private boolean hasAnnotation(String sql) {
		if (sql.startsWith("/*")) {
			return true;
		}
		return false;
	}

	/**
	 * 拦截器对应的封装原始对象的方法
	 */
	public Object plugin(Object arg0) {
		if (arg0 instanceof StatementHandler) {
			return Plugin.wrap(arg0, this);
		} else {
			return arg0;
		}
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
