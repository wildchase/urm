package com.panly.urm.manager.right.vo;

import com.panly.urm.manager.common.page.core.DataTablePageBase;

public class RoleParamsVo extends DataTablePageBase {

	private Long roleId;
	private String roleCode;
	private String roleName;
	private Integer status; /* 状态 */
	private String startCreateTime;
	private String endCreateTime;

	private String deleteIds;

	private String acctName;

	private String acctIds;

	// 添加操作
	private Long operId;

	// 删除关联
	private Long relaId;

	// 删除关联类型
	private String relaType;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getDeleteIds() {
		return deleteIds;
	}

	public void setDeleteIds(String deleteIds) {
		this.deleteIds = deleteIds;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getAcctIds() {
		return acctIds;
	}

	public void setAcctIds(String acctIds) {
		this.acctIds = acctIds;
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

}
