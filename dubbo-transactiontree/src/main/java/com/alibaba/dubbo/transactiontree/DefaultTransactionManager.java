package com.alibaba.dubbo.transactiontree;

import com.alibaba.dubbo.transactiontree.api.Participant;
import com.alibaba.dubbo.transactiontree.api.Transaction;
import com.alibaba.dubbo.transactiontree.api.TransactionContext;
import com.alibaba.dubbo.transactiontree.api.TransactionIdGenerator;
import com.alibaba.dubbo.transactiontree.api.TransactionManager;
import com.alibaba.dubbo.transactiontree.api.TransactionRepository;
import com.alibaba.dubbo.transactiontree.api.TransactionStatus;
import com.alibaba.dubbo.transactiontree.api.TransactionType;
import com.alibaba.dubbo.transactiontree.api.Xid;
import com.alibaba.dubbo.transactiontree.log.TransactionLog;
import com.alibaba.dubbo.transactiontree.stat.TransactionStat;

/**
 * 
 * @author fuhaining
 */
public class DefaultTransactionManager implements TransactionManager {
	
	private TransactionRepository transactionRepository = null;
	
	private TransactionIdGenerator transactionIdGenerator = new UuidTransactionIdGenerator();
	
	private TransactionStat transactionStat = TransactionStat.getInstance();
	
	@Override
	public Transaction getTransation() {
		return transactionRepository.findByTransactionId(null);
	}

	@Override
	public void setTransation(Transaction transaction) {
		transactionRepository.save(transaction);
	}
	
	// ----------- ROOT -----------
	
	@Override
	public Transaction begin() {
		transactionStat.incrBeginCount();
		
		Transaction transaction = createRootTransation();
		transaction.setTransactionStatus(TransactionStatus.BEGINNING);
		
		transactionRepository.save(transaction);
		
		TransactionLog.MANAGER.info("开始事务, transactionId={}", transaction.getXid().getTransactionId());
		transaction.begin();
		
		return transaction;
	}
	
	@Override
	public void commit() {
		try {
			Transaction transaction = getTransation();
			if (transaction != null) {
				transactionStat.incrCommitCount();
				
				transaction.setTransactionStatus(TransactionStatus.COMMITTING);
				
				transactionRepository.update(transaction);
				
				TransactionLog.MANAGER.info("提交事务, transactionId={}", transaction.getXid().getTransactionId());
				transaction.commit();
				
				transactionRepository.delete(transaction.getXid().getTransactionId());
				
				transactionStat.incrCommitSuccessCount();
			}
		} catch (Throwable e) {
			transactionRepository.clearCache();
			TransactionLog.MANAGER.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void rollback() {
		try {
			Transaction transaction = getTransation();
			if (transaction != null) {
				transactionStat.incrRollbackCount();
				
				transaction.setTransactionStatus(TransactionStatus.ROLLBACKING);
				
				transactionRepository.update(transaction);
				
				TransactionLog.MANAGER.info("回滚事务, transactionId={}", transaction.getXid().getTransactionId());
				transaction.rollback();
				
				transactionRepository.delete(transaction.getXid().getTransactionId());
				
				transactionStat.incrRollbackSuccessCount();
			}
		} catch (Throwable e) {
			transactionRepository.clearCache();
			TransactionLog.MANAGER.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void addParticipant(Participant participant) {
		Transaction transaction = getTransation();
		transaction.addParticipant(participant);
		
		transactionRepository.update(transaction);
	}
	
	// ----------- BRANCH -----------
	
	@Override
	public Transaction beginBranch(TransactionContext parentTransactionContext) {
		Transaction transaction = createBranchTransation(parentTransactionContext);
		transaction.setTransactionStatus(TransactionStatus.BEGINNING);
		
		transactionRepository.save(transaction);
		
		transaction.begin();
		
		return transaction;
	}
	
	public Transaction createRootTransation() {
		Transaction transaction = new DefaultTransaction();
		transaction.setTransactionType(TransactionType.ROOT);
		
		Xid xid = new DefaultXid();
		String transactionId = transactionIdGenerator.generate();
		xid.setRootTransactionId(transactionId);
		xid.setParentTransactionId(null);
		xid.setTransactionId(transactionId);
		transaction.setXid(xid);
		
		return transaction;
	}

	public Transaction createBranchTransation(TransactionContext parentTransactionContext) {
		Transaction transaction = new DefaultTransaction();
		transaction.setTransactionType(TransactionType.BRANCH);
		transaction.setTransactionStatus(TransactionStatus.BEGINNING); // parentTransactionContext.getTransactionStatus()
		
		Xid parentXid = parentTransactionContext.getXid();
		
		Xid xid = new DefaultXid();
		String transactionId = transactionIdGenerator.generate();
		xid.setRootTransactionId(parentXid.getRootTransactionId());
		xid.setParentTransactionId(parentXid.getTransactionId());
		xid.setTransactionId(transactionId);
		transaction.setXid(xid);
		
		return transaction;
	}

	@Override
	public TransactionRepository getTransactionRepository() {
		return transactionRepository;
	}

	public void setTransactionRepository(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
	public TransactionIdGenerator getTransactionIdGenerator() {
		return transactionIdGenerator;
	}

	public void setTransactionIdGenerator(TransactionIdGenerator transactionIdGenerator) {
		this.transactionIdGenerator = transactionIdGenerator;
	}
}