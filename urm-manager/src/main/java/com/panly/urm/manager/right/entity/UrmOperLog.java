package com.panly.urm.manager.right.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * entity:UrmOperLog
 * 
 * @author a@panly.me
 */
public class UrmOperLog implements Serializable {
	
	private static final long serialVersionUID = -3188691018897272249L;
	
	private Long	operLogId;		
	private Long	userId;		
	private String	userName;		
	private String	url;		 /* 访问地址 */ 
	private String	operType;		 /* 和 menu 中模块对应 */ 
	private String	operContent;		 /* 操作内容 */ 
	private Long	operCost;		 /* 耗费时间 */ 
	private Integer	success;		 /* 1，成功    2，失败 */ 
	private String	errorMsg;		 /* 失败的原因 */ 
	private Date	createTime;		

	// Constructor
	public UrmOperLog() {
	}

	/**
	 * full Constructor
	 */
	public UrmOperLog(Long operLogId, Long userId, String userName, String url, String operType, String operContent, Long operCost, Integer success, String errorMsg, Date createTime) {
		this.operLogId = operLogId;
		this.userId = userId;
		this.userName = userName;
		this.url = url;
		this.operType = operType;
		this.operContent = operContent;
		this.operCost = operCost;
		this.success = success;
		this.errorMsg = errorMsg;
		this.createTime = createTime;
	}

	public Long getOperLogId() {
		return operLogId;
	}

	public void setOperLogId(Long operLogId) {
		this.operLogId = operLogId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getOperContent() {
		return operContent;
	}

	public void setOperContent(String operContent) {
		this.operContent = operContent;
	}

	public Long getOperCost() {
		return operCost;
	}

	public void setOperCost(Long operCost) {
		this.operCost = operCost;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "UrmOperLog [" + "operLogId=" + operLogId+ ", userId=" + userId+ ", userName=" + userName+ ", url=" + url+ ", operType=" + operType+ ", operContent=" + operContent+ ", operCost=" + operCost+ ", success=" + success+ ", errorMsg=" + errorMsg+ ", createTime=" + createTime+  "]";
	}
}
