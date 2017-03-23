package com.frank.search.transmitData;

/**
 * Created with IntelliJ IDEA.
 * User: frank
 * Date: 15-6-5
 * Time: 下午2:59
 * To change this template use File | Settings | File Templates.
 */
public class CostTime {

	private transient long start;

    public void start(){
		this.start = System.currentTimeMillis();
	}
	
	public long cost(){
		return System.currentTimeMillis() - start;
	}
}