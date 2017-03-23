package com.frank.search.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

@SuppressWarnings("static-access")
public class MyApplicationContext {

	private static ApplicationContext ctx;
	private static MyApplicationContext instance = null;

	public synchronized static MyApplicationContext getInstance() {
		if (instance == null) {
			instance = new MyApplicationContext();
		}
		return instance;
	}

	private MyApplicationContext() {
		initCtx();
	}

	private void initCtx() {
		try {
			if (ctx == null) {
				String location = "conf/spring.xml";
				ctx = new FileSystemXmlApplicationContext(location);
				/*
				 * DataSource dataSource = this.getBean("dataSource");
				 * java.sql.Connection connection = dataSource.getConnection();
				 * connection.close();
				 */
			}
		} catch (Exception e) {
			System.err.println(e.toString());
			System.exit(1);
		}
	}

	public Object getService(String serviceName) {
		return ctx.getBean(serviceName);
	}

	public ApplicationContext getApplicationContext() {
		return this.ctx;
	}

	public static void main(String[] args) {
		getInstance();
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanId) {
		return (T) ctx.getBean(beanId);
	}

}
