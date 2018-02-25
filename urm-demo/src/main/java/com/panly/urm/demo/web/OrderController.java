
package com.panly.urm.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.panly.urm.demo.service.OrderService;
import com.panly.urm.demo.vo.OrderParamVo;
import com.panly.urm.demo.vo.TestOrderVo;
import com.panly.urm.page.core.PageDTO;
import com.panly.urm.page.core.PageDTOUtil;
import com.panly.urm.right.util.RightUtil;
import com.panly.urm.tran.auth.TreeDTO;
import com.panly.urm.web.JsonResult;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView mav = new ModelAndView("order/order-list");
		List<TreeDTO> menus = RightUtil.getAcctTree();
		mav.addObject("menus", menus);
		return mav;
	}
	
	@RequestMapping(value="/page")
	@ResponseBody
	public JsonResult queryPage(OrderParamVo orderParamVo){
		PageDTO<TestOrderVo> page  = orderService.queryPage(orderParamVo);
		return PageDTOUtil.changePageToDataTableResult(page);
	}
	
	@RequestMapping(value="/update/amount",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult updateOrder(@RequestBody TestOrderVo vo){
		orderService.updateOrderAmount(vo);
		return new JsonResult();
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteOrder(@RequestBody TestOrderVo vo){
		orderService.deleteOrder(vo.getOrderId());
		return new JsonResult();
	}
	
	
	
}
