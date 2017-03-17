package com.alibaba.dubbo.transactiontreetest.case4;

import com.alibaba.dubbo.transactiontree.api.TransactionNode;

public interface Client {

	@TransactionNode(commit = "m1_confirm", rollback = "r1_cannel")
	public String m1(String p1, String p2);

	public void m1_confirm(String p1, String p2);

	public void r1_cannel(String p1, String p2);
	
}