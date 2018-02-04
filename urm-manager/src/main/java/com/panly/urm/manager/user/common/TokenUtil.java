package com.panly.urm.manager.user.common;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.panly.umr.common.CookieUtil;
import com.panly.urm.manager.user.config.Constants;

public class TokenUtil {


	/**
	 * 获取token
	 * @param request
	 * @return
	 */
	public static String getToken(HttpServletRequest request) {
		String token = request.getParameter(Constants.COOKIE_TOKEN);
		if(StringUtils.isEmpty(token)){
			token = CookieUtil.getCookieValueByName(request, Constants.COOKIE_TOKEN);
		}
		if(StringUtils.isEmpty(token)){
			token = request.getHeader(Constants.COOKIE_TOKEN);
		}
		return token;
	}
	
	
	/**
	 * 生成随机数
	 * @return
	 */
	public static String token() {
		return getRandomString(16);
	}
	
	// length表示生成字符串的长度
	public static String getRandomString(int length) { 
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(token());
	}

}
