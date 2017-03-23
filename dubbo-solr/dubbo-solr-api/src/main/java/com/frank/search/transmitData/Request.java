package com.frank.search.transmitData;

import java.io.Serializable;

/**
*
* Created with IntelliJ IDEA.
* User: frank
* Date: 15-6-18
* Time: 下午7:14
* To change this template use File | Settings | File Templates.
*/
public interface Request<T> extends Cloneable, Serializable {
    /**
     * 获取 traceId
     * @return
    */
    String getTraceId();

    /**
     * 根据Class
     * 获取具体的对象数据
     *
     * @param c
     * @return
     */
    public T getContent(Class<?> c);


    /**
     * 获取实际内容
     * @return
    */
    public T getContent();

    /**
     * 设置内容
     * @param content
     */
    public void setContent(T content);

    /**
     * 设置traceId
     * @param traceId
     */
    public void setTraceId(String traceId);

    /**
     * 设置资源
     * @param source
     */
    public void setSource(String source);

    /**
     * 获取资源
     * @return
     */
    public String getSource();

}
