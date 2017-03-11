package com.alibaba.dubbo.rpc.protocol.jms;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.dubbo.rpc.protocol.AbstractExporter;

public class JmsExporter<T> extends AbstractExporter<T> {

	// use the given connection and queue to subscribe invocation and execute
	// for a result,
	// then send the result back to replyTo queue
	public JmsExporter(final Invoker<T> invoker, final QueueConnectionFactory queueConnectionFactory,
			final Queue queue) {
		super(invoker);
		try {
			QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
			final QueueSession session = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			QueueReceiver receiver = session.createReceiver(queue);
			MessageListener listener = new MessageListener() {
				public void onMessage(Message message) {
					try {
						Object invocation = ((ObjectMessage) message).getObject();
						Result result = null;
						try {
							result = invoker.invoke((Invocation) invocation);
						} catch (RpcException e) {
							result = new RpcResult(e);
						}
						MessageProducer producer = session.createProducer(message.getJMSReplyTo());
						Message msg = session.createObjectMessage((Serializable) result);
						producer.send(msg);
						producer.close();
					} catch (JMSException e) {
						// throw new RpcException(e);
					}
				}
			};

			receiver.setMessageListener(listener);
			queueConnection.start();

		} catch (JMSException e) {
			throw new RpcException(e);
		}
	}

}
