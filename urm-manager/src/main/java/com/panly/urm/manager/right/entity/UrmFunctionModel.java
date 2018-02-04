package com.panly.urm.manager.right.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * entity:UrmFunctionModel
 * 
 * @author a@panly.me
 */
public class UrmFunctionModel implements Serializable {
	
	private static final long serialVersionUID = -9071959849630981102L;
	
	private Long	functionId;		
	private String	functionCode;		
	private String	functionName;		
	private String	functionDesc;		
	private Long	appId;		
	private Long	parentFunctionId;		
	private Integer	status;		 /* 1是正常， 0 代表冻结 */ 
	private Date	createTime;		
	private Date	updateTime;		
	private Long	createBy;		
	private Long	updateBy;		
	private Integer	recordStatus;		 /* 1，数据正常，0 代表数据删除 */ 

	// Constructor
	public UrmFunctionModel() {
	}

	/**
	 * full Constructor
	 */
	public UrmFunctionModel(Long functionId, String functionCode, String functionName, String functionDesc, Long appId, Long parentFunctionId, Integer status, Date createTime, Date updateTime, Long createBy, Long updateBy, Integer recordStatus) {
		this.functionId = functionId;
		this.functionCode = functionCode;
		this.functionName = functionName;
		this.functionDesc = functionDesc;
		this.appId = appId;
		this.parentFunctionId = parentFunctionId;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createBy = createBy;
		this.updateBy = updateBy;
		this.recordStatus = recordStatus;
	}

	
	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionDesc() {
		return functionDesc;
	}

	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getParentFunctionId() {
		return parentFunctionId;
	}

	public void setParentFunctionId(Long parentFunctionId) {
		this.parentFunctionId = parentFunctionId;
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
		return "UrmFunctionModel [" + "funtionId=" + functionId+ ", functionCode=" + functionCode+ ", functionName=" + functionName+ ", functionDesc=" + functionDesc+ ", appId=" + appId+ ", parentFunctionId=" + parentFunctionId+ ", status=" + status+ ", createTime=" + createTime+ ", updateTime=" + updateTime+ ", createBy=" + createBy+ ", updateBy=" + updateBy+ ", recordStatus=" + recordStatus+  "]";
	}
}
