package com.frank.search.domain;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: frank
 * Date: 15-11-26
 * Time: 下午1:33
 * To change this template use File | Settings | File Templates.
 */
public class PromotionResponseDto {


    //唯一ID
    private String id;

    //商品名称
    private String goodsName;

    //商店ID
    private String storesId;

    //门店名字
    private String storesName;

    //仓库ID
    private String depotGoodsId;

    //消费券绑定的商品
    private String boundDepotGoodsId;


    //具体的分类
    private List<String> productSort;

    //包装类型
    private String productPacType;

    //日限量
    private long limit;

    //状态
    private String status;

    //库存
    private long availInventory;

    //图片
    private String imageUrl;

    //四角图片
    private String angleImageUrl;

    //促销价
    private float promotionPrice;

    //原价
    private float orPrice;

    //现价
    private float cuPrice;

    //原现价
    private float orCuPrice;

    //折扣
    private float discount;

    //促销类型
    private long benefitType;

    //促销类型ID
    private String promotionTypeId;

    //促销ID
    private String promotionId;

    //促销产品范围ID
    private String promotionScopeId;

    //促销规则ID
    private String promotionRulesId;

    //第三方门店ID
    private String thirdId;

    //自营第三方属性
    private String thirdType;

    private long sequence;

    public float getCuPrice() {
        return cuPrice;
    }

    public void setCuPrice(float cuPrice) {
        this.cuPrice = cuPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public float getOrPrice() {
        return orPrice;
    }

    public void setOrPrice(float orPrice) {
        this.orPrice = orPrice;
    }

    public String getProductPacType() {
        return productPacType;
    }

    public void setProductPacType(String productPacType) {
        this.productPacType = productPacType;
    }

    public List<String> getProductSort() {
        return productSort;
    }

    public void setProductSort(List<String> productSort) {
        this.productSort = productSort;
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

    public String getAngleImageUrl() {
        return angleImageUrl;
    }

    public void setAngleImageUrl(String angleImageUrl) {
        this.angleImageUrl = angleImageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getOrCuPrice() {
        return orCuPrice;
    }

    public void setOrCuPrice(float orCuPrice) {
        this.orCuPrice = orCuPrice;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public float getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(float promotionPrice) {
        this.promotionPrice = promotionPrice;
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

    public String getPromotionTypeId() {
        return promotionTypeId;
    }

    public void setPromotionTypeId(String promotionTypeId) {
        this.promotionTypeId = promotionTypeId;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public String getThirdType() {
        return thirdType;
    }

    public void setThirdType(String thirdType) {
        this.thirdType = thirdType;
    }

    public long getAvailInventory() {
        return availInventory;
    }

    public void setAvailInventory(long availInventory) {
        this.availInventory = availInventory;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString()
    {
        return String.format("<ProductResponseDto: %s, %s, %s, %s, %s, %s, " +
                "%s, %s, %s, %s,%s, %s, %s," +
                " %s, %s,%s, %s, %s, %s, " +
                "%s,%s, %s, %s, %s, %s",id,goodsName,storesId,storesName,
                depotGoodsId,boundDepotGoodsId,
                productSort,productPacType,limit,
                status,availInventory,imageUrl,angleImageUrl,
                promotionPrice,orPrice,cuPrice,orCuPrice,
                discount,benefitType,promotionTypeId,
                promotionId,promotionScopeId,promotionRulesId,thirdId,thirdType
                );
    }
}
