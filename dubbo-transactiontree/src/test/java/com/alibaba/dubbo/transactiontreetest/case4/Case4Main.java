package com.alibaba.dubbo.transactiontreetest.case4;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Case4Main {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("me/cungu/transactiontreetest/case4/case4.xml");
		ClassA classA = ctx.getBean(ClassA.class);
		
		for (int i = 0; i < 1; i++) {
			String result = classA.m1("p1", "p2");
			System.out.println("result =>" + result);
		}
		
	}
}