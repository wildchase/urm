package com.panly.urm.manager.right.vo;

import java.util.List;

public class DataRightExecResult {

	private int count;
	
	private List<String> columns;
	
	private List<Object[]> result;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public List<Object[]> getResult() {
		return result;
	}

	public void setResult(List<Object[]> result) {
		this.result = result;
	}

	
}
