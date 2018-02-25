package com.panly.urm.manager.right.vo;

import com.panly.urm.page.core.DataTablePageBase;

public class OperLogParamsVo  extends DataTablePageBase{
	
	private String	userName;		
	private String	url;		 /* 访问地址 */ 
	private String	operType;		 /* 和 menu 中模块对应 */ 
	private String	startCreateTime;
	private String	endCreateTime;
	private Integer success;
	
	public String getStartCreateTime() {
		return startCreateTime;
	}
	public void setStartCreateTime(String startCreateTime) {
		this.startCreateTime = startCreateTime;
	}
	public String getEndCreateTime() {
		return endCreateTime;
	}
	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
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
	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}
	
	
	
}
