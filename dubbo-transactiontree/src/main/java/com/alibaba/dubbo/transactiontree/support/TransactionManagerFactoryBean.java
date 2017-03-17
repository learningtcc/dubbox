package com.alibaba.dubbo.transactiontree.support;

import org.springframework.beans.factory.FactoryBean;

import com.alibaba.dubbo.transactiontree.api.TransactionManager;

/**
 * 
 * @author fuhaining
 */
public class TransactionManagerFactoryBean implements FactoryBean<TransactionManager> {

	@Override
	public TransactionManager getObject() throws Exception {
		return null;
	}

	@Override
	public Class<?> getObjectType() {
		return TransactionManager.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
