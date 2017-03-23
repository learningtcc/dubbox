package com.frank.search.transmitData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created with IntelliJ IDEA.
 * User: frank
 * Date: 15-4-27
 * Time: 下午2:32
 * public common used result
 */
public class ResponseDefault<T> implements Response<T> {

    /**
     * 状态码
     */
    private String statusCode= "200";

    /**
     * 描述信息
     */
    private String msg= "OK";

    @JSONField(serialzeFeatures = {SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteMapNullValue,SerializerFeature.BrowserCompatible}, parseFeatures={Feature.UseBigDecimal,Feature.SupportArrayToBean})
    private T result;

    private long costTime;

    public ResponseDefault(){}
    public ResponseDefault(int statusCode, String msg){
        this.statusCode = String.valueOf(statusCode);
        this.msg=msg;
    }
    public ResponseDefault(String statusCode, T result){
        this.statusCode = statusCode;
        this.result = result;
    }

    public String getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public T getResult() {
        return result;
    }

    public T getResult(Class<?> c){
        return (T)JSON.parseObject(result.toString(),c);
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCostTime() {
        return costTime;
    }
    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }
    
    @Override
    public String toString() {
        return String.format("<ResponseResult: statusCode=%s, msg=%s, result=%s, costTime=%s>",
                statusCode, msg, result, costTime);
    }
}
