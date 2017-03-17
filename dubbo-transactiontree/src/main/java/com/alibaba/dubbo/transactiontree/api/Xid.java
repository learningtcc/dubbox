package com.alibaba.dubbo.transactiontree.api;

/**
 * 
 * @author fuhaining
 */
public interface Xid {
	
	String getRootTransactionId();
	
	void setRootTransactionId(String rootTransactionId);
	
	String getParentTransactionId();
	
	void setParentTransactionId(String parentTransactionId);
	
	String getTransactionId();
	
	void setTransactionId(String transactionId);
}