package com.panly.urm.manager.right.vo;

import java.util.Date;

public class AppLogVo {
	
	private Long	appLogId;		
	private String	appLogReqId;		 /* 请求 唯一id */ 
	private Long	appId;		
	private String	appName;		
	private String	appIp;		 /* app ip地址 */ 
	private Integer	logLevel;		 /* 日志level  1:info   2:error */ 
	private String	logType;		 /* 日志类型，1：普通日志，2：缓存日志，3：数据库日志 */ 
	private String	logContent;		
	private Date	createTime;
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
	
	
}
