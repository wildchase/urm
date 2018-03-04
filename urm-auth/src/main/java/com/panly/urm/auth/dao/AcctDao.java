package com.panly.urm.auth.dao;

import org.apache.ibatis.annotations.Param;

import com.panly.urm.auth.model.AcctEntity;

public interface AcctDao {

	AcctEntity getAcctByAcct(String acct);

	AcctEntity getAcctByEmail(String email);

	AcctEntity getAcctByPhone(String phone);

	int updateAcctLoginLog(@Param("acctId")Long acctId,@Param("lastLoginIp") String remoteAddr);

	
}
