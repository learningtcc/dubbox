package com.alibaba.dubbo.sqlhint.mybatis.plugins;

import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.sqlhint.utils.SpringContextHolder;

/**
 * 
 * mybatis 插件注册器
 * 
 * @author Ternence
 * @date 2016年6月7日
 * @since V3.2.0
 */
@Component
public class PluginsRegsiter implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 自动注册所有实现AutoRegister的监听器
		Map<String, SqlSessionFactory> beans = SpringContextHolder.getBeans(SqlSessionFactory.class);
		if (beans != null && beans.size() > 0) {
			for (String beanName : beans.keySet()) {
				SqlSessionFactory sessionFactory = beans.get(beanName);
				sessionFactory.getConfiguration().addInterceptor(SpringContextHolder.getBean(SQLRouterInterceptor.class));
			}
		}
	}
}
