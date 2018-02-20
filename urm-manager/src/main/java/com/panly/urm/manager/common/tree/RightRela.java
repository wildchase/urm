package com.panly.urm.manager.common.tree;

import java.util.Date;
import java.util.List;

import com.panly.urm.manager.right.vo.DataRightVo;

public class RightRela {
	
	//若是存在，则有此操作的关联， 只对操作生效
	private Long relaId;
	
	private Long operId;
	
	//关联的类型，1 用户关联， 2角色关联
	private String relaType;
	
	//若是角色关联，则有角色的id和角色的名称   若是 acct关联，则是acctId 和acctName
	private String bizId;
	
	private String bizName;
	
	private Long createBy;
	
	private String createUserName;
	
	private Date createTime;
	
	//该角色拥有的权限
	private List<DataRightVo> rights;

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

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public List<DataRightVo> getRights() {
		return rights;
	}

	public void setRights(List<DataRightVo> rights) {
		this.rights = rights;
	}

}
