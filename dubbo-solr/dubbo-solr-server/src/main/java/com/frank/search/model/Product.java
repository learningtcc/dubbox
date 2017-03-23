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

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;
import java.util.List;

/**
 * @author Christoph Strobl
 */

public class Product implements SearchableProduct {

	// 商品ID
	@Field(ID)
	private String id;

	// 商品名称
	@Field(GOODS_NAME)
	private String goodsName;

	// 门店ID
	@Field(STORES_ID)
	private String storesId;

	// 门店名称
	@Field(STORES_NAME)
	private String storesName;

	// 商品分类
	@Field(PRODUCT_SORT)
	private List<String> productSort;

	// 商品父目录的分类
	@Field(PRODUCT_PARENT_SORT)
	private List<String> productParentSort;

	// 产地
	@Field(PRODUCE_AREA)
	private String produceArea;

	// 品牌
	@Field(PRODUCT_BRAND)
	private String productBrand;

	// 商品所有目录
	@Field(PRODUCT_SORT_ALL)
	private List<String> productSortAll;

	// 微店和商城的区分(1:微店，2：商城)
	@Field(PLATFORM_FLAG)
	private List<Long> platformFlag;

	// 商品分类名称
	@Field(PRODUCT_SORT_NAME)
	private List<String> productSortName;

	// 包装类型
	@Field(PRODUCT_PAC_TYPE)
	private String productPacType;

	// 包装类型的名称
	@Field(PRODUCT_PAC_TYPE_NAME)
	private String productPacTypeName;

	// 原价
	@Field(OR_PRICE)
	private float orPrice;

	// 现价
	@Field(CU_PRICE)
	private float cuPrice;

	// 原现价
	@Field(OR_CU_PRICE)
	private float orCuPrice;

	// 日限量
	@Field(LIMIT)
	private long limit;

	// 状态
	@Field(STATUS)
	private String status;

	// 销售类型
	@Field(SELL_TYPE)
	private List<Long> sellType;

	// 第三方的产品的状态
	@Field(THIRD_STATUS)
	private String thirdStatus;

	// 第三方产品的连锁状态
	@Field(CHAIN_STATUS)
	private String chainStatus;

	// 预售产品
	@Field(SELL_PRE)
	private long sellPre;

	// 预售库存
	@Field(PRE_AVAILINVENTORY)
	private long preAvailInventory;

	// 创建时间
	@Field(CREATE_DATE)
	private Date createDate;

	// 更新时间
	@Field(UPDATE_DATE)
	private Date updateDate;

	// 折扣
	@Field(DISCOUNT)
	private float discount;

	// 促销活动ID
	@Field(PROMOTION_TYPE_ID)
	private String promotionId;

	// 促销产品ID
	@Field(PROMOTION_SCOPE_ID)
	private String promotionScopeId;

	// 促销规则ID
	@Field(PROMOTION_RULES_ID)
	private String promotionRulesId;

	// 促销开始时间
	@Field(PROMOTION_START_DATE)
	private Date promotionStartDate;

	// 促销结束时间
	@Field(PROMOTION_END_DATE)
	private Date promotionEndDate;

	// 促销每日开始时间
	@Field(PROMOTION_PER_START_DATE)
	private Date promotionPerStartDate;

	// 促销每日结束时间
	@Field(PROMOTION_PER_END_DATE)
	private Date promotionPerEndDate;

	// 促销规则的名称
	@Field(PROMOTION_RULE_NAME)
	private String promotionRuleName;

	// 促销的现价
	@Field(PROMOTION_CU_PRICE)
	private float promotionCuPrice;

	// 促销类型
	@Field(BENEFIT_TYPE)
	private long benefitType;

	// 每个用户限购
	@Field(LIMIT_COUNT)
	private long limitCount;

	// 自营第三方属性
	@Field(THIRD_TYPE)
	private String thirdType;

	// 第三方门店
	@Field(THIRD_ID)
	private String thirdId;

