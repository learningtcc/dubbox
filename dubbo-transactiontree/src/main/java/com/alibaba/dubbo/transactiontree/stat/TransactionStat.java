package com.alibaba.dubbo.transactiontree.stat;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionStat {
	
	private static TransactionStat instance = new TransactionStat();
	public static TransactionStat getInstance() {
		return instance;
	}
	
	private AtomicLong beginCount = new AtomicLong(0);
	
	private AtomicLong commitCount = new AtomicLong(0);
	private AtomicLong commitSuccessCount = new AtomicLong(0);
	
	private AtomicLong rollbackCount = new AtomicLong(0);
	private AtomicLong rollbackSuccessCount = new AtomicLong(0);
	
	private AtomicLong recoveryCount = new AtomicLong(0);
	private AtomicLong recoverySuccessCount = new AtomicLong(0);
	private ConcurrentLinkedQueue<String> recoveryFailureList = new ConcurrentLinkedQueue<String>(); // 1000
	
	public long incrBeginCount() {
		return beginCount.incrementAndGet();
	}
	
	public long incrCommitCount() {
		return commitCount.incrementAndGet();
	}
	public long incrCommitSuccessCount() {
		return commitSuccessCount.incrementAndGet();
	}
	
	public long incrRollbackCount() {
		return rollbackCount.incrementAndGet();
	}
	public long incrRollbackSuccessCount() {
		return rollbackSuccessCount.incrementAndGet();
	}
	
	public long incrRecoveryCount() {
		return recoveryCount.incrementAndGet();
	}
	public long incrRecoverySuccessCount() {
		return recoverySuccessCount.incrementAndGet();
	}
	
	public void addRecoveryFailure(String transactionId) {
		if (recoveryFailureList.size() > 1000) {
			for (int i = 0; i < 50; i++) {
				recoveryFailureList.poll();
			}
		}
		recoveryFailureList.add(transactionId);
	}
	
	public AtomicLong getBeginCount() {
		return beginCount;
	}

	public void setBeginCount(AtomicLong beginCount) {
		this.beginCount = beginCount;
	}

	public AtomicLong getCommitCount() {
		return commitCount;
	}

	public void setCommitCount(AtomicLong commitCount) {
		this.commitCount = commitCount;
	}

	public AtomicLong getCommitSuccessCount() {
		return commitSuccessCount;
	}

	public void setCommitSuccessCount(AtomicLong commitSuccessCount) {
		this.commitSuccessCount = commitSuccessCount;
	}

	public AtomicLong getRollbackCount() {
		return rollbackCount;
	}

	public void setRollbackCount(AtomicLong rollbackCount) {
		this.rollbackCount = rollbackCount;
	}

	public AtomicLong getRollbackSuccessCount() {
		return rollbackSuccessCount;
	}

	public void setRollbackSuccessCount(AtomicLong rollbackSuccessCount) {
		this.rollbackSuccessCount = rollbackSuccessCount;
	}

	public AtomicLong getRecoveryCount() {
		return recoveryCount;
	}

	public void setRecoveryCount(AtomicLong recoveryCount) {
		this.recoveryCount = recoveryCount;
	}

	public AtomicLong getRecoverySuccessCount() {
		return recoverySuccessCount;
	}

	public void setRecoverySuccessCount(AtomicLong recoverySuccessCount) {
		this.recoverySuccessCount = recoverySuccessCount;
	}

	public ConcurrentLinkedQueue<String> getRecoveryFailureList() {
		return recoveryFailureList;
	}

	public void setRecoveryFailureList(ConcurrentLinkedQueue<String> recoveryFailureList) {
		this.recoveryFailureList = recoveryFailureList;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 1010; i++) {
			TransactionStat.getInstance().addRecoveryFailure("tx" + i);
		}
		System.out.println(TransactionStat.getInstance().getRecoveryFailureList());
	}
}