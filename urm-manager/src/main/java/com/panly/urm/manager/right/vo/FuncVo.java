package com.panly.urm.manager.right.vo;

import java.util.Date;

public class FuncVo {

	private Long functionId;
	private String functionCode;
	private String functionName;
	private String functionDesc;
	private Long appId;
	private Long parentFunctionId;
	private Integer status; /* 1是正常， 0 代表冻结 */
	private Date createTime;
	private Date updateTime;
	private Long createBy;
	private Long updateBy;

	private String createUserName;
	private String updateUserName;

	private String parentFunctionName;
	private String appName;


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

	public String getParentFunctionName() {
		return parentFunctionName;
	}

	public void setParentFunctionName(String parentFunctionName) {
		this.parentFunctionName = parentFunctionName;
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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
}
