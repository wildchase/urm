package com.panly.urm.manager.right.entity;

import java.io.Serializable;

/**
 * entity:UrmRightValueSetConfig
 * 
 * @author gen
 * @date 2018-2-19
 */
public class UrmRightValueSetConfig implements Serializable {
	
	private static final long serialVersionUID = 8314518687329009169L;
	
	private String	valueCode;		 /* 值集配置编号 */ 
	private String	valueName;		 /* 值集配置名称 */ 
	private String	valueIntru;		 /* 值集配置介绍 */ 
	private String	valueTableName;		 /* 值集对应的table */ 
	private String	valueTableKey;		 /* 值集对应的table的key */ 
	private String	valueTableColumn;		 /* 值集对应的column */ 
	private String	valueColumnType;		 /* 1:string  */ 
	private String	valueConfig;		 /* 值集对应的配置，  逗号和冒号隔开 */ 
	private String	dbCode;		 /* 对应的数据链路 */ 
	private Integer	recordStatus;		 /* 1，数据正常，0 代表数据删除 */ 

	// Constructor
	public UrmRightValueSetConfig() {
	}

	/**
	 * full Constructor
	 */
	public UrmRightValueSetConfig(String valueCode, String valueName, String valueIntru, String valueTableName, String valueTableKey, String valueTableColumn, String valueColumnType, String valueConfig, String dbCode, Integer recordStatus) {
		this.valueCode = valueCode;
		this.valueName = valueName;
		this.valueIntru = valueIntru;
		this.valueTableName = valueTableName;
		this.valueTableKey = valueTableKey;
		this.valueTableColumn = valueTableColumn;
		this.valueColumnType = valueColumnType;
		this.valueConfig = valueConfig;
		this.dbCode = dbCode;
		this.recordStatus = recordStatus;
	}

	public String getValueCode() {
		return valueCode;
	}

	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getValueIntru() {
		return valueIntru;
	}

	public void setValueIntru(String valueIntru) {
		this.valueIntru = valueIntru;
	}

	public String getValueTableName() {
		return valueTableName;
	}

	public void setValueTableName(String valueTableName) {
		this.valueTableName = valueTableName;
	}

	public String getValueTableKey() {
		return valueTableKey;
	}

	public void setValueTableKey(String valueTableKey) {
		this.valueTableKey = valueTableKey;
	}

	public String getValueTableColumn() {
		return valueTableColumn;
	}

	public void setValueTableColumn(String valueTableColumn) {
		this.valueTableColumn = valueTableColumn;
	}

	public String getValueColumnType() {
		return valueColumnType;
	}

	public void setValueColumnType(String valueColumnType) {
		this.valueColumnType = valueColumnType;
	}

	public String getValueConfig() {
		return valueConfig;
	}

	public void setValueConfig(String valueConfig) {
		this.valueConfig = valueConfig;
	}

	public String getDbCode() {
		return dbCode;
	}

	public void setDbCode(String dbCode) {
		this.dbCode = dbCode;
	}

	public Integer getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(Integer recordStatus) {
		this.recordStatus = recordStatus;
	}

	@Override
	public String toString() {
		return "UrmRightValueSetConfig [" + "valueCode=" + valueCode+ ", valueName=" + valueName+ ", valueIntru=" + valueIntru+ ", valueTableName=" + valueTableName+ ", valueTableKey=" + valueTableKey+ ", valueTableColumn=" + valueTableColumn+ ", valueColumnType=" + valueColumnType+ ", valueConfig=" + valueConfig+ ", dbCode=" + dbCode+ ", recordStatus=" + recordStatus+  "]";
	}
}
