package com.alibaba.dubbo.transactiontree;

import com.alibaba.dubbo.transactiontree.api.Transaction;
import com.alibaba.dubbo.transactiontree.api.TransactionCallback;
import com.alibaba.dubbo.transactiontree.api.TransactionManager;

/**
 * 
 * @author fuhaining
 */
public class TransactionTemplate {
	
	private TransactionManager transactionManager = null;
	
	public TransactionTemplate(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	public <T> T execute(TransactionCallback<T> action) throws Throwable {
		T returnValue = null;
		
		Transaction transaction = transactionManager.getTransation();
		// Transation Tree Controller (ROOT)
		if (transaction == null) {
			try {
				transaction = transactionManager.begin();
				
				returnValue = action.doInTransaction(transaction);
				
			} catch (Throwable e) {
				transactionManager.rollback();
				throw e;
			}
			transactionManager.commit();
		} 
		// Transation Tree Participant
		else {
			returnValue = action.doInTransaction(transaction);
		}
		
		return returnValue;
	}
	
	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}