package com.alibaba.dubbo.transactiontreetest.case4;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.transactiontree.api.TransactionNode;

@Service
public class ClassB {

	@TransactionNode(commit = "m1_confirm", rollback = "r1_cannel")
	public String m1(String p1, String p2) {
		System.out.println(Thread.currentThread() + " ClassB m1");
		
		return "b";
	}

	public void m1_confirm(String p1, String p2) {
		System.out.println(Thread.currentThread() + " ClassB m1_confirm");
	}

	public void r1_cannel(String p1, String p2) {
		System.out.println(Thread.currentThread() + " ClassB r1_cannel");
	}
}
