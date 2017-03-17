package com.alibaba.dubbo.transactiontree.repository;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.alibaba.dubbo.transactiontree.api.Transaction;
import com.alibaba.dubbo.transactiontree.api.TransactionCallback;
import com.alibaba.dubbo.transactiontree.api.TransactionRepository;

/**
 * 
 * @author fuhaining
 */
public class DefaultTransactionRepository implements TransactionRepository {
	
	private final ConcurrentHashMap<Thread, Transaction> transactionCache = new ConcurrentHashMap<Thread, Transaction>();
	
	private TransactionStore transactionStore = null;

	@Override
	public int save(Transaction transaction) {
		transaction.setCreatedTime();
		transaction.setUpdateTime();
		
		transactionStore.put(transaction.getXid().getTransactionId(), transaction);
		transactionCache.put(Thread.currentThread(), transaction);
		return 1;
	}

	@Override
	public int update(Transaction transaction) {
		transaction.setUpdateTime();
		
		transactionStore.put(transaction.getXid().getTransactionId(), transaction);
		transactionCache.put(Thread.currentThread(), transaction);
		return 1;
	}

	@Override
	public int delete(String transactionId) {
		transactionStore.delete(transactionId);
		transactionCache.remove(Thread.currentThread());
		return 1;
	}
	
	@Override
	public void clearCache() {
		transactionCache.remove(Thread.currentThread());
	}

	@Override
	public Transaction findByTransactionId(String transactionId) {
		Transaction currentTransaction = transactionCache.get(Thread.currentThread());
		if (currentTransaction == null && StringUtils.isNotBlank(transactionId)) {
			currentTransaction = transactionStore.get(transactionId);
			transactionCache.put(Thread.currentThread(), currentTransaction);
		}
		
		return currentTransaction;
	}
	
	@Override
	public void foreach(TransactionCallback<Object> action) {
		transactionStore.foreach(action);
	}

	public void setTransactionStore(TransactionStore transactionStore) {
		this.transactionStore = transactionStore;
	}
}