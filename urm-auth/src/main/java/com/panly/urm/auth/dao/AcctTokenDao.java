package com.panly.urm.auth.dao;


import org.apache.ibatis.annotations.Param;

import com.panly.urm.auth.model.AcctToken;



/**
 * token 处理
 * @author a@panly.me
 */
public interface AcctTokenDao {

	/**
	 * 获取token
	 * @param token
	 * @return
	 */
	public int createToken(AcctToken token);
	
	/**
	 * 获取token
	 * @param token
	 * @return
	 */
	public AcctToken getToken(@Param("token") String token);
	
	/**
	 * 删除token
	 * @param token
	 * @return
	 */
	public int deleteToken(@Param("token")  String token);
	
}

