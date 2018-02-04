package com.panly.urm.manager.right.vo;

import com.panly.urm.manager.common.page.core.DataTablePageBase;

public class OperParamsVo extends DataTablePageBase {
	
	private String operCode;
	private String operName;
	
	private Long appId; //应用
	private Long functionId; //查询功能下的操作
	
	private Integer  status;		 /* 状态*/
	private String	startCreateTime;
	private String	endCreateTime;
	
	private String deleteIds;
	
	private Long firstFunctionId;
	private Long secondFunctionId;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getDeleteIds() {
		return deleteIds;
	}
	public void setDeleteIds(String deleteIds) {
		this.deleteIds = deleteIds;
	}
	public String getOperCode() {
		return operCode;
	}
	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public Long getFunctionId() {
		return functionId;
	}
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}
	public Long getFirstFunctionId() {
		return firstFunctionId;
	}
	public void setFirstFunctionId(Long firstFunctionId) {
		this.firstFunctionId = firstFunctionId;
	}
	public Long getSecondFunctionId() {
		return secondFunctionId;
	}
	public void setSecondFunctionId(Long secondFunctionId) {
		this.secondFunctionId = secondFunctionId;
	}
	
}
