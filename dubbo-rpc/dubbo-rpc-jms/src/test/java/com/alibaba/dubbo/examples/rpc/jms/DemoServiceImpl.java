package com.alibaba.dubbo.examples.rpc.jms;

public class DemoServiceImpl implements DemoService {

	public void test() {
		System.out.println("test");
	}

	public int test(int i) {
		System.out.println("test i");
		return ++i;
	}

	public String test(String s, int i) {
		System.out.println("test s i");
		return s+":"+ ++i;
	}

	public void testTimeout(int t) {
		
		try {
			Thread.sleep(t+500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