	// 消费券绑定的商品
	@Field(BOUND_DEPOT_GOODS_ID)
	private String boundDepotGoodsId;

	// 促销价
	@Field(PROMOTION_PRICE)
	private float promotionPrice;

	// 营业开始时间
	@Field(OPERATE_BEGIN_DATE)
	private Date operateBeginDate;

	// 营业结束时间
	@Field(OPERATE_END_DATE)
	private Date operateEndDate;

	// 单位
	@Field(UNIT)
	private String unit;

	// 特价商品,有限够买
	@Field(LIMIT_ACTIVITY)
	private String limitActivity;

	// 仓库商品ID
	@Field(DEPOT_GOODS_ID)
	private String depotGoodsId;

	// 可用库存
	@Field(AVAILINVENTORY)
	private long availInventory;

	// 是否是爆款商品
	@Field(HOTFLAG)
	private long hotFlag;

	// 商品图片URL
	@Field(IMAGE_URL)
	private String imageUrl;

	// 四个角图片URL
	@Field(ANGLE_IMAGE_URL)
	private String angleImageUrl;

	// 安全库存
	@Field(SECURITY_INVENTORY)
	private long securityInventory;

	// 活动商品排序
	@Field(NEWUSER_SORTING)
	private long newuserSorting;

	// 活动商品用来显示商品展示信息
	@Field(NEWUSER_REMARK)
	private String newuserRemark;

	// 新手来源
	@Field(NEWUSER_FROM_SOURCE_CODE)
	private String newuserFromSourceCode;

	// 新手价格
	@Field(NEWUSER_CURR_PRICE)
	private float newuserCurrPrice;

	// 新手状态
	@Field(NEWUSER_STATUS)
	private List<Long> newuserStatus;

	// 新手分类(固定分类NEWUSER)
	@Field(NEWUSER_PRODUCT_SORT)
	private String newuserProductSort;

	// pic( bigPic,smallPic) normal(鲜果类，饮品类) 用英文
	@Field(SHOW_REGION)
	private String showRegion;

	// pic 存放图片链接 normal 存放中文名称
	@Field(SHOW_REGION_NAME)
	private String showRegionName;

	// pic,normal 图片列表，商品列表
	@Field(SHOW_TYPE)
	private String showType;

	// 活动code
	@Field(ACTIVITY_CODE)
	private String activityCode;

	// 活动评价
	@Field(ACTIVITY_REMARK)
	private String activityRemark;

	// 活动排序
	@Field(activity_sorting)
	private long activitySorting;

	// 位标识 用于处理类似 某些商品不能使用优惠券的需要
	@Field(PRODUCT_FLAG)
	private String productFlag;

	// 产品销量
	@Field(PRODUCT_SALES)
	private long productSales;

	// 排序
	@Field(SEQ)
	private long sequence;

	// 商品名称，多个字段的组合， 包括goods_name， product_sort_name， promotion_rule_name
	@Field(COMMEDITY_NAME)
	private List<String> commedityName;

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getActivityRemark() {
		return activityRemark;
	}

	public void setActivityRemark(String activityRemark) {
		this.activityRemark = activityRemark;
	}

	public long getActivitySorting() {
		return activitySorting;
	}

	public void setActivitySorting(long activitySorting) {
		this.activitySorting = activitySorting;
	}

	public String getAngleImageUrl() {
		return angleImageUrl;
	}

	public void setAngleImageUrl(String angleImageUrl) {
		this.angleImageUrl = angleImageUrl;
	}

	public long getAvailInventory() {
		return availInventory;
	}

	public void setAvailInventory(long availInventory) {
		this.availInventory = availInventory;
	}

	public long getBenefitType() {
		return benefitType;
	}

	public void setBenefitType(long benefitType) {
		this.benefitType = benefitType;
	}

	public String getBoundDepotGoodsId() {
		return boundDepotGoodsId;
	}

