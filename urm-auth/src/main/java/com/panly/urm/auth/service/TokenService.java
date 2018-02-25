package com.panly.urm.auth.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.panly.urm.auth.common.TokenUtil;
import com.panly.urm.auth.dao.AcctTokenDao;
import com.panly.urm.auth.model.AcctEntity;
import com.panly.urm.auth.model.AcctToken;
import com.panly.urm.common.BeanCopyUtil;
import com.panly.urm.tran.auth.TokenDTO;



@Service
public class TokenService{
	
	@Autowired
	private AcctTokenDao tokenDao;
	
	public AcctToken createToken(AcctEntity acct) {
		AcctToken token = new AcctToken();
		token.setAcctId(acct.getAcctId());
		token.setAcctName(acct.getAcctName());
		token.setCreateTime(new Date());
		token.setToken(TokenUtil.token());
		tokenDao.createToken(token);
		return token;
	}

	@Cacheable(value="token")
	public TokenDTO getToken(String token) {
		AcctToken t = tokenDao.getToken(token);
		return BeanCopyUtil.copy(t, TokenDTO.class);
	}
	
	@CacheEvict(value="token")
	public void delToken(String token) {
		tokenDao.deleteToken(token);
	}

}
