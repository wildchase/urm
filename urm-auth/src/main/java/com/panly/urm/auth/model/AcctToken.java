package com.panly.urm.auth.model;


import java.util.Date;

/**
 * 存储的token 对象
 * @author a@panly.me
 */
public class AcctToken {
	
	private String token;
	
	private Long acctId;
	
	private String acctName;
	
	private Date createTime;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	} 

}
