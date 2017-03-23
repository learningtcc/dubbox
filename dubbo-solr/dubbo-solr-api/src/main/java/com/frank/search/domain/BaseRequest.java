package com.frank.search.domain;

/**
 * Created with IntelliJ IDEA.
 * User: frank
 * Date: 15-11-30
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
public class BaseRequest {
    public Integer start;

    public Integer rows;

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
