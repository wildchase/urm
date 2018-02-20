package com.panly.urm.manager.right.entity;

import java.io.Serializable;

/**
 * entity:UrmRightDbConfig
 * 
 * @author gen
 * @date 2018-2-19
 */
public class UrmRightDbConfig implements Serializable {
	
	private static final long serialVersionUID = 3728833694655451995L;
	
	private String	dbCode;		 /* 数据库配置编号 */ 
	private String	dbName;		 /* 配置名称 */ 
	private String	dbIntru;		 /* 配置介绍 */ 
	private String	dbUrl;		 /* url地址 */ 
	private String	dbClass;		 /* 数据库class名称 */ 
	private String	dbUsername;		 /* 用户名 */ 
	private String	dbPassword;		 /* 密码 */ 
	private Integer	recordStatus;		 /* 1，数据正常，0 代表数据删除 */ 

	// Constructor
	public UrmRightDbConfig() {
	}

	/**
	 * full Constructor
	 */
	public UrmRightDbConfig(String dbCode, String dbName, String dbIntru, String dbUrl, String dbClass, String dbUsername, String dbPassword, Integer recordStatus) {
		this.dbCode = dbCode;
		this.dbName = dbName;
		this.dbIntru = dbIntru;
		this.dbUrl = dbUrl;
		this.dbClass = dbClass;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
		this.recordStatus = recordStatus;
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

	public String getDbIntru() {
		return dbIntru;
	}

	public void setDbIntru(String dbIntru) {
		this.dbIntru = dbIntru;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbClass() {
		return dbClass;
	}

	public void setDbClass(String dbClass) {
		this.dbClass = dbClass;
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public Integer getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(Integer recordStatus) {
		this.recordStatus = recordStatus;
	}

	@Override
	public String toString() {
		return "UrmRightDbConfig [" + "dbCode=" + dbCode+ ", dbName=" + dbName+ ", dbIntru=" + dbIntru+ ", dbUrl=" + dbUrl+ ", dbClass=" + dbClass+ ", dbUsername=" + dbUsername+ ", dbPassword=" + dbPassword+ ", recordStatus=" + recordStatus+  "]";
	}
}
