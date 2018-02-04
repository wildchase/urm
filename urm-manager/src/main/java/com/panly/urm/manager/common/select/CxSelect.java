package com.panly.urm.manager.common.select;

import java.util.List;

public class CxSelect {
	
	private String v;
	
	private String n;
	
	private List<CxSelect> s;
	
	public CxSelect(String v, String n) {
		super();
		this.v = v;
		this.n = n;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public List<CxSelect> getS() {
		return s;
	}

	public void setS(List<CxSelect> s) {
		this.s = s;
	}
	
	
	
}
