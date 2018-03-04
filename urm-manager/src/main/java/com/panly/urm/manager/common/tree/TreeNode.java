package com.panly.urm.manager.common.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

	public final static String TYPE_APP = "1";

	public final static String TYPE_FUNC = "2";

	public final static String TYPE_OPER = "3";

	private String id;

	private String code;

	private String text;

	// 1, app ,2 function 3,oper
	private String type;

	private List<TreeNode> nodes;
	
	private boolean selectable = true;
	 
	private State state = new State();
	
	private String icon;
	
	public TreeNode() {
		super();
	}

	public TreeNode(String id, String code, String text, String type) {
		super();
		this.id = id;
		this.code = code;
		this.text = text;
		this.type = type;
	}

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

	public List<TreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<TreeNode> nodes) {
		this.nodes = nodes;
	}
	
	public void addNode(TreeNode node){
		if(this.nodes==null){
			nodes = new ArrayList<>();
		}
		nodes.add(node);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
