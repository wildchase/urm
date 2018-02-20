package com.panly.urm.manager.common.constants;

import java.util.HashMap;
import java.util.Map;

public enum OperSuccessEnum {

	SUCCESS(1,"成功"), //成功
	ERROR(0,"失败"), //失败
	; 
	
	public static Map<Integer, OperSuccessEnum> SUCCESS_MAP = new HashMap<>();
	public static Map<Integer, String> SUCCESS_DESC_MAP = new HashMap<>();
	static{
		OperSuccessEnum[] u = OperSuccessEnum.values();
		for (OperSuccessEnum x : u) {
			SUCCESS_MAP.put(x.getCode(),x);
			SUCCESS_DESC_MAP.put(x.getCode(),x.getDesc());
		}
	}
	
	private int code;
	
	private String desc;
	
	private OperSuccessEnum(int code, String desc) {
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
