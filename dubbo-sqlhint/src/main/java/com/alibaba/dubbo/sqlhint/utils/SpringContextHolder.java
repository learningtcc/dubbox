package com.alibaba.dubbo.sqlhint.utils;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 */
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext; // NOSONAR
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型. 如果有多个Bean符合Class, 取出第一个.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		checkApplicationContext();
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 判断当前bean容器中是否包含指定名称的bean
	 * 
	 * @param name
	 *            bean实例名称
	 * @return
	 * @date 2016年1月15日
	 * @since V2.2.0
	 * @author Ternence
	 */
	public static boolean containsBean(String name) {
		checkApplicationContext();
		return applicationContext.containsBean(name);
	}

	/**
	 * 根据类型获取容器中实例的集合
	 * 
	 * @param requiredType
	 * @return
	 * @date 2015年11月17日
	 * @since V2.1.0
	 * @author Ternence
	 */
	public static <T> Map<String, T> getBeans(Class<T> requiredType) {
		checkApplicationContext();
		return applicationContext.getBeansOfType(requiredType);
	}

	/**
	 * 清除applicationContext静态变量.
	 */
	public static void cleanApplicationContext() {
		applicationContext = null;
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}
}
