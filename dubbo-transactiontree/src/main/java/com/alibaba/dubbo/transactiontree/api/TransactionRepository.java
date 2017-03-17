package com.alibaba.dubbo.transactiontree.api;

/**
 * 
 * @author fuhaining
 */
public interface TransactionRepository {
	
	int save(Transaction transaction);
	
	int update(Transaction transaction);
	
	int delete(String transactionId);
	
	void clearCache();
	
	Transaction findByTransactionId(String transactionId);
	
	void foreach(TransactionCallback<Object> action);
}