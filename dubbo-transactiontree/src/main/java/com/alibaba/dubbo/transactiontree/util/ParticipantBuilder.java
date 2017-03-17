package com.alibaba.dubbo.transactiontree.util;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;

import com.alibaba.dubbo.transactiontree.DefaultMethodInvoker;
import com.alibaba.dubbo.transactiontree.DefaultParticipant;
import com.alibaba.dubbo.transactiontree.api.Participant;
import com.alibaba.dubbo.transactiontree.api.TransactionNode;

/**
 * 
 * @author fuhaining
 */
public class ParticipantBuilder {
	
	public static Participant build(String targetObjectId, Class<?> targetClass, Method method, Object[] arguments) { // TODO TO CACHE
		TransactionNode transactionNode = AnnotationUtils.findAnnotation(method, TransactionNode.class);
		
		String commitMethodSignature = transactionNode.commit();
		DefaultMethodInvoker commitInvoker = null;
		if (StringUtils.isNotBlank(commitMethodSignature)) {
			commitInvoker = new DefaultMethodInvoker();
			commitInvoker.setTargetObjectId(targetObjectId);
			commitInvoker.setTargetClass(targetClass);
			commitInvoker.setMethodSignature(commitMethodSignature.trim());
		}
		
		String rollbackMethodSignature = transactionNode.rollback();
		DefaultMethodInvoker rollbackInvoker = null;
		if (StringUtils.isNotBlank(rollbackMethodSignature)) {
			rollbackInvoker = new DefaultMethodInvoker();
			rollbackInvoker.setTargetObjectId(targetObjectId);
			rollbackInvoker.setTargetClass(targetClass);
			rollbackInvoker.setMethodSignature(rollbackMethodSignature.trim());
		}
		
		String isDoneMethodSignature = transactionNode.isDone();
		DefaultMethodInvoker isDoneInvoker = null;
		if (StringUtils.isNotBlank(isDoneMethodSignature)) {
			isDoneInvoker = new DefaultMethodInvoker();
			isDoneInvoker.setTargetObjectId(targetObjectId);
			isDoneInvoker.setTargetClass(targetClass);
			isDoneInvoker.setMethodSignature(isDoneMethodSignature.trim());
		}
		
		DefaultParticipant participant = new DefaultParticipant();
//		participant.setXid(xid);
//		participant.setTransactionNode(transactionNode);
		participant.setMethodArguments(arguments);
		participant.setCommitInvoker(commitInvoker);
		participant.setRollbackInvoker(rollbackInvoker);
		participant.setIsDoneInvoker(isDoneInvoker);
		
		return participant;
	}
}