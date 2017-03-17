package com.alibaba.dubbo.transactiontreetest.case1;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.transactiontree.api.TransactionManager;

public class Case1Main {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("me/cungu/transactiontreetest/case1/case1.xml");
		final TransactionManager tm = ctx.getBean(TransactionManager.class);
		final ClassA classA = ctx.getBean(ClassA.class);
		
		Executor e = Executors.newFixedThreadPool(50);
		for (int i = 0; i < 1; i++) {
			e.execute(new Runnable() {
				@Override
				public void run() {
					try {
						String result = classA.m1("p1", "p2");
//						System.out.println("success =>" + result);
//						Thread.sleep(new Random().nextInt(1000));
					} catch (Throwable e) {
//						e.printStackTrace();
//						String tix = tm.getTransation().getXid().getTransactionId();
						System.out.println("faliure =>" + e);
					}
				}
			});
		}
		
//		System.out.println("--------------------------");
//		
//		ClassC.exception = true;
//		
//		String result1 = classA.m1("p11", "p12");
//		System.out.println("result =>" + result1);
	}
}