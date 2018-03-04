package com.panly.urm.auth.web;

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

import com.panly.urm.auth.common.Constants;
import com.panly.urm.auth.common.TokenUtil;
import com.panly.urm.auth.common.VerifyCodeUtils;
import com.panly.urm.auth.model.AcctEntity;
import com.panly.urm.auth.model.AcctToken;
import com.panly.urm.auth.service.AcctService;
import com.panly.urm.auth.service.CheckUrlService;
import com.panly.urm.auth.service.TokenService;
import com.panly.urm.auth.service.VerifyCodeService;
import com.panly.urm.common.CookieUtil;
import com.panly.urm.common.RandomCharUtil;
import com.panly.urm.common.WebUtil;
import com.panly.urm.tran.auth.TokenDTO;
import com.panly.urm.web.JsonResult;


/**
 * 不使用session来做验证码 登陆操作
 * @author a@panly.me
 */
@Controller
public class LoginController {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private AcctService acctService;
	
	@Autowired
	private VerifyCodeService verifyCodeService;
	
	@Autowired
	private CheckUrlService checkUrlService;
	
	
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
		
		//验证 returnUrl 
		checkUrlService.checkUrl(returnUrl);
		
		// 校验token是否已经登陆，如果已经登陆则跳转登陆节能面
		String token = CookieUtil.getCookieValueByName(request, Constants.COOKIE_TOKEN);

		// 若是存在token ，且有效，则直接进入首页，不进入登陆页面了
		if (StringUtils.isNotEmpty(token)) {
			TokenDTO tokenObject = tokenService.getToken(token);
			if (tokenObject != null) {
				if (StringUtils.isNotEmpty(returnUrl)) {
					response.sendRedirect(returnUrl);
				} else {
					response.sendRedirect("/index");
				}
				return null;
			}
		}
		ModelAndView mav = new ModelAndView("login");

		mav.addObject("returnUrl", returnUrl);

		//生成 loginKey,登陆请求必须传入
		String loginKey = RandomCharUtil.random(32);
		mav.addObject("loginKey", loginKey);
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
	public JsonResult login(String account, String password, String verifyCode,String loginKey, HttpServletResponse response,
			HttpServletRequest request) {

		//校验验证码是否 和存储的一样
		verifyCodeService.check(loginKey, verifyCode);

		//校验用户名和密码
		AcctEntity acct = acctService.checkAndGetUser(account, password);
		
		//修改登录用户的ip
		acctService.updateAcctLoginLog(acct.getAcctId(),WebUtil.getRemoteAddr());

		// 创建token
		AcctToken token = tokenService.createToken(acct);

		// 设置cookie
		CookieUtil.setCookie(response, Constants.COOKIE_TOKEN, token.getToken());
		// 设置返回
		return new JsonResult().setData(token.getToken());
	}

	/**
	 * 登出的请求操作
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout")
	@ResponseBody
	public JsonResult logout( HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String token = TokenUtil.getToken(request);
			if (StringUtils.isNotEmpty(token)) {
				tokenService.delToken(token);
			}
		} catch (Exception e) {
		}
		CookieUtil.removeAllCookie(request, response);
		return new JsonResult();
	}
	

	/**
	 * 验证码
	 * 
	 * @return
	 */
	@RequestMapping(value = "/image", method = RequestMethod.GET)
	public void authImage(String loginKey, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");

			// 生成随机字串
			String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
			verifyCodeService.save(loginKey, verifyCode);
			
			// 生成图片
			int w = 100, h = 30;
			VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
