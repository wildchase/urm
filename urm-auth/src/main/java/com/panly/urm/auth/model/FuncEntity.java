package com.panly.urm.auth.model;

public class FuncEntity {
	
	private Long	functionId;		
	private String	functionCode;		
	private String	functionName;		
	private String	functionDesc;		
	private Long	appId;		
	private Long	parentFunctionId;		
	private Integer	status;		 /* 1是正常， 0 代表冻结 */
	
	public Long getFunctionId() {
		return functionId;
	}
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}
	public String getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getFunctionDesc() {
		return functionDesc;
	}
	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public Long getParentFunctionId() {
		return parentFunctionId;
	}
	public void setParentFunctionId(Long parentFunctionId) {
		this.parentFunctionId = parentFunctionId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	} 
	
}
