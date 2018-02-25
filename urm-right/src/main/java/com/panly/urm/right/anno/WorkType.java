package com.panly.urm.right.anno;

public enum WorkType {
	 
	 /**用临时表 */
	 TMEP_SQL("tempSql"),
	 
	 /** 直接修改sql  */
	 CHANGE_SQL("changeSql")
	 ;
	
	private String type;

	private WorkType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static WorkType transform(String type){
		WorkType[] a = WorkType.values();
		for (WorkType workType : a) {
			if(workType.getType().equals(type)){
				return workType;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return getType();
	}
	
}
