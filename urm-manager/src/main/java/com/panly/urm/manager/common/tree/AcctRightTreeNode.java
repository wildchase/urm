package com.panly.urm.manager.common.tree;

import java.util.Date;
import java.util.List;

public class AcctRightTreeNode extends TreeNode{
	
	//可能关联有多个
	private List<RightRela> relas;
	
	private String operName;
	
	private Long functionId;
	
	private String functionCode;
	
	private String functionName;
	
	//是否拥有账户授权
	private boolean haveAcctOper;
	
	//是否拥有账户授权Id
	private Long acctOperRelaId;
	
	//账号授权人
	private Long createBy;
	
	//授权时间
	private String createUserName;
	
	private Date createTime;
	
	public AcctRightTreeNode() {
		super();
	}

	public AcctRightTreeNode(String id, String code, String text, String type) {
		super(id, code, text, type);
	}

	public List<RightRela> getRelas() {
		return relas;
	}

	public void setRelas(List<RightRela> relas) {
		this.relas = relas;
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

	public boolean isHaveAcctOper() {
		return haveAcctOper;
	}

	public void setHaveAcctOper(boolean haveAcctOper) {
		this.haveAcctOper = haveAcctOper;
	}

	public Long getAcctOperRelaId() {
		return acctOperRelaId;
	}

	public void setAcctOperRelaId(Long acctOperRelaId) {
		this.acctOperRelaId = acctOperRelaId;
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
	
	

}
