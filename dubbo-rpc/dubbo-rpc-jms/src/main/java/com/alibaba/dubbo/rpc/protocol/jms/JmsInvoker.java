package com.alibaba.dubbo.rpc.protocol.jms;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.dubbo.rpc.protocol.AbstractInvoker;

public class JmsInvoker<T> extends AbstractInvoker<T> {

	QueueConnectionFactory queueConnectionFactory = null;
	Queue queue = null;
	QueueConnection queueConnection = null;
	int timeout = 1000;

	public JmsInvoker(Class<T> type, URL url, QueueConnectionFactory queueConnectionFactory, Queue queue) {
		super(type, url);
		this.queueConnectionFactory = queueConnectionFactory;
		this.queue = queue;
		try {
			this.queueConnection = queueConnectionFactory.createQueueConnection();
			this.queueConnection.start();
		} catch (JMSException e) {
			throw new RpcException(e);
		}
		timeout = url.getParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT);
	}

	@Override
	protected Result doInvoke(Invocation invocation) throws Throwable {
		try {
			// transfer a RPC to a jms-requestor and return the call result
			QueueSession session = this.queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			JmsQueueRequestor requestor = new JmsQueueRequestor(session, queue);
			Message message = session.createObjectMessage((Serializable) invocation);
			message.setJMSRedelivered(false);
			ObjectMessage result = (ObjectMessage) requestor.request(message, timeout);
			if (result == null)
				return new RpcResult(new RpcException("request is timeout in " + timeout + "ms"));
			return (Result) (result.getObject());
		} catch (JMSException e) {
			throw new RpcException(e);
		}
	}

}
