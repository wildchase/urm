package com.panly.urm.manager.common.tree;

import java.util.Date;
import java.util.List;

import com.panly.urm.manager.right.vo.DataRightVo;

public class RoleRightTreeNode extends TreeNode{
	
	private String operName;
	
	private Long functionId;
	
	private String functionCode;
	
	private String functionName;
	
	//是否拥有账户授权Id
	private Long operRelaId;
	
	//账号授权人
	private Long createBy;
	
	//授权时间
	private String createUserName;
	
	private Date createTime;
	
	private List<DataRightVo> rights;
	
	public RoleRightTreeNode() {
		super();
	}

	public RoleRightTreeNode(String id, String code, String text, String type) {
		super(id, code, text, type);
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

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
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

	public Long getOperRelaId() {
		return operRelaId;
	}

	public void setOperRelaId(Long operRelaId) {
		this.operRelaId = operRelaId;
	}

	public List<DataRightVo> getRights() {
		return rights;
	}

	public void setRights(List<DataRightVo> rights) {
		this.rights = rights;
	}

	
}
