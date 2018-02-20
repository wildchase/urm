package com.panly.urm.manager.common.constants;

import java.util.HashMap;
import java.util.Map;

public enum OperTypeEnum {

	OPER_DEFAULT("default", "默认日志"), 
	
	//账户操作
	ACCT_ADD("acct_add", "账户新增"), 
	ACCT_EDIT("acct_edit", "账户修改"), 
	ACCT_DEL("acct_del", "账户删除"), 
	ACCT_ROLE_ADD("acct_role_add", "账户添加角色"),
	ACCT_ROLE_DEL("acct_role_del", "账户删除角色"),
	
	ACCT_OPER_ADD("acct_oper_add", "账户添加操作"),
	ACCT_OPER_DEL("acct_oper_del", "账户删除操作"),
	
	
	
	//角色操作
	ROLE_ADD("role_add", "角色新增"), 
	ROLE_EDIT("role_edit", "角色修改"), 
	ROLE_DEL("role_del", "角色删除"), 
	
	ROLE_ACCT_ADD("role_acct_add", "角色添加账户"),
	ROLE_ACCT_DEL("role_acct_del", "角色删除账户"),
	
	ROLE_OPER_ADD("role_oper_add", "角色添加操作"),
	ROLE_OPER_DEL("role_oper_del", "角色删除操作"),
	
	
	//应用
	APP_ADD("app_add", "应用新增"), 
	APP_EDIT("app_edit", "应用修改"), 
	APP_DEL("app_del", "应用删除"), 
	
	//功能
	FUNC_ADD("func_add", "功能新增"), 
	FUNC_EDIT("func_edit", "功能修改"), 
	FUNC_DEL("func_del", "功能删除"), 
		
		
	//操作
	OPER_ADD("func_add", "操作新增"), 
	OPER_EDIT("func_edit", "操作修改"), 
	OPER_DEL("func_del", "操作删除"), 
	
	
	;

	public static Map<String, OperTypeEnum> OPERTYPE_MAP = new HashMap<>();
	public static Map<String, String> OPERTYPE_DESC_MAP = new HashMap<>();
	static{
		OperTypeEnum[] u = OperTypeEnum.values();
		for (OperTypeEnum type : u) {
			OPERTYPE_MAP.put(type.getCode(),type);
			OPERTYPE_DESC_MAP.put(type.getCode(),type.getDesc());
		}
	}
	
	private String code;

	private String desc;

	private OperTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
