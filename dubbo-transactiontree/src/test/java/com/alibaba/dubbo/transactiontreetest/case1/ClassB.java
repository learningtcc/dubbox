package com.alibaba.dubbo.transactiontreetest.case1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.transactiontree.api.TransactionNode;

@Service
public class ClassB {

	@Autowired
	private ClassC classC;

	@TransactionNode(commit = "m1_confirm", rollback = "r1_cannel")
	public String m1(String p1, String p2) {
//		System.out.println("ClassB m1");
		
//		for (int i = 0; i < 10000; i++) {
			classC.m1(p1, p2);
//		}
		
		return "b";
	}

	public void m1_confirm(String p1, String p2) {
//		System.out.println("ClassB m1_confirm");
	}

	public void r1_cannel(String p1, String p2) {
//		System.out.println("ClassB r1_cannel");
	}
}
