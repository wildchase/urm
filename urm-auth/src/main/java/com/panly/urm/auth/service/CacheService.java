package com.panly.urm.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.panly.urm.auth.dao.AuthDao;
import com.panly.urm.auth.model.AcctEntity;
import com.panly.urm.auth.model.AppEntity;
import com.panly.urm.auth.model.OperEntity;

@Service
public class CacheService {

	@Autowired
	private AuthDao authDao;
	
	@Cacheable("acct")
	public AcctEntity getAcct(Long acctId){
		return authDao.getAcct(acctId);
	}
	
	@Cacheable("app")
	public AppEntity getApp(String appCode){
		return authDao.getApp(appCode);
	}
	
	@Cacheable("oper")
	public OperEntity getOper(String operCode){
		return authDao.getOper(operCode);
	}
	

}
