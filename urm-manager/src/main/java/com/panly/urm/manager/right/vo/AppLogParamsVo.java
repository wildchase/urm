package com.panly.urm.manager.right.vo;

import com.panly.urm.manager.common.page.core.DataTablePageBase;

public class AppLogParamsVo  extends DataTablePageBase{
	
	private String	appLogReqId;		 /* 请求 唯一id */ 
	private Long	appId;	
	private String	appIp;		 /* app ip地址 */ 
	private Integer	logLevel;		 /* 日志level  1:info   2:error */ 
	private String	logType;		 /* 日志类型，1：普通日志，2：缓存日志，3：数据库日志 */
	
	private String	startCreateTime;
	private String	endCreateTime;
	
	
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
	public String getStartCreateTime() {
		return startCreateTime;
	}
	public void setStartCreateTime(String startCreateTime) {
		this.startCreateTime = startCreateTime;
	}
	public String getEndCreateTime() {
		return endCreateTime;
	}
	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
	
	
	
	
}
