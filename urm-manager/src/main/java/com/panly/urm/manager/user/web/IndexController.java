package com.panly.urm.manager.user.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 首页
 * @author a@panly.me
 */
@Controller
public class IndexController {
	
	/**
	 * 进入首页
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping(value={"/","/index"})
	public String index(){
		return "index";
	}
	
	
}
