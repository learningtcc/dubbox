package com.alibaba.dubbo.transactiontree.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author fuhaining
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TransactionNode {
	
//	String name() default ""; // TODO

	String commit() default "";

	String rollback() default "";
	
	String isDone() default "";
	
//	Propagation propagation() default Propagation.REQUIRED; // TODO 
}