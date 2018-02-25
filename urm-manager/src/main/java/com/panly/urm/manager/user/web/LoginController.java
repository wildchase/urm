package com.panly.urm.manager.user.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.panly.urm.common.CookieUtil;
import com.panly.urm.manager.user.config.Constants;
import com.panly.urm.manager.user.login.LoginObject;
import com.panly.urm.manager.user.login.LoginUtil;
import com.panly.urm.manager.user.model.User;
import com.panly.urm.manager.user.service.UserService;
import com.panly.urm.web.JsonResult;

/**
 * 登陆操作
 * 
 * @author a@panly.me
 */
@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private LoginUtil loginUtil;

	/**
	 * 进入登陆页面
	 * 
	 * @param account
	 * @param password
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView index(String returnUrl, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 校验是否已经登陆，如果已经登陆则跳转登陆节能面
		String loginCode = CookieUtil.getCookieValueByName(request, Constants.COOKIE_LOGIN_CODE);

		// 若是存在token ，且有效，则直接进入首页，不进入登陆页面了
		if (StringUtils.isNotEmpty(loginCode)) {
			LoginObject loginObject = loginUtil.check(loginCode);
			if(loginObject!=null){
				//若是存在
				response.sendRedirect("/index");
			}
		}
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("returnUrl", returnUrl);
		return mav;
	}
	
	

	/**
	 * 登陆的请求操作
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult login(String account, String password, HttpServletResponse response, HttpServletRequest request) {
		if (StringUtils.isEmpty(account)) {
			throw new RuntimeException("账号不能为空");
		}
		if (StringUtils.isEmpty(password)) {
			throw new RuntimeException("密码不能为空");
		}
		User user = userService.getUser(account);
		if (user == null) {
			throw new RuntimeException("账号不存在");
		}
		if (!StringUtils.equals(password, user.getPassword())) {
			throw new RuntimeException("密码不正确");
		}
		String loginCode = loginUtil.createLogin(user);
		CookieUtil.setCookie(response, Constants.COOKIE_LOGIN_CODE, loginCode);
		return new JsonResult().setData(loginCode);
	}

	/**
	 * 登出的请求操作
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		CookieUtil.removeAllCookie(request, response);
		return "redirect:login?returnUrl=/";
	}

}
