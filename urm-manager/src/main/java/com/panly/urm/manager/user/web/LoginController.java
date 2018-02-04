package com.panly.urm.manager.user.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.panly.umr.common.CookieUtil;
import com.panly.urm.manager.common.web.JsonResult;
import com.panly.urm.manager.user.VerifyCodeUtils;
import com.panly.urm.manager.user.common.TokenUtil;
import com.panly.urm.manager.user.config.Constants;
import com.panly.urm.manager.user.model.Token;
import com.panly.urm.manager.user.model.User;
import com.panly.urm.manager.user.service.TokenService;
import com.panly.urm.manager.user.service.UserService;


/**
 * 登陆操作
 * @author a@panly.me
 */
@Controller
public class LoginController {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 进入登陆页面
	 * @param account
	 * @param password
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView index(String returnUrl,HttpServletRequest request,HttpServletResponse response) throws IOException{
		//校验token是否已经登陆，如果已经登陆则跳转登陆节能面
		String token = CookieUtil.getCookieValueByName(request, Constants.COOKIE_TOKEN);
		
		//若是存在token ，且有效，则直接进入首页，不进入登陆页面了
		if(StringUtils.isNotEmpty(token)){
			Token tokenObject = tokenService.getToken(token);
			if(tokenObject!=null){
				if(StringUtils.isNotEmpty(returnUrl)){
					response.sendRedirect(returnUrl);
				}else{
					response.sendRedirect("/index");
				}
				return null;
			}
		}
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("returnUrl", returnUrl);
		return mav;
	}
	
	/**
	 * 登陆的请求操作
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult login(String account,String password,HttpServletResponse response,HttpServletRequest request){
		if(StringUtils.isEmpty(account)){
			throw new RuntimeException("账号不能为空");
		}
		if(StringUtils.isEmpty(password)){
			throw new RuntimeException("密码不能为空");
		}
		User user = userService.getUser(account);
		if(user==null){
			throw new RuntimeException("账号不存在");
		}
		if(!StringUtils.equals(password, user.getPassword())){
			throw new RuntimeException("密码不正确");
		}
		Token token = tokenService.createToken(account);
		CookieUtil.setCookie(response, Constants.COOKIE_TOKEN, token.getToken());
		return new JsonResult().setData(token);
	}
	
	
	/**
	 * 登陆的请求操作
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String login(HttpServletRequest request,HttpServletResponse response){
		try {
			String token = TokenUtil.getToken(request);
			if(StringUtils.isNotEmpty(token)){
				tokenService.delToken(token);
			}
		} catch (Exception e) {
		}
		CookieUtil.removeAllCookie(request, response);
		return "redirect:login?returnUrl=/";
	}

	/**
	 * 验证码
	 * @return
	 */
	@RequestMapping(value="/code",method=RequestMethod.GET)
	public void  authImage(HttpServletRequest request,HttpServletResponse response){
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			//生成随机字串
			String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
			//存入会话session
			HttpSession session = request.getSession(true);
			//删除以前的
			session.removeAttribute("verCode");
			session.setAttribute("verCode", verifyCode.toLowerCase());
			//生成图片
			int w = 100, h = 30;
			VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (Exception e) {
		}
	}
}
