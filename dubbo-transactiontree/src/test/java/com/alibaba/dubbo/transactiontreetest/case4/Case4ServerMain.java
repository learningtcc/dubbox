package com.alibaba.dubbo.transactiontreetest.case4;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Case4ServerMain {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("me/cungu/transactiontreetest/case4/case4_server.xml");
		
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}