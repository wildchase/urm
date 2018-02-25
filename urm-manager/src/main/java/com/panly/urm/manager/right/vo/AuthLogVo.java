package com.panly.urm.manager.right.vo;

import java.util.Date;

public class AuthLogVo {
	
	private Long	authLogId;		
	private String	appCode;		
	private String	appName;		
	private Long	acctId;		
	private String	acctName;		
	private String	operCode;		
	private String	operName;		
	private Integer	success;		 /* 1，成功    2，失败 */ 
	private String	dataRight;		
	private Long	authCost;		 /* 耗费时间 */ 
	private String	reqIp;		
	private Date	createTime;
	public Long getAuthLogId() {
		return authLogId;
	}
	public void setAuthLogId(Long authLogId) {
		this.authLogId = authLogId;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
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
	public String getOperCode() {
		return operCode;
	}
	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}
	public String getDataRight() {
		return dataRight;
	}
	public void setDataRight(String dataRight) {
		this.dataRight = dataRight;
	}
	public Long getAuthCost() {
		return authCost;
	}
	public void setAuthCost(Long authCost) {
		this.authCost = authCost;
	}
	public String getReqIp() {
		return reqIp;
	}
	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}		
	
	
}
