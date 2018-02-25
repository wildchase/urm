package com.panly.urm.auth.dao;

import com.panly.urm.auth.model.AcctEntity;

public interface AcctDao {

	AcctEntity getAcctByAcct(String acct);

	AcctEntity getAcctByEmail(String email);

	AcctEntity getAcctByPhone(String phone);

}
