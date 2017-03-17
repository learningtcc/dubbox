package com.alibaba.dubbo.transactiontree.api;

/**
 * 
 * @author fuhaining
 */
public interface Participant {

//	Xid getXid();

	void commit();

	void rollback();
	
	boolean isDone();
}