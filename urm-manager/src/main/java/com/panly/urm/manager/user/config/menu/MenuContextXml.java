package com.panly.urm.manager.user.config.menu;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.panly.umr.jaxb.JaxbUtil;
import com.panly.urm.manager.user.model.Menu;


/**
 * 从配置中获取用户信息
 * @author a@panly.me
 */
@XmlRootElement(name="users")
public class MenuContextXml {
	
	List<Menu> menu;

	public List<Menu> getMenu() {
		return menu;
	}


	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}


	public static void main(String[] args) {
		MenuContextXml xml  = new MenuContextXml();
		List<Menu> menu = new ArrayList<>();
		
		Menu m = new Menu();
		m.setMenuId("101");
		m.setMenuName("menu1");
		m.setIcon("321");
		List<Menu> menuList1 = new ArrayList<>();
		
		Menu mm = new Menu();
		mm.setMenuId("101001");
		mm.setMenuName("menu11");
		mm.setIcon("321");
		menuList1.add(mm);
		menuList1.add(mm);
		
		m.setMenu(menuList1);

		menu.add(m);
		menu.add(m);
		
		xml.setMenu(menu);
		
		
		System.out.println(JaxbUtil.toXml(xml));
	}
	
}
 