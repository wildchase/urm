package com.panly.urm.manager.right.vo;

import java.util.Date;

public class AuthLogVo {
	
	private Long	authLogId;		
	private Long	appId;		
	private String	appName;		
	private Long	acctId;		
	private String	acctName;		
	private String	authType;		 /* 鉴权的类型，1 登陆，2,操作，3,功能 */ 
	private String	authParam;		 /* 鉴权参数 */ 
	private Long	authCost;		 /* 耗费时间 */ 
	private Integer	success;		 /* 1，成功    2，失败 */ 
	private String	authRet;		 /* 鉴权返回 */ 
	private Date	createTime;
	
	
	public Long getAuthLogId() {
		return authLogId;
	}
	public void setAuthLogId(Long authLogId) {
		this.authLogId = authLogId;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Long getAcctId() {
		return acctId;
	}
	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public String getAuthParam() {
		return authParam;
	}
	public void setAuthParam(String authParam) {
		this.authParam = authParam;
	}
	public Long getAuthCost() {
		return authCost;
	}
	public void setAuthCost(Long authCost) {
		this.authCost = authCost;
	}
	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}
	public String getAuthRet() {
		return authRet;
	}
	public void setAuthRet(String authRet) {
		this.authRet = authRet;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}		
	
	
}
