package com.panly.urm.manager.common.constants;

import java.util.HashMap;
import java.util.Map;

public enum RelaTypeEnum {

	ACCT_REAL("1","账户关联"), 
	ROLE_REAL("2","角色关联"), 
	; 
	
	public static Map<String, RelaTypeEnum> RELATYPE_MAP = new HashMap<>();
	public static Map<String, String> RELATYPE_DESC_MAP = new HashMap<>();
	static{
		RelaTypeEnum[] u = RelaTypeEnum.values();
		for (RelaTypeEnum x : u) {
			RELATYPE_MAP.put(x.getCode(),x);
			RELATYPE_DESC_MAP.put(x.getCode(),x.getDesc());
		}
	}
	
	private String code;
	
	private String desc;
	
	private RelaTypeEnum(String code, String desc) {
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
