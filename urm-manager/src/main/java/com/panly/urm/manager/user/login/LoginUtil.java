package com.panly.urm.manager.user.login;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.panly.urm.common.CookieUtil;
import com.panly.urm.common.WebUtil;
import com.panly.urm.manager.user.config.Constants;
import com.panly.urm.manager.user.model.User;
import com.panly.urm.secret.AesUtil;

@Component
public class LoginUtil {
	
	@Value("${login.secret}")
	private String loginSecret;
	
	public String createLogin(User user){
		LoginObject o = new LoginObject();
		o.setLoginIp(WebUtil.getRemoteAddr());
		o.setUserId(user.getUserId());
		o.setAccount(user.getAccount());
		o.setUserName(user.getUserName());
		o.setTime(System.currentTimeMillis());
		return AesUtil.encrypt(JSON.toJSONString(o), loginSecret );
	}
	
	public LoginObject check(String loginCode){
		try {
			String v = AesUtil.decrypt(loginCode, loginSecret);
			return JSON.parseObject(v,LoginObject.class);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取token
	 * @param request
	 * @return
	 */
	public String getToken(HttpServletRequest request) {
		String token = request.getParameter(Constants.COOKIE_LOGIN_CODE);
		if(StringUtils.isEmpty(token)){
			token = CookieUtil.getCookieValueByName(request, Constants.COOKIE_LOGIN_CODE);
		}
		if(StringUtils.isEmpty(token)){
			token = request.getHeader(Constants.COOKIE_LOGIN_CODE);
		}
		return token;
	}
	

}
