package com.panly.urm.manager.common.constants;

public enum RecordStatusEnum {

	NORMAL(1,"未删除"), //正常
	DELETED(0,"删除"),//禁用
	;
	
	private int code;
	
	private String desc;
	
	private RecordStatusEnum(int code, String desc) {
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
