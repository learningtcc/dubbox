package com.alibaba.dubbo.rpc.protocol.jms;

import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TemporaryQueue;

public class JmsQueueRequestor {
	private QueueSession session;
	private TemporaryQueue temporaryQueue;
	private QueueSender sender;
	private QueueReceiver receiver;

	public JmsQueueRequestor(QueueSession session, Queue queue) throws JMSException {
		super();

		if (queue == null) {
			throw new InvalidDestinationException("Invalid queue");
		}

		setSession(session);
		setTemporaryQueue(session.createTemporaryQueue());
		setSender(session.createSender(queue));
		setReceiver(session.createReceiver(getTemporaryQueue()));
	}

	public Message request(Message message) throws JMSException {
		message.setJMSReplyTo(getTemporaryQueue());
		getSender().send(message);
		return getReceiver().receive();
	}

	public Message request(Message message, long timeout) throws JMSException {
		message.setJMSReplyTo(getTemporaryQueue());
		getSender().send(message);
		return getReceiver().receive(timeout);
	}

	public void close() throws JMSException {
		getSession().close();
		getTemporaryQueue().delete();
	}

	private void setReceiver(QueueReceiver receiver) {
		this.receiver = receiver;
	}

	private QueueReceiver getReceiver() {
		return receiver;
	}

	private void setSender(QueueSender sender) {
		this.sender = sender;
	}

	private QueueSender getSender() {
		return sender;
	}

	private void setSession(QueueSession session) {
		this.session = session;
	}

	private QueueSession getSession() {
		return session;
	}

	private void setTemporaryQueue(TemporaryQueue temporaryQueue) {
		this.temporaryQueue = temporaryQueue;
	}

	private TemporaryQueue getTemporaryQueue() {
		return temporaryQueue;
	}
}