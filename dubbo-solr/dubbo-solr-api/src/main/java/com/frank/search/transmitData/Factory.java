package com.frank.search.transmitData;

/**
 * Created with IntelliJ IDEA.
 * User: frank
 * Date: 15-6-19
 * Time: 下午4:44
 * To change this template use File | Settings | File Templates.
 */
public interface Factory<T> {

    T create(Object... o);
    T create();
}