	public void setBoundDepotGoodsId(String boundDepotGoodsId) {
		this.boundDepotGoodsId = boundDepotGoodsId;
	}

	public String getChainStatus() {
		return chainStatus;
	}

	public void setChainStatus(String chainStatus) {
		this.chainStatus = chainStatus;
	}

	public List<String> getCommedityName() {
		return commedityName;
	}

	public void setCommedityName(List<String> commedityName) {
		this.commedityName = commedityName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public float getCuPrice() {
		return cuPrice;
	}

	public void setCuPrice(float cuPrice) {
		this.cuPrice = cuPrice;
	}

	public String getDepotGoodsId() {
		return depotGoodsId;
	}

	public void setDepotGoodsId(String depotGoodsId) {
		this.depotGoodsId = depotGoodsId;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public long getHotFlag() {
		return hotFlag;
	}

	public void setHotFlag(long hotFlag) {
		this.hotFlag = hotFlag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
	}

	public String getLimitActivity() {
		return limitActivity;
	}

	public void setLimitActivity(String limitActivity) {
		this.limitActivity = limitActivity;
	}

	public long getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(long limitCount) {
		this.limitCount = limitCount;
	}

	public float getNewuserCurrPrice() {
		return newuserCurrPrice;
	}

	public void setNewuserCurrPrice(float newuserCurrPrice) {
		this.newuserCurrPrice = newuserCurrPrice;
	}

	public String getNewuserFromSourceCode() {
		return newuserFromSourceCode;
	}

	public void setNewuserFromSourceCode(String newuserFromSourceCode) {
		this.newuserFromSourceCode = newuserFromSourceCode;
	}

	public String getNewuserProductSort() {
		return newuserProductSort;
	}

	public void setNewuserProductSort(String newuserProductSort) {
		this.newuserProductSort = newuserProductSort;
	}

	public String getNewuserRemark() {
		return newuserRemark;
	}

	public void setNewuserRemark(String newuserRemark) {
		this.newuserRemark = newuserRemark;
	}

	public long getNewuserSorting() {
		return newuserSorting;
	}

	public void setNewuserSorting(long newuserSorting) {
		this.newuserSorting = newuserSorting;
	}

	public List<Long> getNewuserStatus() {
		return newuserStatus;
	}

	public void setNewuserStatus(List<Long> newuserStatus) {
		this.newuserStatus = newuserStatus;
	}

	public Date getOperateBeginDate() {
		return operateBeginDate;
	}

	public void setOperateBeginDate(Date operateBeginDate) {
		this.operateBeginDate = operateBeginDate;
	}

	public Date getOperateEndDate() {
		return operateEndDate;
	}

	public void setOperateEndDate(Date operateEndDate) {
		this.operateEndDate = operateEndDate;
	}

	public float getOrCuPrice() {
		return orCuPrice;
	}

	public void setOrCuPrice(float orCuPrice) {
		this.orCuPrice = orCuPrice;
	}

	public float getOrPrice() {
		return orPrice;
	}

	public void setOrPrice(float orPrice) {
		this.orPrice = orPrice;
	}

	public long getPreAvailInventory() {
		return preAvailInventory;
	}

	public void setPreAvailInventory(long preAvailInventory) {
		this.preAvailInventory = preAvailInventory;
	}

	public String getProductFlag() {
		return productFlag;
	}

	public void setProductFlag(String productFlag) {
		this.productFlag = productFlag;
	}

	public String getProductPacType() {
		return productPacType;
	}

	public void setProductPacType(String productPacType) {
		this.productPacType = productPacType;
	}

	public String getProductPacTypeName() {
		return productPacTypeName;
	}

	public void setProductPacTypeName(String productPacTypeName) {
		this.productPacTypeName = productPacTypeName;
	}

	public List<String> getProductParentSort() {
		return productParentSort;
	}

	public void setProductParentSort(List<String> productParentSort) {
		this.productParentSort = productParentSort;
	}

	public long getProductSales() {
		return productSales;
	}

	public void setProductSales(long productSales) {
		this.productSales = productSales;
	}

	public List<String> getProductSort() {
		return productSort;
	}

	public void setProductSort(List<String> productSort) {
		this.productSort = productSort;
	}

	public List<String> getProductSortAll() {
		return productSortAll;
	}

	public void setProductSortAll(List<String> productSortAll) {
		this.productSortAll = productSortAll;
	}

	public List<String> getProductSortName() {
		return productSortName;
	}

	public void setProductSortName(List<String> productSortName) {
		this.productSortName = productSortName;
	}

	public float getPromotionCuPrice() {
		return promotionCuPrice;
	}

	public void setPromotionCuPrice(float promotionCuPrice) {
		this.promotionCuPrice = promotionCuPrice;
	}

	public Date getPromotionEndDate() {
		return promotionEndDate;
	}

	public void setPromotionEndDate(Date promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public Date getPromotionPerEndDate() {
		return promotionPerEndDate;
	}

	public void setPromotionPerEndDate(Date promotionPerEndDate) {
		this.promotionPerEndDate = promotionPerEndDate;
	}

	public Date getPromotionPerStartDate() {
		return promotionPerStartDate;
	}

	public void setPromotionPerStartDate(Date promotionPerStartDate) {
		this.promotionPerStartDate = promotionPerStartDate;
	}

	public float getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(float promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public String getPromotionRuleName() {
		return promotionRuleName;
	}

	public void setPromotionRuleName(String promotionRuleName) {
		this.promotionRuleName = promotionRuleName;
	}

	public String getPromotionRulesId() {
		return promotionRulesId;
	}

	public void setPromotionRulesId(String promotionRulesId) {
		this.promotionRulesId = promotionRulesId;
	}

	public String getPromotionScopeId() {
		return promotionScopeId;
	}

	public void setPromotionScopeId(String promotionScopeId) {
		this.promotionScopeId = promotionScopeId;
	}

	public Date getPromotionStartDate() {
		return promotionStartDate;
	}

	public void setPromotionStartDate(Date promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}

	public long getSecurityInventory() {
		return securityInventory;
	}

	public void setSecurityInventory(long securityInventory) {
		this.securityInventory = securityInventory;
	}

	public long getSellPre() {
		return sellPre;
	}

	public void setSellPre(long sellPre) {
		this.sellPre = sellPre;
	}

	public List<Long> getSellType() {
		return sellType;
	}

	public void setSellType(List<Long> sellType) {
		this.sellType = sellType;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public String getShowRegion() {
		return showRegion;
	}

	public void setShowRegion(String showRegion) {
		this.showRegion = showRegion;
	}

	public String getShowRegionName() {
		return showRegionName;
	}

	public void setShowRegionName(String showRegionName) {
		this.showRegionName = showRegionName;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStoresId() {
		return storesId;
	}

	public void setStoresId(String storesId) {
		this.storesId = storesId;
	}

	public String getStoresName() {
		return storesName;
	}

	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

	public String getThirdStatus() {
		return thirdStatus;
	}

	public void setThirdStatus(String thirdStatus) {
		this.thirdStatus = thirdStatus;
	}

	public String getThirdType() {
		return thirdType;
	}

	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<Long> getPlatformFlag() {
		return platformFlag;
	}

	public void setPlatformFlag(List<Long> platformFlag) {
		this.platformFlag = platformFlag;
	}

	public String getProduceArea() {
		return produceArea;
	}

	public void setProduceArea(String produceArea) {
		this.produceArea = produceArea;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	@Override
	public String toString() {
		return String.format(
				"<Product: id=%s, goodsName=%s, storesId=%s, storesName=%s, productSort=%s, "
						+ "productParentSort=%s, productSortAll=%s, productSortName=%s, productPacType=%s>",
				id, goodsName, storesId, storesName, productSort, productParentSort, productSortAll, productSortName,
				productPacType);

	}

}
