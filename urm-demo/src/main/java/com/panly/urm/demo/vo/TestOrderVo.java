package com.panly.urm.demo.vo;

import java.math.BigDecimal;
import java.util.Date;

public class TestOrderVo {

	private Long	orderId;		
	private String	orderType;		
	private String	orderAreaId;		
	private Long	sellerId;		
	private Long	mallId;		
	private Long	buyerId;		
	private BigDecimal	orderAmount;		
	private Date	createTime;
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
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}		
	
	
}
