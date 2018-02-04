package com.panly.urm.manager.user.config.user;

import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.panly.umr.jaxb.JaxbUtil;
import com.panly.urm.manager.user.model.User;

/**
 * 读取缓存，获取用户信息
 * 
 * @author a@panly.me
 */
@Component
public class UserConfig {

	List<User> users;

	@PostConstruct
	public void init() {
		InputStream in = null;
		try {
			in = UserConfig.class.getClassLoader().getResourceAsStream("config/users.xml");
			String xml = IOUtils.toString(in, "UTF-8");
			UserContextXml userContextXml = JaxbUtil.toBean(xml, UserContextXml.class);
			List<User> list = userContextXml.getUser();
			users = list;
		} catch (Exception e) {
			throw new RuntimeException("users.xml配置错误", e);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public User getUser(String account) {
		for (User user : users) {
			if (user.getAccount().equals(account)) {
				return user;
			}
		}
		return null;
	}

	public User getUser(Long userId) {
		for (User user : users) {
			if (user.getUserId().equals(userId)) {
				return user;
			}
		}
		return null;
	}

}
