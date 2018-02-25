package com.panly.urm.auth.dao;

import com.panly.urm.auth.model.VerifyCode;

public interface VerifyCodeDao {
	
	public int insert(VerifyCode verifyCode);
	
	public int update(VerifyCode verifyCode);
	
	public VerifyCode getVerifyCode(String verifyKey);

}
