package com.panly.urm.manager.user.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.panly.urm.manager.user.UserUtil;


/**
 * 首页
 * @author a@panly.me
 */
@Controller
public class IndexController {
	
	private final static Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	/**
	 * 进入首页
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping(value={"/","/index"})
	public String index(){
		logger.info("userId:{} userName:{} account:{} token:{} visitIp:{}",UserUtil.getUserId(),
				UserUtil.getUserName(),UserUtil.getAccount(),UserUtil.getToken(),UserUtil.getVisitIp());
		return "index";
	}
	
	
}
