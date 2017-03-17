package com.alibaba.dubbo.transactiontreetest.case1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.transactiontree.api.TransactionManager;
import com.alibaba.dubbo.transactiontree.api.TransactionNode;

@Service
public class ClassA {

	@Autowired
	private ClassB classB;
	
	@Autowired
	private TransactionManager tm;

	@TransactionNode(commit = "m1_confirm(#args[0])", rollback = "r1_cannel")
	public String m1(String p1, String p2) {
		System.out.println("ClassA m1 " + tm.getTransation().getXid().getTransactionId());

		classB.m1(p1, p2);

		return "a";
	}

	public void m1_confirm(String p1) {
		System.out.println("ClassA m1_confirm "+ tm.getTransation().getXid().getTransactionId());
	}

	public void r1_cannel(String p1, String p2) {
		System.out.println("ClassA r1_cannel "+ tm.getTransation().getXid().getTransactionId());
	}
}
