package com.panly.urm.manager.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panly.urm.manager.user.config.user.UserConfig;
import com.panly.urm.manager.user.model.User;
import com.panly.urm.manager.user.service.UserService;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserConfig userConfig;
	
	@Override
	public User getUser(String account) {
		return userConfig.getUser(account);
	}

	@Override
	public String getUserName(Long userId) {
		User u = userConfig.getUser(userId);
		if(u==null){
			return null;
		}else{
			return u.getUserName();
		}
	}

}
