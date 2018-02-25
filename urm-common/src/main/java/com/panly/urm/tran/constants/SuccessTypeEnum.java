package com.panly.urm.tran.constants;

import java.util.HashMap;
import java.util.Map;

public enum SuccessTypeEnum {
	
	SUCCESS(1,"成功"),
	
	ERROR(0,"失败"),
	;
	
	public static Map<Integer, SuccessTypeEnum> SUCCESS_MAP = new HashMap<>();
	public static Map<Integer, String> SUCCESS_DESC_MAP = new HashMap<>();
	static{
		SuccessTypeEnum[] u = SuccessTypeEnum.values();
		for (SuccessTypeEnum type : u) {
			SUCCESS_MAP.put(type.getCode(),type);
			SUCCESS_DESC_MAP.put(type.getCode(),type.getDesc());
		}
	}

	private Integer code;

	private String desc;

	private SuccessTypeEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
