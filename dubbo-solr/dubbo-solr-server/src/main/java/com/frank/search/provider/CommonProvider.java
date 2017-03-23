package com.frank.search.provider;

import com.frank.search.transmitData.factory.ResponseDefaultFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommonProvider<T> {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	protected ResponseDefaultFactory factory;

	/**
	 * 日志
	 */
	public org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

}
