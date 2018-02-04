package com.panly.urm.manager.user.config.menu;

import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.panly.umr.jaxb.JaxbUtil;
import com.panly.urm.manager.user.model.Menu;

/**
 * 读取缓存，获取用户信息
 * 
 * @author a@panly.me
 */
@Component
public class MenuConfig {

	static List<Menu> menus;
	
	@PostConstruct
	public void init() {
		InputStream in = null;
		try {
			in = MenuConfig.class.getClassLoader().getResourceAsStream(
					"config/menus.xml");
			String xml = IOUtils.toString(in, "UTF-8");
			MenuContextXml menuXml = JaxbUtil.toBean(xml,
					MenuContextXml.class);
			menus = menuXml.getMenu();
		} catch (Exception e) {
			throw new RuntimeException("menus.xml配置错误", e);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public static List<Menu> getMenus(String account){
		return menus;
	}

}
