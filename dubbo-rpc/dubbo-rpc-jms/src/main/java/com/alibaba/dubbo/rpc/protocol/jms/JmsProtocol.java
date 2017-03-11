package com.alibaba.dubbo.rpc.protocol.jms;

import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.protocol.AbstractProtocol;

public class JmsProtocol extends AbstractProtocol {

	public static final String NAME = "jms";
	private final static int DEFAULT_PORT = 61616;
	// private final static String DEFAULT_QUEUE = "DUBBO";

	// p:
	private Queue queue;

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public QueueConnectionFactory getQueueConnectionFactory() {
		return queueConnectionFactory;
	}

	public void setQueueConnectionFactory(QueueConnectionFactory queueConnectionFactory) {
		this.queueConnectionFactory = queueConnectionFactory;
	}

	private QueueConnectionFactory queueConnectionFactory;

	public int getDefaultPort() {
		return DEFAULT_PORT;
	}

	public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
		return new JmsExporter<T>(invoker, queueConnectionFactory, queue);
	}

	public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
		return new JmsInvoker<T>(type, url, queueConnectionFactory, queue);
	}

}
