package com.alibaba.dubbo.transactiontree.recovery;

/**
 * 
 * @author fuhaining
 */
public interface RemoteTransactionRecoveryClient {
	
	boolean recovery(byte[] transactionBytes);
	
}