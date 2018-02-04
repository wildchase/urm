package com.panly.urm.manager.right.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * entity:UrmRoleRelaOper
 * 
 * @author a@panly.me
 */
public class UrmRoleRelaOper implements Serializable {
	
	private static final long serialVersionUID = 6181006969767001312L;
	
	private Long	relaId;		
	private Long	roleId;		
	private Long	operId;		
	private Date	createTime;		
	private Date	updateTime;		
	private Long	createBy;		
	private Long	updateBy;		
	private Integer	recordStatus;		 /* 1，数据正常，0 代表数据删除 */ 

	// Constructor
	public UrmRoleRelaOper() {
	}

	/**
	 * full Constructor
	 */
	public UrmRoleRelaOper(Long relaId, Long roleId, Long operId, Date createTime, Date updateTime, Long createBy, Long updateBy, Integer recordStatus) {
		this.relaId = relaId;
		this.roleId = roleId;
		this.operId = operId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createBy = createBy;
		this.updateBy = updateBy;
		this.recordStatus = recordStatus;
	}

	public Long getRelaId() {
		return relaId;
	}

	public void setRelaId(Long relaId) {
		this.relaId = relaId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
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
		return "UrmRoleRelaOper [" + "relaId=" + relaId+ ", roleId=" + roleId+ ", operId=" + operId+ ", createTime=" + createTime+ ", updateTime=" + updateTime+ ", createBy=" + createBy+ ", updateBy=" + updateBy+ ", recordStatus=" + recordStatus+  "]";
	}
}
