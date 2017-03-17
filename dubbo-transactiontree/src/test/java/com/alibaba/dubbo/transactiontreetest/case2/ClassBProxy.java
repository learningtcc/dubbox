package com.alibaba.dubbo.transactiontreetest.case2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.transactiontree.api.Transaction;
import com.alibaba.dubbo.transactiontree.api.TransactionManager;
import com.alibaba.dubbo.transactiontree.api.TransactionNode;

@Service
public class ClassBProxy {
	
	public static boolean exception = false; 

	@Autowired
	private ClassB classB;
	
	@Autowired
	private TransactionManager transactionManager;

	@TransactionNode(commit = "m1_confirm", rollback = "r1_cannel")
	public String m1(final String p1, final String p2) {
		System.out.println(Thread.currentThread() + " ClassBProxy m1");
		
		
//		new Thread(
//			new Runnable() {
//				public void run() {
//					classB.m1(p1, p2);
//				}
//			}
//		).start();
		
		
		
		final Transaction transaction = transactionManager.getTransation();
		
//		// ERROR: 异步线程还没addParticipant， 主线程已经执行成功并调用commit
//		new Thread(
//			new Runnable() {
//				public void run() {
//					transactionManager.setTransation(transaction);
//					
//					classB.m1(p1, p2);
//				}
//			}).start();
		
		ExecutorService es = Executors.newFixedThreadPool(10);
		Future<String> future = es.submit(new Callable<String>() {
			public String call() throws Exception {
				transactionManager.setTransation(transaction);
				
				return classB.m1(p1, p2);
			}
		});
		try {
			future.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (exception) {
			throw new RuntimeException();
		}
		
		return "b";
	}

	public void m1_confirm(String p1, String p2) {
		System.out.println(Thread.currentThread() + " ClassBProxy m1_confirm");
	}

	public void r1_cannel(String p1, String p2) {
		System.out.println(Thread.currentThread() + " ClassBProxy r1_cannel");
	}
}
