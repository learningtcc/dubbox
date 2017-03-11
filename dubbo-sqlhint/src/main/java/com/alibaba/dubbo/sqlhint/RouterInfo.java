/**
 * 
 */
package com.alibaba.dubbo.sqlhint;

import com.alibaba.dubbo.sqlhint.annotation.Scope;

/**
 * 路由信息
 * 
 * @author Ternence
 * @date 2016年11月30日
 */
public class RouterInfo {
	/**
	 * SQL注解
	 */
	private String sqlrouter;

	/**
	 * 注解应用范围
	 */
	private Scope scope;

	/**
	 * 
	 */
	public RouterInfo() {
	}

	/**
	 * @param sqlrouter
	 */
	public RouterInfo(String sqlrouter) {
		super();
		this.sqlrouter = sqlrouter;
	}

	/**
	 * @param sqlrouter
	 * @param scope
	 */
	public RouterInfo(String sqlrouter, Scope scope) {
		this.sqlrouter = sqlrouter;
		this.scope = scope;
	}

	/**
	 * @return the sqlhint
	 */
	public String getSqlrouter() {
		return sqlrouter;
	}

	/**
	 * @param sqlhint
	 *            the sqlhint to set
	 */
	public void setSqlrouter(String sqlhint) {
		this.sqlrouter = sqlhint;
	}

	/**
	 * @return the scope
	 */
	public Scope getScope() {
		return scope;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public String toString() {
		return sqlrouter + "|" + scope.name();
	}

	public static RouterInfo parse(String routerInfo) {
		String[] routers = routerInfo.split("\\|");
		return new RouterInfo(routers[0], Scope.valueOf(routers[1]));
	}

}
