package com.panly.urm.demo.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.panly.urm.right.util.RightUtil;
import com.panly.urm.tran.auth.TreeDTO;

@Controller
@RequestMapping
public class IndexController {

	@RequestMapping(value={"/","index"})
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("index");
		List<TreeDTO> menus = RightUtil.getAcctTree();
		System.out.println(JSON.toJSONString(menus));
		mav.addObject("menus", menus);
		return mav;
	}	
	
	
}
