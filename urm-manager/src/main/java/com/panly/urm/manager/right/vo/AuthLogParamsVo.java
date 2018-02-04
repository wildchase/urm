package com.panly.urm.manager.right.vo;

import com.panly.urm.manager.common.page.core.DataTablePageBase;

public class AuthLogParamsVo  extends DataTablePageBase{
	
	private Long	appId;		
	private Long	acctId;		
	private String	authType;		 /* 鉴权的类型，1 登陆，2,操作，3,功能 */
	
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
	
	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public Long getAcctId() {
		return acctId;
	}
	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	
	
}
