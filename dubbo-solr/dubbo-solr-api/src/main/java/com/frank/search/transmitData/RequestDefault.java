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
public class RequestDefault<T> implements Request<T> {

    /**
     * 跟踪ID
     */
    private String traceId;

    /**
     * 请求参数
     */
    @JSONField(name="content",serialzeFeatures={SerializerFeature.WriteClassName,SerializerFeature.BrowserCompatible}, parseFeatures={Feature.UseBigDecimal,Feature.SupportArrayToBean})
    private T content;

    private String source;

    public RequestDefault(){}

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public T getContent(Class<?> c){
        return (T)JSON.parseObject(content.toString(),c);
    }

    /*public T getContent(TypeReffere)   */

    public T getContent(){
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return String.format("< RequestData: traceId=%s, content=%s >", traceId, content);
    }
}
