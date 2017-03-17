package com.alibaba.dubbo.transactiontree.aop.dubbo;

import java.util.Map;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.transactiontree.DefaultTransactionContext;
import com.alibaba.dubbo.transactiontree.DefaultXid;
import com.alibaba.dubbo.transactiontree.api.TransactionContext;
import com.alibaba.dubbo.transactiontree.api.TransactionManager;
import com.alibaba.dubbo.transactiontree.api.TransactionStatus;
import com.alibaba.dubbo.transactiontree.util.BeanFactoryUtil;

/**
 * 
 * ***尚未完成***
 * 
 * @author fuhaining
 * 
 * resources/META-INF/dubbo/com.alibaba.dubbo.rpc.Filter
 *   transactiontreeprovider=me.cungu.transactiontree.aop.dubbo.TransactionProviderFilter
 */
@Activate(group = Constants.PROVIDER)
public class TransactionProviderFilter implements Filter {
	
	private TransactionManager transactionManager;

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		Map<String, String> attachments = invocation.getAttachments();
		if (attachments.containsKey("transactiontree")) {
			TransactionStatus transactionStatus = TransactionStatus.valueOf(attachments.get("transactiontree_transactionstatus"));
			String rootTransactionId = attachments.get("transactiontree_roottransactionid");
			String transactionId = attachments.get("transactiontree_transactionid");
			
			transactionManager = (TransactionManager) BeanFactoryUtil.getBean("transactionManager");
			
			if (transactionStatus == TransactionStatus.BEGINNING) {
				TransactionContext parentTransactionContext = new DefaultTransactionContext();
				parentTransactionContext.setTransactionStatus(transactionStatus);
				parentTransactionContext.setXid(new DefaultXid(rootTransactionId, null, transactionId));
				
				transactionManager.beginBranch(parentTransactionContext); // 定期清理 + 线程覆盖
				
				return invoker.invoke(invocation);
			}
//			else if (transactionStatus == TransactionStatus.COMMITTING) {
//				Result returnValue = invoker.invoke(invocation);
//				
//				transactionManager.commit();
//				
//				return returnValue;
//			}
//			else if (transactionStatus == TransactionStatus.ROLLBACKING) {
//				Result returnValue = invoker.invoke(invocation);
//				
//				transactionManager.rollback();
//				
//				return returnValue;
//			}
		}
		
		return invoker.invoke(invocation);
	}
}