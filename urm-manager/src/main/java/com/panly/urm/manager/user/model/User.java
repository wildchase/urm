package com.panly.urm.manager.user.model;


import javax.xml.bind.annotation.XmlAttribute;

/**
 * 用户
 * @author a@panly.me
 */
public class User {
	
	/** 用户id*/
	private Long userId;

	/** 账号*/
	private String account;
	
	/** 用户名*/
	private String userName;
	
	/** 密码*/
	private String password;
	

	@XmlAttribute
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@XmlAttribute
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@XmlAttribute
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@XmlAttribute
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
