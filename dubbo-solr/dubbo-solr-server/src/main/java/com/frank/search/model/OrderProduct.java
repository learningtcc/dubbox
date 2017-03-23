package com.frank.search.model;

import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA. User: frank Date: 15-11-4 Time: 下午3:24 To change
 * this template use File | Settings | File Templates.
 */
@Service
public class OrderProduct {

	private String id;

	private String orderId;

	private String userName;

	private String productId;

	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
