package com.panly.urm.manager.right.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * entity:UrmApp
 * 
 * @author a@panly.me
 */
public class UrmApp implements Serializable {
	
	private static final long serialVersionUID = 6563099706131710516L;
	
	private Long	appId;		
	private String	appCode;		
	private String	appName;		
	private Integer	status;		 /* 1是正常， 0 代表冻结 */ 
	private Date	createTime;		
	private Date	updateTime;		
	private Long	createBy;		
	private Long	updateBy;		
	private Integer	recordStatus;		 /* 1，数据正常，0 代表数据删除 */ 

	// Constructor
	public UrmApp() {
	}

	/**
	 * full Constructor
	 */
	public UrmApp(Long appId, String appCode, String appName, Integer status, Date createTime, Date updateTime, Long createBy, Long updateBy, Integer recordStatus) {
		this.appId = appId;
		this.appCode = appCode;
		this.appName = appName;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createBy = createBy;
		this.updateBy = updateBy;
		this.recordStatus = recordStatus;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
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
		return "UrmApp [" + "appId=" + appId+ ", appCode=" + appCode+ ", appName=" + appName+ ", status=" + status+ ", createTime=" + createTime+ ", updateTime=" + updateTime+ ", createBy=" + createBy+ ", updateBy=" + updateBy+ ", recordStatus=" + recordStatus+  "]";
	}
}
