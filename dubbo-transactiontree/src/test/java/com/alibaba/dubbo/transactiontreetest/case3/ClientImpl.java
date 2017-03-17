package com.alibaba.dubbo.transactiontreetest.case3;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.transactiontree.api.TransactionManager;

@Service
public class ClientImpl implements Client {
	
	@Autowired
	private TransactionManager transactionManager;

	@Override
	public String m1(String p1, String p2) {
		System.out.println(Thread.currentThread() + " ClientImpl m1 " + p1);
		
		if (new Random().nextInt(100) < 10) {
			throw new RuntimeException("m1 error");
		}
		
		return null;
	}

	@Override
	public void m1_confirm(String p1, String p2) {
		System.out.println(Thread.currentThread() + " ClientImpl m1_confirm " + p1);
		
		if (new Random().nextInt(100) < 50) {
			throw new RuntimeException("m1_confirm error");
		}
		
//		try {
//			Thread.sleep(10000L);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		transactionManager.getTransactionRepository();
	}

	@Override
	public void r1_cannel(String p1, String p2) {
		
		System.out.println(Thread.currentThread() + " ClientImpl r1_cannel " + p1);
		
//		try {
//			Thread.sleep(10000L);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
}
