package com.panly.urm.auth.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panly.urm.auth.common.PatternUtil;
import com.panly.urm.auth.dao.AcctDao;
import com.panly.urm.auth.model.AcctEntity;
import com.panly.urm.secret.Md5Util;


@Service
public class AcctService {

	@Autowired
	private AcctDao acctDao;
	
	public AcctEntity checkAndGetUser(String account, String password) {
		if(StringUtils.isEmpty(account)){
			throw new RuntimeException("账号不能为空");
		}
		if(StringUtils.isEmpty(password)){
			throw new RuntimeException("密码不能为空");
		}
		AcctEntity acct = null;
		// 判断 acctcount 的类型，是phone 账号，还是邮箱
		if(PatternUtil.isPhone(account)){
			acct = getByPhone(account);
		}else if(PatternUtil.isEmail(account)){
			acct = getByEmail(account);
		}else{
			acct = getByAcct(account);
		}
		if(acct==null){
			throw new RuntimeException("用户不存在");
		}
		if(!StringUtils.equals(acct.getPassword(), Md5Util.encrptWithSalt(password, acct.getSalt()))){
			throw new RuntimeException("密码错误");
		}
		return acct;
	}

	private AcctEntity getByAcct(String acct) {
		return acctDao.getAcctByAcct(acct);
	}

	private AcctEntity getByEmail(String email) {
		return acctDao.getAcctByEmail(email);
	}

	private AcctEntity getByPhone(String phone) {
		return acctDao.getAcctByPhone(phone);
	}

	public void updateAcctLoginLog(Long acctId, String remoteAddr) {
		acctDao.updateAcctLoginLog(acctId,remoteAddr);
	}
	
}
