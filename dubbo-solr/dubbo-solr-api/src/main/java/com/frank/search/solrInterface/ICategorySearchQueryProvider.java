package com.frank.search.solrInterface;

import com.frank.search.transmitData.Request;
import com.frank.search.transmitData.ResponseDefault;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: frank
 * Date: 15-11-02
 * Time: 上午10:56
 * To change this template use File | Settings | File Templates.
 */
@Path("categorySearchQueryProvider")
public interface ICategorySearchQueryProvider {
	
	/**
     * HelloWorld测试
     *
     * @param request
     * @return
     */
    @POST
    @Path("searchCategoryGoods")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public ResponseDefault searchCategoryGoods(Request request) throws Exception;


    /**
     * HelloWorld测试
     *
     * @param request
     * @return
     */
    @POST
    @Path("searchCategory")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public ResponseDefault searchCategory(Request request) throws Exception;



    /**
     * HelloWorld测试
     *
     * @param request
     * @return
     */
    @POST
    @Path("searchGoods")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public void searchGoods(Request request);

}
