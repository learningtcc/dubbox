package com.alibaba.dubbo.transactiontree.api;

/**
 * 
 * @author fuhaining
 */
public interface TransactionContext {

	Xid getXid();

	void setXid(Xid xid);

	TransactionStatus getTransactionStatus();

	void setTransactionStatus(TransactionStatus transactionStatus);

}