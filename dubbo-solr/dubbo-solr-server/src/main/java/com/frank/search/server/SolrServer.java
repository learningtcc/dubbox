package com.frank.search.server;

import com.alibaba.dubbo.container.Main;

/**
 * Created with IntelliJ IDEA. User: frank Date: 15-6-4 Time: 上午10:34 To change
 * this template use File | Settings | File Templates.
 */
public class SolrServer {

	public static void main(String args[]) {
		MyApplicationContext.getInstance();
		Main.main(args);
	}
}
