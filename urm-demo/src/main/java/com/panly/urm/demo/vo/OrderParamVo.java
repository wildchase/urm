package com.panly.urm.demo.vo;

import com.panly.urm.page.core.DataTablePageBase;

public class OrderParamVo extends DataTablePageBase{
	
	private Long orderId;
	private String	orderType;		
	private String	orderAreaId;		
	private Long	sellerId;		
	private Long	mallId;		
	private Long	buyerId;
	
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderAreaId() {
		return orderAreaId;
	}
	public void setOrderAreaId(String orderAreaId) {
		this.orderAreaId = orderAreaId;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public Long getMallId() {
		return mallId;
	}
	public void setMallId(Long mallId) {
		this.mallId = mallId;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	
	
}
