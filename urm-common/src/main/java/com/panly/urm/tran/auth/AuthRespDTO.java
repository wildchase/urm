package com.panly.urm.tran.auth;

import java.util.List;

public class AuthRespDTO {
	
	private Long operId;
	
	private String operCode;
	
	private String operName;
	
	private List<String> rightSqls;


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

	public List<String> getRightSqls() {
		return rightSqls;
	}

	public void setRightSqls(List<String> rightSqls) {
		this.rightSqls = rightSqls;
	}
	
	

}
