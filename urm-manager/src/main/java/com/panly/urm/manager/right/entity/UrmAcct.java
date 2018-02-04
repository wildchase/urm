package com.panly.urm.manager.right.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * entity:UrmAcct
 * 
 * @author a@panly.me
 */
public class UrmAcct implements Serializable {
	
	private static final long serialVersionUID = -1312348714299396388L;
	
	private Long	acctId;		 /* 账号id */ 
	private String	acctName;		 /* 登陆账号名称 */ 
	private String	phone;		 /* 账号登陆 手机号码 */ 
	private String	email;		 /* 账号登陆email，邮箱地址 */ 
	private String	password;		
	private String	salt;		
	private String	lastLoginIp;		
	private Date	lastLoginTime;		
	private Integer	status;		 /* 1是正常， 0 代表冻结 */ 
	private Date	createTime;		
	private Date	updateTime;		
	private Long	createBy;		
	private Long	updateBy;		
	private Integer	recordStatus;		 /* 1，数据正常，0 代表数据删除 */ 

	// Constructor
	public UrmAcct() {
	}

	/**
	 * full Constructor
	 */
	public UrmAcct(Long acctId, String acctName, String phone, String email, String password, String salt, String lastLoginIp, Date lastLoginTime, Integer status, Date createTime, Date updateTime, Long createBy, Long updateBy, Integer recordStatus) {
		this.acctId = acctId;
		this.acctName = acctName;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.lastLoginIp = lastLoginIp;
		this.lastLoginTime = lastLoginTime;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createBy = createBy;
		this.updateBy = updateBy;
		this.recordStatus = recordStatus;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(Integer recordStatus) {
		this.recordStatus = recordStatus;
	}

	@Override
	public String toString() {
		return "UrmAcct [" + "acctId=" + acctId+ ", acctName=" + acctName+ ", phone=" + phone+ ", email=" + email+ ", password=" + password+ ", salt=" + salt+ ", lastLoginIp=" + lastLoginIp+ ", lastLoginTime=" + lastLoginTime+ ", status=" + status+ ", createTime=" + createTime+ ", updateTime=" + updateTime+ ", createBy=" + createBy+ ", updateBy=" + updateBy+ ", recordStatus=" + recordStatus+  "]";
	}
}
