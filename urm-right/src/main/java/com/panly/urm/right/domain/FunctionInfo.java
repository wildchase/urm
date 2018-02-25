package com.panly.urm.right.domain;

import java.util.List;

public class FunctionInfo {
	
	private Long functionId;
	
	private String functionCode;
	
	private String functionName;
	
	private Integer functionState;
	
	private String parentCode;
	
	private List<FunctionInfo> child;

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

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public Integer getFunctionState() {
		return functionState;
	}

	public void setFunctionState(Integer functionState) {
		this.functionState = functionState;
	}

	public List<FunctionInfo> getChild() {
		return child;
	}

	public void setChild(List<FunctionInfo> child) {
		this.child = child;
	}

	
}
