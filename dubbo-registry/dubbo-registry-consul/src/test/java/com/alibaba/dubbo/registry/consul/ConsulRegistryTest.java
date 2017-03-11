/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.registry.consul;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * ConsulRegistryTest
 * 
 * @author tony.chenl
 */
public class ConsulRegistryTest {

	String service = "com.alibaba.dubbo.test.injvmServie";
	/*
	 * URL registryUrl = URL.valueOf("consul://192.168.43.78:8500/"); URL
	 * serviceUrl = URL.valueOf("consul://192.168.43.77:8088/" + service +
	 * "?notify=false&methods=test1,test2&version=1.0");
	 */
	URL registryUrl = URL.valueOf("consul://192.168.177.129:8500/");
	URL serviceUrl = URL
			.valueOf("consul://192.168.177.1:8088/" + service + "?notify=false&methods=test1,test2&version=1.0");
	URL consumerUrl = URL.valueOf("consul://consumer/" + service + "?notify=false&methods=test1,test2");
	ConsulRegistry registry = new ConsulRegistry(registryUrl);

	/**
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		String host = registryUrl.getIp();
		registry.doRegister(serviceUrl);
	}

	@Test
	public void registryService() throws Exception {
		registry.doRegister(serviceUrl);
	}


	/*
	 * @Test(expected = IllegalStateException.class) public void testUrlerror()
	 * { URL errorUrl = URL.valueOf("zookeeper://zookeeper/"); new
	 * ZookeeperRegistry(errorUrl); }
	 */
	@Test
	public void testURL() {

		System.out.println(serviceUrl.getAddress());
		System.out.println(serviceUrl.getIp());
		System.out.println(serviceUrl.getPort());
		System.out.println(serviceUrl.getServiceInterface());
		System.out.println(serviceUrl.getServiceKey());
		System.out.println(serviceUrl.getServiceName());
		System.out.println(serviceUrl.toIdentityString());
		System.out.println(serviceUrl.getParameter(Constants.VERSION_KEY, "2.0"));
		System.out.println(serviceUrl.getParameter(Constants.DYNAMIC_KEY, true));
		System.out.println(serviceUrl.getServiceInterface() + Constants.PATH_SEPARATOR
				+ serviceUrl.getParameter(Constants.CATEGORY_KEY, Constants.DEFAULT_CATEGORY));
		System.out.println(serviceUrl.toFullString());
	}

	@Test
	public void testDefaultPort() {
		// Assert.assertEquals("10.20.153.10:2181",
		// ZookeeperRegistry.appendDefaultPort("10.20.153.10:0"));
		// Assert.assertEquals("10.20.153.10:2181",
		// ZookeeperRegistry.appendDefaultPort("10.20.153.10"));
	}

	@Test
	public void getServiceId() {
		URL url = serviceUrl;
		int hashCode = 0;
		hashCode += url.getServiceInterface().hashCode() * 13;
		hashCode += url.getParameter(Constants.VERSION_KEY, "1.0").hashCode() * 17;
		System.out.print(hashCode);
	}

	@Test
	public void keepAlive() {
		URL url = serviceUrl;
		registry.keepAlive(url);
	}

	@Test
	public void toCategoriesPath() {
		URL url = consumerUrl;
		String[] categroies;
		if (Constants.ANY_VALUE.equals(url.getParameter(Constants.CATEGORY_KEY))) {
			categroies = new String[] { Constants.PROVIDERS_CATEGORY, Constants.CONSUMERS_CATEGORY,
					Constants.ROUTERS_CATEGORY, Constants.CONFIGURATORS_CATEGORY };
		} else {
			categroies = url.getParameter(Constants.CATEGORY_KEY, new String[] { Constants.DEFAULT_CATEGORY });
		}
		String[] paths = new String[categroies.length];
		for (int i = 0; i < categroies.length; i++) {
			paths[i] = "dubbo" + Constants.PATH_SEPARATOR + categroies[i];
		}

		for (String s : paths) {
			System.out.println(s);
		}
	}

	@Test
	public void testRegister() {
		registry.doRegister(serviceUrl);
		/*
		 * List<URL> registered = null; // clear first registered =
		 * registry.getRegistered(service);
		 * 
		 * for (int i = 0; i < 2; i++) { registry.register(service, serviceUrl);
		 * registered = registry.getRegistered(service);
		 * assertTrue(registered.contains(serviceUrl)); } // confirm only 1
		 * regist success; registered = registry.getRegistered(service);
		 * assertEquals(1, registered.size());
		 */
		/*
		 * Set<URL> registered = registry.getRegistered();
		 * 
		 * System.out.print("register sise="+registered.size());
		 */
	}

	@Test
	public void testSubscribe() {
		/*
		 * final String subscribearg = "arg1=1&arg2=2"; // verify lisener. final
		 * AtomicReference<Map<String, String>> args = new
		 * AtomicReference<Map<String, String>>(); registry.subscribe(service,
		 * new URL("dubbo", NetUtils.getLocalHost(), 0,
		 * StringUtils.parseQueryString(subscribearg)), new NotifyListener() {
		 * 
		 * public void notify(List<URL> urls) { // FIXME
		 * assertEquals(ZookeeperRegistry.this.service, service);
		 * args.set(urls.get(0).getParameters()); } });
		 * assertEquals(serviceUrl.toParameterString(),
		 * StringUtils.toQueryString(args.get())); Map<String, String> arg =
		 * registry.getSubscribed(service); assertEquals(subscribearg,
		 * StringUtils.toQueryString(arg));
		 */

	}

}