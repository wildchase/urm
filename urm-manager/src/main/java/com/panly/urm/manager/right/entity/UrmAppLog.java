package com.panly.urm.manager.right.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * entity:UrmAppLog
 * 
 * @author a@panly.me
 */
public class UrmAppLog implements Serializable {
	
	private static final long serialVersionUID = 6983109047294031823L;
	
	private Long	appLogId;		
	private String	appLogReqId;		 /* 请求 唯一id */ 
	private Long	appId;		
	private String	appName;		
	private String	appIp;		 /* app ip地址 */ 
	private Integer	logLevel;		 /* 日志level  1:info   2:error */ 
	private String	logType;		 /* 日志类型，1：普通日志，2：缓存日志，3：数据库日志 */ 
	private String	logContent;		
	private Date	createTime;		

	// Constructor
	public UrmAppLog() {
	}

	/**
	 * full Constructor
	 */
	public UrmAppLog(Long appLogId, String appLogReqId, Long appId, String appName, String appIp, Integer logLevel, String logType, String logContent, Date createTime) {
		this.appLogId = appLogId;
		this.appLogReqId = appLogReqId;
		this.appId = appId;
		this.appName = appName;
		this.appIp = appIp;
		this.logLevel = logLevel;
		this.logType = logType;
		this.logContent = logContent;
		this.createTime = createTime;
	}

	public Long getAppLogId() {
		return appLogId;
	}

	public void setAppLogId(Long appLogId) {
		this.appLogId = appLogId;
	}

	public String getAppLogReqId() {
		return appLogReqId;
	}

	public void setAppLogReqId(String appLogReqId) {
		this.appLogReqId = appLogReqId;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppIp() {
		return appIp;
	}

	public void setAppIp(String appIp) {
		this.appIp = appIp;
	}

	public Integer getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(Integer logLevel) {
		this.logLevel = logLevel;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "UrmAppLog [" + "appLogId=" + appLogId+ ", appLogReqId=" + appLogReqId+ ", appId=" + appId+ ", appName=" + appName+ ", appIp=" + appIp+ ", logLevel=" + logLevel+ ", logType=" + logType+ ", logContent=" + logContent+ ", createTime=" + createTime+  "]";
	}
}
