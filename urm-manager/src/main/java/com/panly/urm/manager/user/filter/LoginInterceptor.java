package com.panly.urm.manager.user.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.alibaba.fastjson.JSON;
import com.panly.urm.common.CookieUtil;
import com.panly.urm.common.DateUtil;
import com.panly.urm.common.WebUtil;
import com.panly.urm.manager.user.UserUtil;
import com.panly.urm.manager.user.config.Constants;
import com.panly.urm.manager.user.config.menu.MenuConfig;
import com.panly.urm.manager.user.login.LoginObject;
import com.panly.urm.manager.user.login.LoginUtil;
import com.panly.urm.web.JsonResult;

public class LoginInterceptor implements HandlerInterceptor {
	
	protected UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	private String loginUrl;
	
	private LoginUtil loginUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String loginCode = loginUtil.getToken(request);

		if (StringUtils.isNotEmpty(loginCode)) {
			LoginObject loginObject = loginUtil.check(loginCode);
			if (loginObject != null) {
				UserUtil.init(loginObject);
				setObjectToRequest(loginObject,request);
				return true;
			}
		} 
		toLogin(request, response);
		return false;
	}
	
	public void toLogin(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		if (WebUtil.isJsonRequest(request)) {
			ajaxToLogin(request, response);
		} else {
			redirectToLogin(request, response);
		}
	}
	
	private void setObjectToRequest(LoginObject loginObject, HttpServletRequest request) {
		request.setAttribute("userId", loginObject.getUserId());
		request.setAttribute("userName", loginObject.getUserName());
		request.setAttribute("account", loginObject.getAccount());
		request.setAttribute("loginTime", DateUtil.dateToChineseString(new Date(loginObject.getTime())));
		request.setAttribute("menus", MenuConfig.getMenus());
	}

	private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		CookieUtil.removeCookie(response, Constants.COOKIE_LOGIN_CODE);
		String returnUrl = urlPathHelper.getRequestUri(request);
		response.sendRedirect(loginUrl+"?returnUrl="+returnUrl);
	}

	private void ajaxToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		CookieUtil.removeCookie(response, Constants.COOKIE_LOGIN_CODE);
		JsonResult result = new JsonResult();
		result.setStatus(JsonResult.LOGIN_TIMEOUT);
		result.setError("登录token失效");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().write(JSON.toJSONString(result));
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserUtil.remove();
	}
	
	
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setLoginUtil(LoginUtil loginUtil) {
		this.loginUtil = loginUtil;
	}

}
