package com.alibaba.dubbo.transactiontreetest.case3;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.transactiontree.stat.TransactionStat;

public class Case3Main {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("me/cungu/transactiontreetest/case3/case3.xml");
		ClassA classA = ctx.getBean(ClassA.class);
		
		for (int i = 0; i < 50; i++) {
			try {
				String result = classA.m1("" + System.currentTimeMillis(), "p2");
//				System.out.println("result =>" + result + "   "+ new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
				
//				TransactionStat ts = TransactionStat.getInstance();
//				System.out.println(ToStringBuilder.reflectionToString(ts));
			} catch (Throwable e) {
			}
		}
		
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				TransactionStat ts = TransactionStat.getInstance();
				System.out.println(ToStringBuilder.reflectionToString(ts));
			}
		}, 0, 5000);
		
		
	}
}