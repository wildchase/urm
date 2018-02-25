package com.panly.urm.right.domain;

public class OperMethod {

	private Long operId;
	
	private Long functionId;
	
	private Long appId;

	private String operCode;

	private String operName;

	private Integer operState;
	
	
	public OperMethod() {
		super();
	}

	public OperMethod(Long operId, String operCode, String operName, Integer operState) {
		super();
		this.operId = operId;
		this.operCode = operCode;
		this.operName = operName;
		this.operState = operState;
	}

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
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

	public Integer getOperState() {
		return operState;
	}

	public void setOperState(Integer operState) {
		this.operState = operState;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}
	

}
