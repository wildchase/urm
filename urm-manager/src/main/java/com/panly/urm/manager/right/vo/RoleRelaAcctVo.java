package com.panly.urm.manager.right.vo;

import java.util.Date;

public class RoleRelaAcctVo {
	
	private Long relaId;
	private Long acctId;
	private Long roleId;
	
	private Date createTime;
	private Date updateTime;
	private Long createBy;
	private Long updateBy;

	private String createUserName;
	private String updateUserName;
	
	private String	acctName;		 /* 登陆账号名称 */ 
	private String	phone;		 /* 账号登陆 手机号码 */ 
	private String	email;		 /* 账号登陆email，邮箱地址 */ 
	private Integer	acctStatus;		 /* 1是正常， 0 代表冻结 */
	public Long getRelaId() {
		return relaId;
	}
	public void setRelaId(Long relaId) {
		this.relaId = relaId;
	}
	public Long getAcctId() {
		return acctId;
	}
	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
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
	public Integer getAcctStatus() {
		return acctStatus;
	}
	public void setAcctStatus(Integer acctStatus) {
		this.acctStatus = acctStatus;
	} 
	
	
}
