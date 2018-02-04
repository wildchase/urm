package com.panly.urm.manager.user.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.panly.urm.manager.user.common.TokenUtil;
import com.panly.urm.manager.user.config.user.UserConfig;
import com.panly.urm.manager.user.dao.TokenDao;
import com.panly.urm.manager.user.model.Token;
import com.panly.urm.manager.user.model.User;
import com.panly.urm.manager.user.service.TokenService;


@Service
public class TokenServiceImpl implements TokenService{
	
	@Autowired
	private TokenDao tokenDao;
	
	@Autowired
	private UserConfig userConfig;
	

	public Token createToken(String account) {
		User user = userConfig.getUser(account);
		if(user==null){
			throw new RuntimeException("用户不存在，不能创建token");
		}
		Token token = new Token();
		token.setAccount(account);
		token.setCreateTime(new Date());
		token.setToken(TokenUtil.token());
		token.setUserName(user.getUserName());
		token.setUserId(user.getUserId());
		tokenDao.createToken(token);
		return token;
	}

	@Cacheable(value="token")
	public Token getToken(String token) {
		return tokenDao.getToken(token);
	}
	
	@CacheEvict(value="token")
	public void delToken(String token) {
		tokenDao.deleteToken(token);
	}
	

}
