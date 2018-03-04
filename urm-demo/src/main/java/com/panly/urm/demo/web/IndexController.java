package com.panly.urm.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping
public class IndexController {

	@RequestMapping(value={"/","index"})
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}	
	
	
	@RequestMapping(value={"/welcome"})
	public ModelAndView welcome(){
		ModelAndView mav = new ModelAndView("welcome");
		return mav;
	}	
	
}
