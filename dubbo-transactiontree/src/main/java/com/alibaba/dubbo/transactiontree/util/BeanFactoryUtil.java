package com.alibaba.dubbo.transactiontree.util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import com.alibaba.dubbo.registry.integration.RegistryDirectory;
import com.alibaba.dubbo.rpc.cluster.support.wrapper.MockClusterInvoker;
import com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler;

/**
 * 
 * @author fuhaining
 */
public class BeanFactoryUtil implements BeanFactoryAware, BeanPostProcessor {
	
	private static final Logger LOG = LoggerFactory.getLogger(BeanFactoryUtil.class);
	
	// -------------------------------------
	// BeanFactoryAware
	// -------------------------------------

	private static BeanFactory beanFactory = null;
	
	public static Object getBean(String name) {
		return beanFactory.getBean(name);
	}

	@Override
	public void setBeanFactory(BeanFactory bf) {
		beanFactory = bf;
	}
	
	// -------------------------------------
	// BeanPostProcessor
	// -------------------------------------
	
	private static ConcurrentHashMap<Object, String> beanNameMap = new ConcurrentHashMap<Object, String>();
	
	public static String getBeanName(Object bean) {
		return beanNameMap.get(bean);
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// <dubbo:reference id="client" interface="me.cungu.transactiontreetest.case3.Client" />
		Object handlerFieldValue = getFieldValue(bean, "handler");
		if (handlerFieldValue != null && handlerFieldValue instanceof InvokerInvocationHandler) {
			InvokerInvocationHandler invokerInvocationHandler = (InvokerInvocationHandler) handlerFieldValue;
			Object invokerFieldValue = getFieldValue(invokerInvocationHandler, "invoker");
			if (invokerFieldValue != null && invokerFieldValue instanceof MockClusterInvoker<?>) {
				RegistryDirectory<?> directoryFieldValue = (RegistryDirectory<?>) getFieldValue(invokerFieldValue, "directory");
				Map<String, ?> urlInvokerMap = directoryFieldValue.getUrlInvokerMap();
				if (urlInvokerMap != null) {
					for(Map.Entry<String, ?> entry : urlInvokerMap.entrySet()) {
						Object invoker = getFieldValue(entry.getValue(), "invoker");
						Object invoker0 = getFieldValue(invoker, "invoker"); // ListenerInvokerWrapper
						
						beanNameMap.put((invoker0 != null ? invoker0 : invoker ).toString(), beanName); //  interface me.cungu.transactiontreetest.case3.Client -> dubbo://10.144.33.31:20880/me.cungu.transactiontreetest.case3.Client?anyhost=true&application=me.cungu.transactiontree&check=false&default.check=false&default.delay=-1&delay=-1&dubbo=2.5.5.cat-SNAPSHOT&generic=false&interface=me.cungu.transactiontreetest.case3.Client&logger=slf4j&methods=m1_confirm,m1,r1_cannel&pid=8336&providerside=me.cungu.transactiontree&side=consumer&timestamp=1451379561390
						
						LOG.debug("添加beanName={}", beanName);
					}
				}
			} else {
				// TODO
				throw new UnsupportedOperationException(invokerFieldValue.getClass() + " unknow type");
			}
		}
		
		return bean;
	}
	
	private Object getFieldValue(Object obj, String fieldName) {
		Field field = ReflectionUtils.findField(obj.getClass(), fieldName);
		if (field != null) {
			ReflectionUtils.makeAccessible(field);
			return ReflectionUtils.getField(field, obj);
		}
		
		return null;
	}
}