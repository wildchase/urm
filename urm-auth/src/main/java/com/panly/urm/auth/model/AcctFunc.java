package com.panly.urm.auth.model;

public class AcctFunc {
	
	private Long acctId;
	
	private Long funcId;
	
	private String funcCode;
	
	private String funcName;
	
	private Long parentFuncId;
	
	private Long appId;
	
	public Long getAcctId() {
		return acctId;
	}

	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}


	public Long getFuncId() {
		return funcId;
	}

	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}

	public Long getParentFuncId() {
		return parentFuncId;
	}

	public void setParentFuncId(Long parentFuncId) {
		this.parentFuncId = parentFuncId;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}
	
}
