package com.panly.urm.manager.user.model;


import java.util.Date;

/**
 * 存储的token 对象
 * @author a@panly.me
 */
public class Token {
	
	private String token;
	
	private Long userId;
	
	private String account;
	
	private String userName;
	
	private Date createTime;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	} 

}
