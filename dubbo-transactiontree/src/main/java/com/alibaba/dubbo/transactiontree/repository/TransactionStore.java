package com.alibaba.dubbo.transactiontree.repository;

import com.alibaba.dubbo.transactiontree.api.Transaction;
import com.alibaba.dubbo.transactiontree.api.TransactionCallback;

/**
 * 
 * @author fuhaining
 */
public interface TransactionStore {
	
	Transaction get(String transactionId);
	
	void put(String transactionId, Transaction transaction);
	
	void delete(String transactionId);
	
	void foreach(TransactionCallback<Object> action);
}