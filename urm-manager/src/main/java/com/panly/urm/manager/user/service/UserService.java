package com.panly.urm.manager.user.service;

import com.panly.urm.manager.user.model.User;

public interface UserService {

	public User getUser(String account);
	
	public String getUserName(Long userId);
	
}
