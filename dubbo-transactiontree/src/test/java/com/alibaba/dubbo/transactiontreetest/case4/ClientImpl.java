package com.alibaba.dubbo.transactiontreetest.case4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.transactiontree.api.TransactionManager;

@Service
public class ClientImpl implements Client {
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private ClassB classB;

	@Override
	public String m1(String p1, String p2) {
		System.out.println(Thread.currentThread() + " ClientImpl m1");
		
		classB.m1(p1, p2);
		
		return null;
	}

	@Override
	public void m1_confirm(String p1, String p2) {
		System.out.println(Thread.currentThread() + " ClientImpl m1_confirm");
	}

	@Override
	public void r1_cannel(String p1, String p2) {
		System.out.println(Thread.currentThread() + " ClientImpl r1_cannel");
	}
}
