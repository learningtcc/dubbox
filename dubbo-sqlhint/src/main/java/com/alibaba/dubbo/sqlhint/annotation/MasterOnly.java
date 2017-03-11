/**
 * 
 */
package com.alibaba.dubbo.sqlhint.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * masteronly注解 ：标记当前方法及当前方法调用的方法所执行的SQL语句只走主库
 * 
 * @author Ternence
 * @date 2016年11月30日
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MasterOnly {

	/**
	 * sql路由作用域，默认为当前线程，
	 * @return
	 * @date 2016年11月30日
	 * @author Ternence
	 */
	Scope scope() default Scope.INTERNAL;
}
