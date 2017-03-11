package com.alibaba.dubbo.sqlhint.utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 属性配置 加载器
 * @author Ternence
 * @date 2015年3月26日
 */
public abstract class PropertiesUtil {

	protected static final String SYSTEM_PROPERTIES = "/META-INF/app.properties";

	private static Properties props;

	protected static void loadProperties() {
		Resource resource = new ClassPathResource(SYSTEM_PROPERTIES);
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);

		} catch (IOException e) {
			props = new Properties(); // 实例化，标志着配置文件以读取，不关心是否读取成功，防止重复读取文件
		}
	}

	public static String get(String key) {

		if (props == null) {
			synchronized (props) {
				if (props == null) {
					loadProperties();
				}
			}
		}
		return props.getProperty(key);
	}

}
