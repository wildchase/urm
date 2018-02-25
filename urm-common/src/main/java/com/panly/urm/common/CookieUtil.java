package com.panly.urm.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * <p> cookie 操作工具类 </p> 
 *
 * @project 	core
 * @class 		CookieUtil
 * @author 		a@panly.me
 * @date 		2017年5月27日 上午10:48:49
 */
public class CookieUtil {

	// 默认cookie maxAge
	public final static int DEFAULT_COOKIE_MAXAGE = 60 * 60 * 24 * 7 * 2;

	/**
	 * 读取cookie getCookieByName
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}
	
	/**
	 * 读取cookie getCookieByName
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookieValueByName(HttpServletRequest request, String name) {
		Cookie cookie = getCookieByName(request, name);
		if(cookie!=null){
			return cookie.getValue();
		}else{
			return "";
		}
	}

	/**
	 * 删除cookie 
	 * removeCookie
	 * @param response
	 * @param name
	 */
	public static void removeCookie(HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	 * 删除所有cookie 
	 * removeCookie
	 * @param response
	 */
	public static void removeAllCookie(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				removeCookie(response, cookie.getName());
			}
		}
	}
	
	
	/**
	 * 设置cookies
	 * setCookie
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void setCookie(HttpServletResponse response, String name,String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(DEFAULT_COOKIE_MAXAGE);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	 * 
	 * setCookie
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void setCookie(HttpServletResponse response, String name, String value,int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(DEFAULT_COOKIE_MAXAGE);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	
	/**
	 * 读取所有的cookies readCookieMap
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}


}
