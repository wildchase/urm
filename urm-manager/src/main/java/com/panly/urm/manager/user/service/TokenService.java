package com.panly.urm.manager.user.service;

import com.panly.urm.manager.user.model.Token;


public interface TokenService {
	
	/**
	 * 创建token
	 * @param account
	 * @return
	 */
	public Token createToken(String account);
	
	/**
	 * 获取tokenObject
	 * @param token
	 * @return
	 */
	public Token getToken(String token);
	
	
	
	/**
	 * 删除token
	 * @param token
	 * @return
	 */
	public void delToken(String token);
}
