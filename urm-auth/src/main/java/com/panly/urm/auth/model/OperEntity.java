package com.panly.urm.auth.model;

public class OperEntity {

	private Long	operId;		
	private String	operCode;		
	private String	operName;
	
	private Integer	status;		 /* 1是正常， 0 代表冻结 */
	
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}	
	
}
