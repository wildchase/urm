package com.panly.urm.manager.right.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * entity:UrmDataRight
 * 
 * @author a@panly.me
 */
public class UrmDataRight implements Serializable {
	
	private static final long serialVersionUID = 4000835264522412L;
	
	private Long	rightId;		
	private Long	relaId;		
	private Integer	relaType;		 /* 1，用户关联操作对应的数据权限   2，角色关联操作对应的数据权限 */ 
	private String	dataRightTableKey;		
	private String	dataRightSql;		
	private Integer	status;		 /* 1是正常， 0 代表冻结 */ 
	private Date	createTime;		
	private Date	updateTime;		
	private Long	createBy;		
	private Long	updateBy;		
	private Integer	recordStatus;		 /* 1，数据正常，0 代表数据删除 */ 

	// Constructor
	public UrmDataRight() {
	}

	/**
	 * full Constructor
	 */
	public UrmDataRight(Long rightId, Long relaId, Integer relaType, String dataRightTableKey, String dataRightSql, Integer status, Date createTime, Date updateTime, Long createBy, Long updateBy, Integer recordStatus) {
		this.rightId = rightId;
		this.relaId = relaId;
		this.relaType = relaType;
		this.dataRightTableKey = dataRightTableKey;
		this.dataRightSql = dataRightSql;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createBy = createBy;
		this.updateBy = updateBy;
		this.recordStatus = recordStatus;
	}

	public Long getRightId() {
		return rightId;
	}

	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}

	public Long getRelaId() {
		return relaId;
	}

	public void setRelaId(Long relaId) {
		this.relaId = relaId;
	}

	public Integer getRelaType() {
		return relaType;
	}

	public void setRelaType(Integer relaType) {
		this.relaType = relaType;
	}

	public String getDataRightTableKey() {
		return dataRightTableKey;
	}

	public void setDataRightTableKey(String dataRightTableKey) {
		this.dataRightTableKey = dataRightTableKey;
	}

	public String getDataRightSql() {
		return dataRightSql;
	}

	public void setDataRightSql(String dataRightSql) {
		this.dataRightSql = dataRightSql;
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
		return "UrmDataRight [" + "rightId=" + rightId+ ", relaId=" + relaId+ ", relaType=" + relaType+ ", dataRightTableKey=" + dataRightTableKey+ ", dataRightSql=" + dataRightSql+ ", status=" + status+ ", createTime=" + createTime+ ", updateTime=" + updateTime+ ", createBy=" + createBy+ ", updateBy=" + updateBy+ ", recordStatus=" + recordStatus+  "]";
	}
}
