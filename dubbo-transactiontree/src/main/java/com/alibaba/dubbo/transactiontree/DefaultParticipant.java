package com.alibaba.dubbo.transactiontree;

import com.alibaba.dubbo.transactiontree.api.MethodInvoker;
import com.alibaba.dubbo.transactiontree.api.Participant;

/**
 * 
 * @author fuhaining
 */
public class DefaultParticipant implements Participant {

//	private Xid xid;

//	private TransactionNode transactionNode; // IS PROXY

	private Object[] methodArguments;
	private MethodInvoker commitInvoker;
	private MethodInvoker rollbackInvoker;
	private MethodInvoker isDoneInvoker;

	@Override
	public void commit() {
		if (commitInvoker != null) {
			commitInvoker.invoke(methodArguments);
		}
	}

	@Override
	public void rollback() {
		if (rollbackInvoker != null) {
			rollbackInvoker.invoke(methodArguments);
		}
	}
	
	@Override
	public boolean isDone() {
		if (isDoneInvoker != null) {
			return (boolean) isDoneInvoker.invoke(methodArguments);
		}
		
		return false;
	}
	
//	public TransactionNode getTransactionNode() {
//		return transactionNode;
//	}
//
//	public void setTransactionNode(TransactionNode transactionNode) {
//		this.transactionNode = transactionNode;
//	}

	public Object[] getMethodArguments() {
		return methodArguments;
	}

	public void setMethodArguments(Object[] methodArguments) {
		this.methodArguments = methodArguments;
	}

	public MethodInvoker getCommitInvoker() {
		return commitInvoker;
	}

	public void setCommitInvoker(MethodInvoker commitInvoker) {
		this.commitInvoker = commitInvoker;
	}

	public MethodInvoker getRollbackInvoker() {
		return rollbackInvoker;
	}

	public void setRollbackInvoker(MethodInvoker rollbackInvoker) {
		this.rollbackInvoker = rollbackInvoker;
	}

	public MethodInvoker getIsDoneInvoker() {
		return isDoneInvoker;
	}

	public void setIsDoneInvoker(MethodInvoker isDoneInvoker) {
		this.isDoneInvoker = isDoneInvoker;
	}
	
//	@Override
//	public Xid getXid() {
//		return this.xid;
//	}
//
//	public void setXid(Xid xid) {
//		this.xid = xid;
//	}
}