package com.panly.urm.right.domain;

public class UserInfo {
	
	private Long acctId;

	private String acctName;
	
	private Integer acctState;


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

	public Integer getAcctState() {
		return acctState;
	}

	public void setAcctState(Integer acctState) {
		this.acctState = acctState;
	}
	
}
