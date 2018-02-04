package com.panly.urm.manager.user.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

public class Menu {

	/**
	 * 菜单id
	 */
	private String menuId;

	/**
	 * 菜单名称
	 */
	private String menuName;

	/**
	 * 上次菜单id 若是parentId为0，或者为空则为一级菜单
	 */
	private String parentId;

	/**
	 * 点击菜单跳转的地址，相对地址
	 */
	private String url;
	
	/**
	 * 图标
	 */
	private String icon;
	
	/**
	 * 下级菜单
	 */
	List<Menu> menu;

	@XmlAttribute
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@XmlAttribute
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@XmlAttribute
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@XmlAttribute
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@XmlAttribute
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<Menu> getMenu() {
		return menu;
	}

	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}
	

}
