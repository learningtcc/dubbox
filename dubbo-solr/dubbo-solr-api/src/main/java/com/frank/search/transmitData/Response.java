package com.frank.search.transmitData;


import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: frank
 * Date: 15-6-18
 * Time: 下午5:11
 * To change this template use File | Settings | File Templates.
 */

public interface Response<T> extends Cloneable, Serializable {

    public void setStatusCode(String statusCode);

    public String getStatusCode();


    public void setResult(T result);

    public T getResult(Class<?> c);

    public T getResult();

    public void setMsg(String msg);

    public String getMsg();

    public void setCostTime(long costTime);

    public long getCostTime();
}

