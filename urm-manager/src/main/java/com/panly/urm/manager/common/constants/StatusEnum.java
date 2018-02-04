package com.panly.urm.manager.common.constants;

import java.util.HashMap;
import java.util.Map;

public enum StatusEnum {

	NORMAL(1,"正常"), //正常
	DISABLED(0,"禁用"),//禁用
	;
	
	public static Map<Integer, StatusEnum> STATUS_MAP = new HashMap<>();
	public static Map<Integer, String> STATUS_DESC_MAP = new HashMap<>();
	static{
		StatusEnum[] u = StatusEnum.values();
		for (StatusEnum statusEnum : u) {
			STATUS_MAP.put(statusEnum.getCode(),statusEnum);
			STATUS_DESC_MAP.put(statusEnum.getCode(),statusEnum.getDesc());
		}
	}
	
	private int code;
	
	private String desc;
	
	private StatusEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
}
