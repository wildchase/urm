package com.panly.urm.manager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	
	/**
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sayHello")
	public String sayHello(String name) {
		return "hell" + name;
	}
	
	
	/**
	 * @param name
	 * @return
	 */
	@RequestMapping("/index")
	public String test(String name) {
		return "index" ;
	}
	

}
