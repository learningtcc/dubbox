package com.alibaba.dubbo.transactiontree.api;

import java.util.List;

/**
 * 
 * @author fuhaining
 */
public interface Transaction {
	
	Xid getXid();
	
	void setXid(Xid xid);
	
	TransactionType getTransactionType();
	
	void setTransactionType(TransactionType transactionType);
	
	TransactionStatus getTransactionStatus();
	
	void setTransactionStatus(TransactionStatus transactionStatus);
	
	
	List<Participant> getParticipantList();
	
	void addParticipant(Participant participant);
	
	
	void begin();
	
	void commit();
	
	void rollback();
	
	void recovery();
	
//	void setRollbackOnly();
	
	
	void setCreatedTime();
	
	long getCreatedTime();
	
	void setUpdateTime();
	
	long getUpdateTime();
	
	int incrRecoveryNum();
	
	int getRecoveryNum();
}