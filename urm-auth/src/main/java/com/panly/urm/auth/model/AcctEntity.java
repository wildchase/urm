package com.panly.urm.auth.model;

import java.util.Date;

public class AcctEntity {
	
	private Long	acctId;		 /* 账号id */ 
	private String	acctName;		 /* 登陆账号名称 */ 
	private String	phone;		 /* 账号登陆 手机号码 */ 
	private String	email;		 /* 账号登陆email，邮箱地址 */ 
	private String	password;		
	private String	salt;		
	private String	lastLoginIp;		
	private Date	lastLoginTime;		
	private Integer	status;		 /* 1是正常， 0 代表冻结 */ 
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	} 
	
	
	

}
