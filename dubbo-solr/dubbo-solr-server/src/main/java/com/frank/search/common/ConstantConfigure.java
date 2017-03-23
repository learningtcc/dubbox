package com.frank.search.common;

/**
 * Created with IntelliJ IDEA. User: frank Date: 15-12-8 Time: 下午5:48 To change
 * this template use File | Settings | File Templates.
 */
public interface ConstantConfigure {

	// 微店平台标识
	long PLATFORM_FLAG_WEIDIAN = 1;

	// 主站平台标识
	long PLATFORM_FLAG_MASTERSTATION = 2;

	// 自营第三方属性
	String THIRD_TYPE = "PRODUC_TYPE_THIRD";

	// 第三方的产品的状态
	String third_status = "ENABLE_TRUE";

	// 第三方产品的连锁状态
	String CHAIN_STATUS = "ENABLE_TRUE";

	// 当前的价格
	long CU_PRICE = 0;

	// 新手来源
	String NEWUSER_FROM_SOURCE_CODE = "0";

	// 新手状态
	long NEWUSER_STATUS = 1;

	// 新手分类(固定分类NEWUSER)
	String NEWUSER_PRODUCT_SORT = "NEWUSER";

}
