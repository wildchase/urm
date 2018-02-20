package com.panly.urm.manager.right.vo;

import java.util.Date;

public class OperLogVo {
	
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
	
	private String successName;
	private String operTypeName;
	
	
	public String getSuccessName() {
		return successName;
	}
	public void setSuccessName(String successName) {
		this.successName = successName;
	}
	public String getOperTypeName() {
		return operTypeName;
	}
	public void setOperTypeName(String operTypeName) {
		this.operTypeName = operTypeName;
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
	
}
