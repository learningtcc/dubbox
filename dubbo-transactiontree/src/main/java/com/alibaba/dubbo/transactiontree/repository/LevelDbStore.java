package com.alibaba.dubbo.transactiontree.repository;

import java.io.File;
import java.io.IOException;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.dubbo.transactiontree.api.Transaction;
import com.alibaba.dubbo.transactiontree.api.TransactionCallback;
import com.alibaba.dubbo.transactiontree.log.TransactionLog;
import com.alibaba.dubbo.transactiontree.util.SerializationUtil;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Kryo.DefaultInstantiatorStrategy;

/**
 * 
 * @author fuhaining
 */
public class LevelDbStore implements TransactionStore, InitializingBean, DisposableBean {
	
	private DB db = null;
	private String dataDir = "txtreedb";
	private Options options = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (db == null) {
			if (options == null) {
				options = new Options();
				options.createIfMissing(true);
				options.cacheSize(100 * 1048576); // 100MB cache
				options.logger(new org.iq80.leveldb.Logger() {
					public void log(String message) {
						TransactionLog.REPOSITORY.info(message);
					}
				});
			}
			db = Iq80DBFactory.factory.open(new File(dataDir), options);
		}
		TransactionLog.REPOSITORY.info("初始化levelDB存储成功，数据目录 {}", new File(dataDir).getAbsolutePath());
	}
	
	@Override
	public Transaction get(String transactionId) {
		return (Transaction) SerializationUtil.deserialize(db.get(Iq80DBFactory.bytes(transactionId)));
	}
	
	@Override
	public void put(String transactionId, Transaction transaction) {
		db.put(
			Iq80DBFactory.bytes(transactionId),
			SerializationUtil.serialize(transaction)
		);
	}
	
	@Override
	public void delete(String transactionId) {
		db.delete(Iq80DBFactory.bytes(transactionId));
	}
	
	@Override
	public void foreach(TransactionCallback<Object> action) {
		DBIterator iterator = null;
		try {
			iterator = db.iterator();
			for(iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
//				String key = Iq80DBFactory.asString(iterator.peekNext().getKey());
				Transaction transaction = (Transaction) SerializationUtil.deserialize(iterator.peekNext().getValue());
				try {
					action.doInTransaction(transaction);
				} catch (Throwable e) {
					throw new IllegalStateException(e.getMessage(), e);
				}
			}
		} finally {
			if (iterator != null) { 
				try {
					iterator.close();
				} catch (IOException e) {
					// NOP
				}
			}
		}
	}
	
	@Override
	public void destroy() throws Exception {
		if (db != null) {
			db.close();
		}
		
		TransactionLog.REPOSITORY.info("已关闭levelDB存储");
	}

	public String getDataDir() {
		return dataDir;
	}

	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}

	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public static void main(String[] args) throws Exception {
		Kryo kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);
		kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
//		kryo.register(Transaction.class);
//		kryo.register(Xid.class);
//		kryo.register(TransactionType.class);
//		kryo.register(TransactionStatus.class);
//		kryo.register(Participant.class);
		
//		Output output = new Output(new ByteArrayOutputStream());//new Output(new FileOutputStream("file.bin"));
//		
//		DefaultTransaction defaultTransaction = new DefaultTransaction();
//		defaultTransaction.setXid(new DefaultXid("1", "2", "3"));
//		
//		kryo.writeObject(output, defaultTransaction);
//		output.flush();
//		output.close();
		
		Options options = new Options();
		options.createIfMissing(true);
		options.cacheSize(100 * 1048576); // 100MB cache
		options.logger(new org.iq80.leveldb.Logger() {
			public void log(String message) {
				TransactionLog.REPOSITORY.info(message);
			}
		});
		DB db = Iq80DBFactory.factory.open(new File("txtreedb"), options);
		
//		String stats = db.getProperty("leveldb.stats");
//		System.out.println(stats);
		
		DBIterator iterator = null;
		try {
			iterator = db.iterator();
			for(iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
				String key = Iq80DBFactory.asString(iterator.peekNext().getKey());
				Transaction value = (Transaction) SerializationUtil.deserialize(iterator.peekNext().getValue());
				
				System.out.println(key+" = "+value);
			}
		} finally {
			if (iterator != null) {
				iterator.close();
			}
		}

//		String transactionId = "f282205a-e794-4bda-83a2-bb28b8839cad";
//		Input input = new Input(db.get(Iq80DBFactory.bytes(transactionId)));
//		Transaction transaction = (Transaction) kryo.readClassAndObject(input);
//		System.out.println(transaction);
	}
}