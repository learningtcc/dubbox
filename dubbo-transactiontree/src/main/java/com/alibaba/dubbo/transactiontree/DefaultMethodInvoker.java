package com.alibaba.dubbo.transactiontree;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.alibaba.dubbo.transactiontree.api.MethodInvoker;
import com.alibaba.dubbo.transactiontree.log.TransactionLog;
import com.alibaba.dubbo.transactiontree.util.BeanFactoryUtil;
import com.alibaba.dubbo.transactiontree.util.ElParser;
import com.alibaba.dubbo.transactiontree.util.MethodUtil;
import com.alibaba.dubbo.transactiontree.util.Pair;

/**
 * 
 * @author fuhaining
 */
public class DefaultMethodInvoker implements MethodInvoker {
	
	private String targetObjectId;
	private Class<?> targetClass;

	private String methodSignature;

	@Override
	public Object invoke(Object[] arguments) {
		Object target = BeanFactoryUtil.getBean(targetObjectId);
		
		Pair<String, List<String>> methodPair = MethodUtil.parseSignature(methodSignature);
		String methodName = methodPair.first();
		List<String> methodArgExprList = methodPair.second(); // arg expr
		int methodArgLength = methodArgExprList.size();
		
		Object[] methodArgs = null;
		Class<?>[] methodArgTypes = null;
		if (methodArgLength > 0) {
			ElParser elParser = ElParser.getInstance();
			elParser.setVar("args", arguments);
			
			methodArgs = new Object[methodArgLength];
			methodArgTypes = new Class<?>[methodArgLength];
			for (int i = 0; i < methodArgLength; i++) {
				Class<?> methodArgType = arguments[i].getClass();
				methodArgs[i] = elParser.eval(methodArgExprList.get(i), methodArgType);
				methodArgTypes[i] = methodArgType;
			}
		} else {
			if (arguments != null && arguments.length > 0) {
				methodArgLength = arguments.length;
				methodArgs = new Object[methodArgLength];
				methodArgTypes = new Class<?>[methodArgLength];
				for (int i = 0; i < methodArgLength; i++) {
					methodArgs[i] = arguments[i];
					methodArgTypes[i] = methodArgs[i].getClass();
				}
			}
		}
		
		Method method = ReflectionUtils.findMethod(targetClass, methodName, methodArgTypes);
		TransactionLog.MANAGER.debug("执行事务方法, {}", method.toGenericString());
		
		return ReflectionUtils.invokeMethod(method, target, methodArgs);
	}
	
	public String getTargetObjectId() {
		return targetObjectId;
	}

	public void setTargetObjectId(String targetObjectId) {
		this.targetObjectId = targetObjectId;
	}

	@Override
	public Class<?> getTargetClass() {
		return this.targetClass;
	}

	@Override
	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
		
		if (StringUtils.isBlank(targetObjectId)) {
			Component component = AnnotationUtils.findAnnotation(targetClass, Component.class);
			String beanName = component.value();
			if (StringUtils.isBlank(beanName)) {
				beanName = StringUtils.uncapitalize(targetClass.getSimpleName());
			}
			this.targetObjectId = beanName;
		}
	}

	@Override
	public String getMethodSignature() {
		return this.methodSignature;
	}

	@Override
	public void setMethodSignature(String methodSignature) {
		this.methodSignature = methodSignature;
	}
}