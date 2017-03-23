package com.frank.search.domain;

/**
 * Created with IntelliJ IDEA.
 * User: frank
 * Date: 15-11-26
 * Time: 下午1:33
 * To change this template use File | Settings | File Templates.
 */
public class ProductRequestDto extends BaseRequest{

    //唯一ID
    private String id;

    //商品名称
    private String goodsName;

    //商店ID
    private String storesId;

    //门店名字
    private String storesName;

    //具体的分类
    private String productSort;

    //包装类型
    private String productPacType;

    //原价
    private float orPrice;

    //现价
    private float cuPrice;

    //日限量
    private long limit;

    //状态
    private String status;

    //促销类型ID
    private String promotionTypeId;

    //爆款的标识
    private String hotFlag;


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

    public String getProductSort() {
        return productSort;
    }

    public void setProductSort(String productSort) {
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

    public String getPromotionTypeId() {
        return promotionTypeId;
    }

    public void setPromotionTypeId(String promotionTypeId) {
        this.promotionTypeId = promotionTypeId;
    }

    public String getHotFlag() {
        return hotFlag;
    }

    public void setHotFlag(String hotFlag) {
        this.hotFlag = hotFlag;
    }

    @Override
    public String toString()
    {
        return String.format("<ProductRequestDto: %s, %s, %s, %s, %s, %s, " +
                "%s, %s, %s, %s, %s, %s>"
                ,id, goodsName, storesId, storesName, productSort, productPacType,
                orPrice, cuPrice, limit, status,promotionTypeId, hotFlag);
    }

}
