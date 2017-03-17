package com.alibaba.dubbo.transactiontree.api;

/**
 * 
 * @author fuhaining
 */
public enum Propagation {
	
	REQUIRED(1), REQUIRES_NEW(2);

	private int id;

	private Propagation() {
	}

	private Propagation(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}