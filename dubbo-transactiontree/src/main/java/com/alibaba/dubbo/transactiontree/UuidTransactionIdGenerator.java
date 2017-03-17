package com.alibaba.dubbo.transactiontree;

import java.util.UUID;

import com.alibaba.dubbo.transactiontree.api.TransactionIdGenerator;

/**
 * 
 * @author fuhaining
 */
public class UuidTransactionIdGenerator implements TransactionIdGenerator {
	public static final String STR_HENG = "-";
	public static final String STR_EMPTY = "";
	@Override
	public String generate() {
		return UUID.randomUUID().toString().replaceAll(STR_HENG, STR_EMPTY);
	}
}