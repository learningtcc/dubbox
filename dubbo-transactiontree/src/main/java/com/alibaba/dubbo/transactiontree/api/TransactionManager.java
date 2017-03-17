package com.alibaba.dubbo.transactiontree.api;

/**
 * 
 * @author fuhaining
 */
public interface TransactionManager {
	
//	Transaction createRootTransation();
//	
//	Transaction createBranchTransation(TransactionContext parentTransactionContext);
	
//	Transaction getCurrentTransation();
	
	TransactionRepository getTransactionRepository();
	
	Transaction getTransation();
	
	void setTransation(Transaction transaction);
	
	
	Transaction begin();
	
	void commit();

	void rollback();
	
	void addParticipant(Participant participant);
	
	
	Transaction beginBranch(TransactionContext parentTransactionContext);
}