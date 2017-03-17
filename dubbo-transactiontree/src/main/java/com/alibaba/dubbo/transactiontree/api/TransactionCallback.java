package com.alibaba.dubbo.transactiontree.api;

/**
 * 
 * @author fuhaining
 */
public interface TransactionCallback<T> {
	T doInTransaction(Transaction transaction) throws Throwable;
}