package com.alibaba.dubbo.sqlhint.annotation;

/**
 * 标记maseronly或者slaveonly的作用域
 * 
 * @author Ternence
 * @date 2016年11月30日
 */
public enum Scope {

	/**
	 * 当前线程
	 */
	INTERNAL,

	/**
	 * 当前线程及远端调用线程（从当前方法开始往后的所有方法调用，包括远端进程的）
	 * 
	 * <pre>
	 * 例如：mod-a -> mod-b -> mod-c,
	 * 假设在 mod-b 的Aservice类的get方法头上添加了该scope标识,那么 mod-b 和 mod-c 在当前链路内执行的SQL语句都会只路由到主库或者从库。
	 * 前提条件是 mod-b 和mod-c 这部分调用链内没有事物注解，事物注解的优先级要高于sqlhint
	 * </pre>
	 */
	REMOTE
}
