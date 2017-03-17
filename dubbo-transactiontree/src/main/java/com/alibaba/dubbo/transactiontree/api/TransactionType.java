package com.alibaba.dubbo.transactiontree.api;

/**
 * 
 * @author fuhaining
 */
public enum TransactionType {

	ROOT(0), BRANCH(1);

	private int id;

	private TransactionType() {
	}

	private TransactionType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public static TransactionType valueOf(int id) {
		switch (id) {
			case 0:
				return ROOT;
			case 1:
				return BRANCH;
			default:
				return null;
		}
	}
}