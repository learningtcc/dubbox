package com.alibaba.dubbo.transactiontree.recovery;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.dubbo.transactiontree.api.Transaction;
import com.alibaba.dubbo.transactiontree.api.TransactionCallback;
import com.alibaba.dubbo.transactiontree.api.TransactionRepository;
import com.alibaba.dubbo.transactiontree.log.TransactionLog;
import com.alibaba.dubbo.transactiontree.stat.TransactionStat;

/**
 * 
 * @author fuhaining
 */
public class BadTransactionRecoveryTimer implements InitializingBean, DisposableBean, Runnable {
	
	private TransactionRepository transactionRepository = null;

	private ScheduledExecutorService recoveryScheduledExecutor = Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> recoveryScheduledFuture = null;
	private long recoveryInterval = 5 * 1000; // Millis
	private long badTransactionPeriod = 60 * 1000; // Millis
	private int maxRecoveryNum = 20;
	
	private ExecutorService recoveryExecutor = null;
	
	private TransactionStat transactionStat = TransactionStat.getInstance();

	public void start() {
		if (recoveryScheduledFuture == null) {
			TransactionLog.RECOVERY.info("启动事务恢复线程");
			recoveryScheduledFuture = recoveryScheduledExecutor.scheduleAtFixedRate(this, 0, recoveryInterval, TimeUnit.MILLISECONDS);
		}
	}
	
	public void stop() {
		recoveryScheduledFuture.cancel(false);
		recoveryScheduledFuture = null;
		TransactionLog.RECOVERY.info("停止事务恢复线程");
	}
	
	@Override
	public void run() {
		transactionRepository.foreach(new TransactionCallback<Object>() {
			@Override
			public Object doInTransaction(final Transaction transaction) {
				long lastUpdateTime = transaction.getUpdateTime();
				int recoveryNum = transaction.getRecoveryNum();
				if (recoveryNum <= maxRecoveryNum 
						&& (lastUpdateTime + badTransactionPeriod + (recoveryNum * badTransactionPeriod)) <= System.currentTimeMillis()) {
					recoveryExecutor.execute(new Runnable() {
						@Override
						public void run() {
							String transactionId = transaction.getXid().getTransactionId();
							try {
								transactionStat.incrRecoveryCount();
								
								int currentyRecoveryNum = transaction.incrRecoveryNum();
								transactionRepository.update(transaction);
								
								TransactionLog.RECOVERY.info("第{}次恢复事务, transactionId={}", currentyRecoveryNum, transactionId);
								
								transaction.recovery();
								
								transactionRepository.delete(transactionId);
								
								TransactionLog.RECOVERY.info("恢复事务成功, transactionId={}", transactionId);
								
								transactionStat.incrRecoverySuccessCount();
							} catch (Throwable e) {
								TransactionLog.RECOVERY.error("事务恢复错误，transactionId=" + transactionId, e);
								transactionStat.addRecoveryFailure(transactionId);
							}
						}
					});
				} else {
					if (recoveryNum > maxRecoveryNum ) {
						TransactionLog.RECOVERY.debug("超过最大事务恢复次数，transactionId=" + transaction.getXid().getTransactionId());
					}
				}
				
				return null;
			}
		});
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		start();
		
		if (recoveryExecutor == null) {
			recoveryExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
		}
	}
	
	@Override
	public void destroy() throws Exception {
		recoveryScheduledExecutor.shutdown();
		
		if (recoveryExecutor == null) {
			recoveryExecutor.shutdown();
		}
	}

	public ScheduledExecutorService getRecoveryScheduledExecutor() {
		return recoveryScheduledExecutor;
	}

	public void setRecoveryScheduledExecutor(ScheduledExecutorService recoveryScheduledExecutor) {
		this.recoveryScheduledExecutor = recoveryScheduledExecutor;
	}

	public long getRecoveryInterval() {
		return recoveryInterval;
	}

	public void setRecoveryInterval(long recoveryInterval) {
		this.recoveryInterval = recoveryInterval;
	}
	
	public long getBadTransactionPeriod() {
		return badTransactionPeriod;
	}

	public void setBadTransactionPeriod(long badTransactionPeriod) {
		this.badTransactionPeriod = badTransactionPeriod;
	}

	public int getMaxRecoveryNum() {
		return maxRecoveryNum;
	}

	public void setMaxRecoveryNum(int maxRecoveryNum) {
		this.maxRecoveryNum = maxRecoveryNum;
	}

	public TransactionRepository getTransactionRepository() {
		return transactionRepository;
	}

	public void setTransactionRepository(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public ExecutorService getRecoveryExecutor() {
		return recoveryExecutor;
	}

	public void setRecoveryExecutor(ExecutorService recoveryExecutor) {
		this.recoveryExecutor = recoveryExecutor;
	}
}