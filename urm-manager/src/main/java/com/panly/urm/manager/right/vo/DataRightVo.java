package com.panly.urm.manager.right.vo;

import java.util.Date;

public class DataRightVo {

	private Long	rightId;		
	private Long	relaId;		
	private Integer	relaType;		 /* 1，用户关联操作对应的数据权限   2，角色关联操作对应的数据权限 */ 
	private Integer	rightType;		 /* 1，数据链路   2，值集 */ 
	private String	dbCode;		 /* 数据库配置编号 */ 
	private String	dbName;		 /* 数据库配置名称 */ 
	private String	valueCode;		 /* 值集编号 */ 
	private String	valueName;		 /* 值集名称 */ 
	private String	valueConfig;		 /* 值集选中的项 逗号隔开 */ 
	private String	valueConfigName;	/* 值集选中的项 对应的值，逗号隔开 */ 
	private String	dataRightSql;		 /* 数据权限sql */ 
	private Integer	status;		 /* 1是正常， 0 代表冻结 */ 
	private Date	createTime;		
	private Date	updateTime;		
	private Long	createBy;		
	private Long	updateBy;		
	private Integer	recordStatus;		 /* 1，数据正常，0 代表数据删除 */ 
	
	private String  createUserName;
	private String  updateUserName;
	
	public Long getRightId() {
		return rightId;
	}
	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}
	public Long getRelaId() {
		return relaId;
	}
	public void setRelaId(Long relaId) {
		this.relaId = relaId;
	}
	public Integer getRelaType() {
		return relaType;
	}
	public void setRelaType(Integer relaType) {
		this.relaType = relaType;
	}
	
	public String getDataRightSql() {
		return dataRightSql;
	}
	public void setDataRightSql(String dataRightSql) {
		this.dataRightSql = dataRightSql;
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
	public Integer getRightType() {
		return rightType;
	}
	public void setRightType(Integer rightType) {
		this.rightType = rightType;
	}
	public String getDbCode() {
		return dbCode;
	}
	public void setDbCode(String dbCode) {
		this.dbCode = dbCode;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getValueCode() {
		return valueCode;
	}
	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public String getValueConfig() {
		return valueConfig;
	}
	public void setValueConfig(String valueConfig) {
		this.valueConfig = valueConfig;
	}
	public Integer getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(Integer recordStatus) {
		this.recordStatus = recordStatus;
	}
	public String getValueConfigName() {
		return valueConfigName;
	}
	public void setValueConfigName(String valueConfigName) {
		this.valueConfigName = valueConfigName;
	}
	
	
}
