package com.panly.urm.manager.user.config.user;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.panly.umr.jaxb.JaxbUtil;
import com.panly.urm.manager.user.model.User;


/**
 * 从配置中获取用户信息
 * @author a@panly.me
 */
@XmlRootElement(name="users")
public class UserContextXml {
	
	List<User> user;

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}
	
	public static void main(String[] args) {
		UserContextXml xml  = new UserContextXml();
		
		List<User> list = new ArrayList<>();
		
		User user = new User();
		user.setAccount("admin");
		user.setPassword("123456");
		user.setUserName("admin");
		user.setRoleCode("admin");
		user.setRoleName("管理员");
		list.add(user);
		list.add(user);
		xml.setUser(list);
		
		System.out.println(JaxbUtil.toXml(xml));
	}
	
}
 