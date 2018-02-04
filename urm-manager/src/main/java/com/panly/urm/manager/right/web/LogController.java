package com.panly.urm.manager.right.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.panly.urm.manager.common.constants.OperTypeEnum;
import com.panly.urm.manager.common.page.core.PageDTO;
import com.panly.urm.manager.common.page.core.PageDTOUtil;
import com.panly.urm.manager.common.web.JsonResult;
import com.panly.urm.manager.right.service.LogService;
import com.panly.urm.manager.right.vo.AppLogParamsVo;
import com.panly.urm.manager.right.vo.AppLogVo;
import com.panly.urm.manager.right.vo.AuthLogParamsVo;
import com.panly.urm.manager.right.vo.AuthLogVo;
import com.panly.urm.manager.right.vo.OperLogParamsVo;
import com.panly.urm.manager.right.vo.OperLogVo;
import com.panly.urm.manager.user.anno.MenuOp;

@Controller
public class LogController {
	
	@Autowired
	private LogService logService;
	

	@MenuOp("102001")
	@RequestMapping("/oper/log/list")
	public String toOperLog(HttpServletRequest req){
		req.setAttribute("OperTypeEnum", OperTypeEnum.values());
		return "/right/log/oper/oper-log-list";
	}
	
	@MenuOp("102002")
	@RequestMapping("/auth/log/list")
	public String toAuthLog(HttpServletRequest req){
		return "/right/log/auth/auth-log-list";
	}
	
	@MenuOp("102003")
	@RequestMapping("/app/log/list")
	public String toAppLog(HttpServletRequest req){
		return "/right/log/app/app-log-list";
	}
	
	@ResponseBody
	@RequestMapping("/oper/log/page")
	public JsonResult queryOperLog(OperLogParamsVo operLogParamsVo){
		PageDTO<OperLogVo> page = logService.queryOperLog(operLogParamsVo);
		return PageDTOUtil.changePageToDataTableResult(page);
	}
	
	
	@ResponseBody
	@RequestMapping("/oper/auth/page")
	public JsonResult queryAuthLog(AuthLogParamsVo authLogParamsVo){
		PageDTO<AuthLogVo> page = logService.queryAuthLog(authLogParamsVo);
		return PageDTOUtil.changePageToDataTableResult(page);
	}
	
	
	@ResponseBody
	@RequestMapping("/app/log/page")
	public JsonResult queryAppLog(AppLogParamsVo appLogParamsVo){
		PageDTO<AppLogVo> page = logService.queryAppLog(appLogParamsVo);
		return PageDTOUtil.changePageToDataTableResult(page);
	}
	
	
}
