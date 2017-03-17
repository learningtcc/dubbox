package com.alibaba.dubbo.transactiontree.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionLog {
	
	public static final Logger MANAGER = LoggerFactory.getLogger("transactiontree.manager");
	
	public static final Logger REPOSITORY = LoggerFactory.getLogger("transactiontree.repository");
	
	public static final Logger RECOVERY = LoggerFactory.getLogger("transactiontree.recovery");
}
