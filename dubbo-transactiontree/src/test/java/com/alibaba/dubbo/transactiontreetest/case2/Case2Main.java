package com.alibaba.dubbo.transactiontreetest.case2;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Case2Main {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("me/cungu/transactiontreetest/case2/case2.xml");
		ClassA classA = ctx.getBean(ClassA.class);
		
		String result = classA.m1("p1", "p2");
		System.out.println("result =>" + result);
		
		System.out.println("--------------------------");
		
		ClassBProxy.exception = true;
		
		String result1 = classA.m1("p11", "p12");
		System.out.println("result =>" + result1);
	}
}