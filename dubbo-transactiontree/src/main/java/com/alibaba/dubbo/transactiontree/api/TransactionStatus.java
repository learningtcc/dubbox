package com.alibaba.dubbo.transactiontree.api;

/**
 * 
 * @author fuhaining
 */
public enum TransactionStatus {

	// INITIALIZED(0),
	BEGINNING(1), COMMITTING(2), ROLLBACKING(3);

	private int id;

	private TransactionStatus() {
	}

	private TransactionStatus(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static TransactionStatus valueOf(int id) {
		switch (id) {
			case 1:
				return BEGINNING;
			case 2:
				return COMMITTING;
			case 3:
				return ROLLBACKING;
			default:
				return null;
		}
	}
}