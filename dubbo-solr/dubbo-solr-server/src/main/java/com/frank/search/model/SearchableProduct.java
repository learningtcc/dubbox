/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.frank.search.model;

/**
 * @author Christoph Strobl
 */
public interface SearchableProduct {

	// 商品ID
	String ID = "id";

	// 商品名称
	String GOODS_NAME = "goods_name";

	// 门店ID
	String STORES_ID = "stores_id";

	// 门店名称
	String STORES_NAME = "stores_name";

	// 商品分类
	String PRODUCT_SORT = "product_sort";

	// 商品父目录的分类
	String PRODUCT_PARENT_SORT = "product_parent_sort";

	// 产地
	String PRODUCE_AREA = "produce_area";

	// 品牌
	String PRODUCT_BRAND = "product_brand";

	// 商品分类名称
	String PRODUCT_SORT_NAME = "product_sort_name";

	// 包装类型
	String PRODUCT_PAC_TYPE = "product_pac_type";

	// 包装类型的名称
	String PRODUCT_PAC_TYPE_NAME = "product_pac_type_name";

	// 原价
	String OR_PRICE = "or_price";

	// 现价
	String CU_PRICE = "cu_price";

	// 原现价
	String OR_CU_PRICE = "or_cu_price";

	// 日限量
	String LIMIT = "limit";

	// 状态
	String STATUS = "status";

	// 销售类型
	String SELL_TYPE = "sell_type";

	// 第三方的产品的状态
	String THIRD_STATUS = "third_status";

	// 第三方产品的连锁状态
	String CHAIN_STATUS = "chain_status";

	// 预售产品
	String SELL_PRE = "sell_pre";

	// 预售库存
	String PRE_AVAILINVENTORY = "pre_availInventory";

	// 创建时间
	String CREATE_DATE = "create_date";

	// 修改时间
	String UPDATE_DATE = "update_date";

	// 折扣
	String DISCOUNT = "discount";

	// 促销类型ID
	String PROMOTION_TYPE_ID = "promotion_type_id";

	// 促销产品ID
	String PROMOTION_SCOPE_ID = "promotion_scope_id";

	// 促销规则ID
	String PROMOTION_RULES_ID = "promotion_rules_id";

	// 促销开始时间
	String PROMOTION_START_DATE = "promotion_start_date";

	// 促销结束时间
	String PROMOTION_END_DATE = "promotion_end_date";

	// 促销每日开始时间
	String PROMOTION_PER_START_DATE = "promotion_per_start_date";

	// 促销每日结束时间
	String PROMOTION_PER_END_DATE = "promotion_per_end_date";

	// 促销规则的名称
	String PROMOTION_RULE_NAME = "promotion_rule_name";

	// 促销的现价
	String PROMOTION_CU_PRICE = "promotion_cu_price";

	// 促销类型
	String BENEFIT_TYPE = "benefit_type";

	// 每个用户限购
	String LIMIT_COUNT = "limit_count";

	// 自营第三方属性
	String THIRD_TYPE = "third_type";

	// 第三方门店ID
	String THIRD_ID = "third_id";

	// 消费券绑定的商品
	String BOUND_DEPOT_GOODS_ID = "bound_depot_goods_id";

	// 促销价
	String PROMOTION_PRICE = "promotion_price";

	// 营业开始时间
	String OPERATE_BEGIN_DATE = "operate_begin_date";

	// 营业结束时间
	String OPERATE_END_DATE = "operate_end_date";

	// 单位
	String UNIT = "unit";

	// 特价商品,有限够买
	String LIMIT_ACTIVITY = "limit_activity";

	// 仓库商品ID
	String DEPOT_GOODS_ID = "depot_goods_id";

	// 可用库存
	String AVAILINVENTORY = "availInventory";

	// 是否是爆款商品
	String HOTFLAG = "hotFlag";

	// 商品图片URL
	String IMAGE_URL = "image_url";

	// 四个角图片URL
	String ANGLE_IMAGE_URL = "angle_image_url";

	// 安全库存
	String SECURITY_INVENTORY = "security_inventory";

	// 活动商品排序
	String NEWUSER_SORTING = "newuser_sorting";

	// 活动商品用来显示商品展示信息
	String NEWUSER_REMARK = "newuser_remark";

	// 新手来源
	String NEWUSER_FROM_SOURCE_CODE = "newuser_from_source_code";

	// 新手价格
	String NEWUSER_CURR_PRICE = "newuser_curr_price";

	// 新手状态
	String NEWUSER_STATUS = "newuser_status";

	// 新手分类(固定分类NEWUSER)
	String NEWUSER_PRODUCT_SORT = "newuser_product_sort";

	// pic( bigPic,smallPic) normal(鲜果类，饮品类) 用英文
	String SHOW_REGION = "show_region";

	// pic 存放图片链接 normal 存放中文名称
	String SHOW_REGION_NAME = "show_region_name";

	// pic,normal 图片列表，商品列表
	String SHOW_TYPE = "show_type";

	// 活动code
	String ACTIVITY_CODE = "activity_code";

	// 活动评价
	String ACTIVITY_REMARK = "activity_remark";

	// 活动排序
	String activity_sorting = "activity_sorting";

	// 位标识 用于处理类似 某些商品不能使用优惠券的需要
	String PRODUCT_FLAG = "product_flag";

	// 产品销量
	String PRODUCT_SALES = "product_sales";

	// 排序
	String SEQ = "seq";

	// 商品名称，多个字段的组合， 包括goods_name， product_sort_name， promotion_rule_name
	String COMMEDITY_NAME = "commedity_name";

	// 商品所有目录
	String PRODUCT_SORT_ALL = "product_sort_all";

	// 平台标识
	String PLATFORM_FLAG = "platform_flag";
}
