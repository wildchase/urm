package com.panly.urm.manager.user.config.user;


import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

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
	
}
 