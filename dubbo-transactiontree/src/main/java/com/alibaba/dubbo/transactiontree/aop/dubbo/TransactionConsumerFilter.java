package com.alibaba.dubbo.transactiontree.aop.dubbo;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.support.RpcUtils;
import com.alibaba.dubbo.transactiontree.TransactionTemplate;
import com.alibaba.dubbo.transactiontree.api.Participant;
import com.alibaba.dubbo.transactiontree.api.Transaction;
import com.alibaba.dubbo.transactiontree.api.TransactionCallback;
import com.alibaba.dubbo.transactiontree.api.TransactionManager;
import com.alibaba.dubbo.transactiontree.api.TransactionNode;
import com.alibaba.dubbo.transactiontree.api.TransactionStatus;
import com.alibaba.dubbo.transactiontree.util.BeanFactoryUtil;
import com.alibaba.dubbo.transactiontree.util.ParticipantBuilder;

/**
 * 
 * @author fuhaining
 *
 * resources/META-INF/dubbo/com.alibaba.dubbo.rpc.Filter
 *   transactiontreeconsumer=me.cungu.transactiontree.aop.dubbo.TransactionConsumerFilter
 */
@Activate(group = Constants.CONSUMER)
public class TransactionConsumerFilter implements Filter {
	
	private TransactionTemplate transactionTemplate = null;

	@Override
	public Result invoke(final Invoker<?> invoker, final Invocation invocation) throws RpcException {
		final TransactionManager transactionManager = (TransactionManager) BeanFactoryUtil.getBean("transactionManager"); // TODO 
		transactionTemplate = new TransactionTemplate(transactionManager);
		
		String methodName = RpcUtils.getMethodName(invocation);
		Class<?>[] parameterTypes = RpcUtils.getParameterTypes(invocation);
		Method method = ClassUtils.getMethod(invoker.getInterface(), methodName, parameterTypes);
		TransactionNode transactionNode = AnnotationUtils.findAnnotation(method, TransactionNode.class);
		if (transactionNode != null) {
			try {
				return transactionTemplate.execute(new TransactionCallback<Result>() {
					@Override
					public Result doInTransaction(Transaction transaction) throws Throwable {
						if (transaction.getTransactionStatus() == TransactionStatus.BEGINNING) {
							Participant participant = buildParticipant(invocation);
							transactionManager.addParticipant(participant);
						}
						
						putTransactionAttachments(invocation, transaction);
						
						return invoker.invoke(invocation);
					}
				});
			} catch (RpcException e) {
				throw e;
			} catch (Throwable e) {
				throw new IllegalArgumentException(e.getMessage(), e);
			}
		} else {
			Transaction transaction = transactionTemplate.getTransactionManager().getTransation();
			if (transaction != null) {
				putTransactionAttachments(invocation, transaction);
			}
			
			return invoker.invoke(invocation);
		}
	}
	
	public Participant buildParticipant(Invocation invocation) {
		Invoker<?> invoker = invocation.getInvoker();
		Object[] arguments = RpcUtils.getArguments(invocation);
		String methodName = RpcUtils.getMethodName(invocation);
		Class<?>[] parameterTypes = RpcUtils.getParameterTypes(invocation);
		
		Method method = ClassUtils.getMethod(invoker.getInterface(), methodName, parameterTypes);
		String targetObjectId = BeanFactoryUtil.getBeanName(invoker.toString()); //  interface me.cungu.transactiontreetest.case3.Client -> dubbo://10.144.33.31:20880/me.cungu.transactiontreetest.case3.Client?anyhost=true&application=me.cungu.transactiontree&check=false&default.check=false&default.delay=-1&delay=-1&dubbo=2.5.5.cat-SNAPSHOT&generic=false&interface=me.cungu.transactiontreetest.case3.Client&logger=slf4j&methods=m1_confirm,m1,r1_cannel&pid=8336&providerside=me.cungu.transactiontree&side=consumer&timestamp=1451379561390
		
		return ParticipantBuilder.build(targetObjectId, invoker.getInterface(), method, arguments);
	}
	
	public void putTransactionAttachments(Invocation invocation, Transaction transaction) {
		Map<String, String> attachments = invocation.getAttachments();
		attachments.put("transactiontree", "true");
		attachments.put("transactiontree_transactionstatus", transaction.getTransactionStatus().name());
		attachments.put("transactiontree_roottransactionid", transaction.getXid().getRootTransactionId());
		attachments.put("transactiontree_transactionid", transaction.getXid().getTransactionId());
	}
	
	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}
	
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
}