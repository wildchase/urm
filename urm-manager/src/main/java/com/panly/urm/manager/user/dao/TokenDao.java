package com.panly.urm.manager.user.dao;


import org.apache.ibatis.annotations.Param;

import com.panly.urm.manager.user.model.Token;


/**
 * token 处理
 * @author a@panly.me
 */
public interface TokenDao {

	/**
	 * 获取token
	 * @param token
	 * @return
	 */
	public int createToken(Token token);
	
	/**
	 * 获取token
	 * @param token
	 * @return
	 */
	public Token getToken(@Param("token") String token);
	
	/**
	 * 删除token
	 * @param token
	 * @return
	 */
	public int deleteToken(@Param("token")  String token);
	
}

