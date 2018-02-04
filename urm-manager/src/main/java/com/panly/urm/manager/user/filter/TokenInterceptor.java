package com.panly.urm.manager.user.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.alibaba.fastjson.JSON;
import com.panly.umr.common.CookieUtil;
import com.panly.umr.common.WebUtil;
import com.panly.urm.manager.common.web.JsonResult;
import com.panly.urm.manager.user.UserUtil;
import com.panly.urm.manager.user.config.Constants;
import com.panly.urm.manager.user.config.menu.MenuConfig;
import com.panly.urm.manager.user.model.Token;
import com.panly.urm.manager.user.service.TokenService;

public class TokenInterceptor implements HandlerInterceptor {
	
	protected UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	private String loginUrl;
	
	private TokenService tokenService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 获取token
		String token = getToken(request);

		if (StringUtils.isNotEmpty(token)) {
			Token tokenObject = tokenService.getToken(token);
			if (tokenObject != null) {
				UserUtil.init(tokenObject);
				setObjectToRequest(request);
				return true;
			} else {
				if (WebUtil.isJsonRequest(request)) {
					ajaxToLogin(request, response);
					return false;
				} else {
					redirectToLogin(request, response);
					return false;
				}
			}
		} else {
			if (WebUtil.isJsonRequest(request)) {
				ajaxToLogin(request, response);
				return false;
			} else {
				redirectToLogin(request, response);
				return false;
			}
		}
	}
	
	
	private void setObjectToRequest(HttpServletRequest request) {
		request.setAttribute("userId", UserUtil.getUserId());
		request.setAttribute("userName", UserUtil.getUserName());
		request.setAttribute("account", UserUtil.getAccount());
		request.setAttribute("token", UserUtil.getToken());
		request.setAttribute("menus", MenuConfig.getMenus(UserUtil.getAccount()));
	}


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

	private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		CookieUtil.removeCookie(response, Constants.COOKIE_TOKEN);
		String returnUrl = urlPathHelper.getRequestUri(request);
		response.sendRedirect(loginUrl+"?returnUrl="+returnUrl);
	}

	private void ajaxToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		CookieUtil.removeCookie(response, Constants.COOKIE_TOKEN);
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

	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}

}
