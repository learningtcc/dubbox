package com.alibaba.dubbo.transactiontree;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.dubbo.transactiontree.api.Participant;
import com.alibaba.dubbo.transactiontree.api.Transaction;
import com.alibaba.dubbo.transactiontree.api.TransactionStatus;
import com.alibaba.dubbo.transactiontree.api.TransactionType;
import com.alibaba.dubbo.transactiontree.api.Xid;

/**
 * 
 * @author fuhaining
 */
public class DefaultTransaction implements Transaction {
	
	private Xid xid;

	private TransactionType transactionType;

	private volatile TransactionStatus transactionStatus;

	private List<Participant> participantList = new CopyOnWriteArrayList<Participant>();
	
	private long createdTime;
	private long updateTime;
	
	private AtomicInteger recoveryNum = new AtomicInteger(0);
	
	@Override
	public void begin() {
	}

	@Override
	public void commit() {
		for (Participant participant : participantList) {
			participant.commit();
		}
	}

	@Override
	public void rollback() {
		for (Participant participant : participantList) {
			participant.rollback();
		}
	}

	@Override
	public void recovery() {
		boolean rollback = false;
		
		if (transactionStatus == TransactionStatus.ROLLBACKING) {
			rollback = true;
		}
		else { // transactionStatus == TransactionStatus.BEGINNING || transactionStatus == TransactionStatus.COMMITTING
			for (Participant participant : participantList) {
				if (!participant.isDone()) {
					rollback = true;
					break;
				}
			}
		}
		
		for (Participant participant : participantList) {
			if (rollback) {
				participant.rollback();
			}
			else {
				participant.commit();
			}
		}
	}

	@Override
	public void addParticipant(Participant participant) {
		participantList.add(participant);
	}

	public Xid getXid() {
		return xid;
	}

	public void setXid(Xid xid) {
		this.xid = xid;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public List<Participant> getParticipantList() {
		return participantList;
	}

	public void setParticipantList(List<Participant> participantList) {
		this.participantList = participantList;
	}
	
	@Override
	public long getCreatedTime() {
		return createdTime;
	}

	@Override
	public void setCreatedTime() {
		this.createdTime = System.currentTimeMillis();
	}

	@Override
	public long getUpdateTime() {
		return updateTime;
	}

	@Override
	public void setUpdateTime() {
		this.updateTime = System.currentTimeMillis();
	}
	
	@Override
	public int incrRecoveryNum() {
		return recoveryNum.incrementAndGet();
	}
	
	@Override
	public int getRecoveryNum() {
		return recoveryNum.get();
	}
}