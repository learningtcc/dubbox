package com.alibaba.dubbo.transactiontreetest.case3;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Case3ServerMain {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("me/cungu/transactiontreetest/case3/case3_server.xml");
		
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}