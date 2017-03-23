package com.frank.search.domain;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: frank
 * Date: 15-11-24
 * Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
public class PageProductResponse<T>{

    public long totalPages;

    public List<T> productResponseList;

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getProductResponseList() {
        return productResponseList;
    }

    public void setProductResponseList(List<T> productResponseList) {
        this.productResponseList = productResponseList;
    }
}
