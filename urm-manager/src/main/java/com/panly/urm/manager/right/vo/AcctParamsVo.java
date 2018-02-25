package com.panly.urm.manager.right.vo;

import com.panly.urm.page.core.DataTablePageBase;

//import com.panly.urm.page.core.DataTablePageBase;


public class AcctParamsVo extends DataTablePageBase {
	
	private Long	acctId;		 /* 账号id */ 
	private String	acctName;		 /* 登陆账号名称 */ 
	private String	phone;		 /* 账号登陆 手机号码 */ 
	private String	email;		 /* 账号登陆email，邮箱地址 */ 
	private Integer  status;		 /* 状态*/
	private String	startCreateTime;
	private String	endCreateTime;
	
	private String deleteIds;
	
	private String roleName;
	
	private String roleCode;
	
	/**
	 * 添加的roleId
	 */
	private String roleIds;
	
	//添加操作
	private Long operId;
	
	//删除关联
	private Long relaId;
	
	//删除关联类型
	private String relaType;
	
	/**
	 * 选中的nodeId
	 */
	private String chooseNodeId;
	
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
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public Long getRelaId() {
		return relaId;
	}
	public void setRelaId(Long relaId) {
		this.relaId = relaId;
	}
	public String getRelaType() {
		return relaType;
	}
	public void setRelaType(String relaType) {
		this.relaType = relaType;
	}
	public String getChooseNodeId() {
		return chooseNodeId;
	}
	public void setChooseNodeId(String chooseNodeId) {
		this.chooseNodeId = chooseNodeId;
	}	
	
	
}
