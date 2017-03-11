package com.alibaba.dubbo.sqlhint.masterslave;

import org.springframework.beans.factory.FactoryBean;

/**
 * Masterslave hint instance factory
 * 
 * @author Ternence
 * @date 2016年9月14日
 */
public class MasterSlaveHintFactory implements FactoryBean<MasterSlaveHint> {

	private String instanceClass;

	@Override
	public MasterSlaveHint getObject() throws Exception {
		if (instanceClass == null || instanceClass.startsWith("${")) {
			// have not instanceClass placeholder
			instanceClass = MycatSqlHint.class.getName();
		}

		Class<?> instanceClazz = Class.forName(instanceClass);

		return (MasterSlaveHint) instanceClazz.newInstance();
	}

	@Override
	public Class<?> getObjectType() {
		return MasterSlaveHint.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setInstanceClass(String instanceClass) {
		this.instanceClass = instanceClass;
	}

}
