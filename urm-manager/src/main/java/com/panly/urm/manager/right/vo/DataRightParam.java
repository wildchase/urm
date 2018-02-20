package com.panly.urm.manager.right.vo;

public class DataRightParam {

	private String dbCode;

	private String dataRightSql;

	private Long rightId;
	private Long relaId;
	private Integer relaType; /* 1，用户关联操作对应的数据权限 2，角色关联操作对应的数据权限 */
	private Integer rightType; /* 1，数据链路 2，值集 */

	private String valueCode; /* 值集编号 */
	private String valueConfig; /* 值集选中的项 逗号隔开 */

	public String getDbCode() {
		return dbCode;
	}

	public void setDbCode(String dbCode) {
		this.dbCode = dbCode;
	}

	public String getDataRightSql() {
		return dataRightSql;
	}

	public void setDataRightSql(String dataRightSql) {
		this.dataRightSql = dataRightSql;
	}

	public Long getRelaId() {
		return relaId;
	}

	public void setRelaId(Long relaId) {
		this.relaId = relaId;
	}

	public Integer getRelaType() {
		return relaType;
	}

	public void setRelaType(Integer relaType) {
		this.relaType = relaType;
	}

	public Integer getRightType() {
		return rightType;
	}

	public void setRightType(Integer rightType) {
		this.rightType = rightType;
	}

	public Long getRightId() {
		return rightId;
	}

	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}

	public String getValueCode() {
		return valueCode;
	}

	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}

	public String getValueConfig() {
		return valueConfig;
	}

	public void setValueConfig(String valueConfig) {
		this.valueConfig = valueConfig;
	}

}
