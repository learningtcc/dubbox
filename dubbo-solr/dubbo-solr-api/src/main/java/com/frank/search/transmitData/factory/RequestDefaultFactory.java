package com.frank.search.transmitData.factory;

import com.frank.search.transmitData.Request;
import com.frank.search.transmitData.RequestDefault;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: frank
 * Date: 15-6-18
 * Time: 下午6:54
 * To change this template use File | Settings | File Templates.
 */
@Component
public class RequestDefaultFactory<T> {

    /**
     * 获取UUID
     * @return String
     */
    private String getUUID()
    {
        String s = UUID.randomUUID().toString();
        // 去掉“-”符号
        return s.replaceAll("-", "");
    }

    /**
     * 生成traceId
     * @return
     */
    private String generateTraceId(){
        return getUUID();
    }

    public Request create(T content, String source){
        Request nr = new RequestDefault();
        nr.setContent(content);
        nr.setTraceId(generateTraceId());
        nr.setSource(source);
        return nr;
    }

    public Request create(String source){
        Request nr = new RequestDefault();
        nr.setTraceId(generateTraceId());
        nr.setSource(source);
        return nr;
    }

    public Request create(Request request){
        if (null == request){
            request = new RequestDefault();
        }
        request.setTraceId(generateTraceId());
        return request;
    }
}
