package com.panly.urm.tran.auth;

import java.util.List;


public class TreeDTO {
	
	private String id;

	private String code;

	private String text;

	// 1, app ,2 function 3,oper
	private String type;
	
	private String url;

	private List<TreeDTO> nodes;
	
	private String icon;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<TreeDTO> getNodes() {
		return nodes;
	}

	public void setNodes(List<TreeDTO> nodes) {
		this.nodes = nodes;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
