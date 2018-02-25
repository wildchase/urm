package com.panly.urm.demo.entity;

import java.util.Date;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * entity:TestOrder
 * 
 * @author a@panly.me
 */
public class TestOrder implements Serializable {
	
	private static final long serialVersionUID = 4766983782831688257L;
	
	private Long	orderId;		
	private String	orderType;		
	private String	orderAreaId;		
	private Long	sellerId;		
	private Long	mallId;		
	private Long	buyerId;		
	private BigDecimal	orderAmount;		
	private Date	createTime;		

	// Constructor
	public TestOrder() {
	}

	/**
	 * full Constructor
	 */
	public TestOrder(Long orderId, String orderType, String orderAreaId, Long sellerId, Long mallId, Long buyerId, BigDecimal orderAmount, Date createTime) {
		this.orderId = orderId;
		this.orderType = orderType;
		this.orderAreaId = orderAreaId;
		this.sellerId = sellerId;
		this.mallId = mallId;
		this.buyerId = buyerId;
		this.orderAmount = orderAmount;
		this.createTime = createTime;
	}

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

	@Override
	public String toString() {
		return "TestOrder [" + "orderId=" + orderId+ ", orderType=" + orderType+ ", orderAreaId=" + orderAreaId+ ", sellerId=" + sellerId+ ", mallId=" + mallId+ ", buyerId=" + buyerId+ ", orderAmount=" + orderAmount+ ", createTime=" + createTime+  "]";
	}
}
