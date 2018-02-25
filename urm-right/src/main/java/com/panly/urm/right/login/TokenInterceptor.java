package com.panly.urm.right.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.panly.urm.common.CookieUtil;
import com.panly.urm.common.HttpUtil;
import com.panly.urm.common.WebUtil;
import com.panly.urm.right.service.UserService;
import com.panly.urm.tran.auth.TokenDTO;
import com.panly.urm.web.JsonResult;

public class TokenInterceptor implements HandlerInterceptor {
	
	private final static Logger logger =LoggerFactory.getLogger(TokenInterceptor.class);
	
	public final static  String TOKEN_PARAM = "token";
	
	protected UrlPathHelper urlPathHelper = new UrlPathHelper();

	private String loginUrl;

	private String checkTokenUrl;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//带token的 get请求
		if(request.getMethod().equalsIgnoreCase("get")&&request.getParameter(TOKEN_PARAM)!=null){
			String tokenParam = request.getParameter(TOKEN_PARAM);
			CookieUtil.setCookie(response, TOKEN_PARAM, tokenParam);
			String redirectUrl = removeTokenUrl(request);
			response.sendRedirect(redirectUrl);
			return false;
		}
		// 获取token
		String token = getToken(request);
		if (StringUtils.isNotEmpty(token)) {
			String str=HttpUtil.httpPost(checkTokenUrl+"?token="+token);
			logger.debug(str);
			JsonResult ret = JSON.parseObject(str, JsonResult.class);
			if(JsonResult.SUCCESS.equals(ret.getStatus())){
				JSONObject data  = (JSONObject) ret.getData();
				TokenDTO t = data.toJavaObject(TokenDTO.class);
				setTokenToRequest(t,request);
				return true;
			}else{
				toLogin(request, response);
				return false;
			}
		} else {
			toLogin(request, response);
			return false;
		}
	}
	
	private void setTokenToRequest(TokenDTO t, HttpServletRequest request) {
		request.setAttribute(UserService.PARAM_ACCT_ID, t.getAcctId());
		request.setAttribute("acctName", t.getAcctName());
		request.setAttribute("createTime", t.getCreateTime());
	}

	private String removeTokenUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		int s = queryString.lastIndexOf(TOKEN_PARAM);
		if(s!=-1){
			queryString = queryString.substring(0, s);
		}
		if(StringUtils.isNotEmpty(queryString)){
			return url+"?"+queryString;	
		}else{
			return url;
		}
	}

	public void toLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		if (WebUtil.isJsonRequest(request)) {
			ajaxToLogin(request, response);
		} else {
			redirectToLogin(request, response);
		}
	}

	/**
	 * 获取token
	 * 
	 * @param request
	 * @return
	 */
	public static String getToken(HttpServletRequest request) {
		return CookieUtil.getCookieValueByName(request,TOKEN_PARAM);
	}

	private void redirectToLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		CookieUtil.removeCookie(response, TOKEN_PARAM);
		response.sendRedirect(getRetrunUrl(request));
	}
	
	private String getRetrunUrl(HttpServletRequest request){
		String queryString= request.getQueryString();
		String requestURL = request.getRequestURL().toString();
		if(StringUtils.isEmpty(queryString)){
			return loginUrl + "?returnUrl=" + requestURL;
		}else{
			return loginUrl +"?"+queryString+"&returnUrl=" + requestURL;
		}
	}

	private void ajaxToLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		CookieUtil.removeCookie(response, TOKEN_PARAM);
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
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setCheckTokenUrl(String checkTokenUrl) {
		this.checkTokenUrl = checkTokenUrl;
	}
	

}
