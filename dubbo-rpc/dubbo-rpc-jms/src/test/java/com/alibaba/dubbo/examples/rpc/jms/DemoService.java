package com.alibaba.dubbo.examples.rpc.jms;

public interface DemoService {
	
	void test();
	
	int test(int i);
	
	String test(String s, int i);
	
	void testTimeout(int t);

}
