package com.panly.urm.auth.model;

public class AcctRight {
	
	private Long acctId;
	
	private Long operId;
	
	private Long rightId;
	
	private String dataRightSql;
	
	private Long relaId;
	
	private String relaType;
	
	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public Long getAcctId() {
		return acctId;
	}

	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}

	public Long getRightId() {
		return rightId;
	}

	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}

	public String getDataRightSql() {
		return dataRightSql;
	}

	public void setDataRightSql(String dataRightSql) {
		this.dataRightSql = dataRightSql;
	}

	public Long getRelaId() {
		return relaId;
	}

	public void setRelaId(Long relaId) {
		this.relaId = relaId;
	}

	public String getRelaType() {
		return relaType;
	}

	public void setRelaType(String relaType) {
		this.relaType = relaType;
	}
	
}
