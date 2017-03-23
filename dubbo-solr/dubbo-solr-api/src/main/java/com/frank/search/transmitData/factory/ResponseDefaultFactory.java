package com.frank.search.transmitData.factory;

import com.frank.search.transmitData.Response;
import com.frank.search.transmitData.ResponseDefault;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: frank
 * Date: 15-6-18
 * Time: 下午6:54
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ResponseDefaultFactory<T> {

    /**
     * 获取成功的返回响应信息
     *
     * @param result
     * @return
     */
    public Response createSuccess(T result){
        Response nr = new ResponseDefault();
        Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success",result);
        ((ResponseDefault)nr).setResult(resultMap);
        return nr;
    }

    /**
     * 获取成功的返回响应信息
     *
     * @param result
     * @return
     */
    public ResponseDefault createResult(T result){
        ResponseDefault nr = new ResponseDefault();
        nr.setResult(result);
        return nr;
    }

    /**
     * 获取成功的返回响应信息
     *
     * @return
     */
    public Response createSuccess(){
        Response nr = new ResponseDefault();
        ((ResponseDefault)nr).setResult(new HashMap<String, Object>());
        return nr;
    }

    /**
     * 获取异常返回的结果集
     *
     * @param result
     * @param errorCode
     * @param errorMsg
     * @return
     */
    public Response createException(T result, String errorCode, String errorMsg){
        Response nr = new ResponseDefault();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("exception",result);
        ((ResponseDefault)nr).setResult(resultMap);
        ((ResponseDefault)nr).setStatusCode(errorCode);
        ((ResponseDefault)nr).setMsg(errorMsg);
        return nr;
    }

    /**
     * 获取异常返回的结果集
     *
     * @param errorCode
     * @param errorMsg
     * @return
     */
    public Response createException(String errorCode, String errorMsg){
        Response nr = new ResponseDefault();
        ((ResponseDefault)nr).setResult(new HashMap<String, Object>());
        ((ResponseDefault)nr).setStatusCode(errorCode);
        ((ResponseDefault)nr).setMsg(errorMsg);
        return nr;
    }

}
