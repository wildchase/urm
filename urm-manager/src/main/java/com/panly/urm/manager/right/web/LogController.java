package com.panly.urm.manager.right.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.panly.urm.common.BeanCopyUtil;
import com.panly.urm.manager.common.constants.OperTypeEnum;
import com.panly.urm.manager.common.excel.FileDownloadUtil;
import com.panly.urm.page.core.PageDTO;
import com.panly.urm.page.core.PageDTOUtil;
import com.panly.urm.manager.right.service.LogService;
import com.panly.urm.manager.right.vo.AuthLogParamsVo;
import com.panly.urm.manager.right.vo.AuthLogVo;
import com.panly.urm.manager.right.vo.OperLogParamsVo;
import com.panly.urm.manager.right.vo.OperLogVo;
import com.panly.urm.manager.user.anno.MenuOp;
import com.panly.urm.tran.constants.SuccessTypeEnum;
import com.panly.urm.web.JsonResult;

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
	
	@ResponseBody
	@RequestMapping("/oper/log/page")
	public JsonResult queryOperLog(OperLogParamsVo operLogParamsVo){
		PageDTO<OperLogVo> page = logService.queryOperLog(operLogParamsVo);
		return PageDTOUtil.changePageToDataTableResult(page);
	}
	
	
	@ResponseBody
	@RequestMapping("/auth/log/page")
	public JsonResult queryAuthLog(AuthLogParamsVo authLogParamsVo){
		PageDTO<AuthLogVo> page = logService.queryAuthLog(authLogParamsVo);
		return PageDTOUtil.changePageToDataTableResult(page);
	}
	
	
	@RequestMapping(value="/oper/log/download")
	public void downloadOperLog(OperLogParamsVo operLogParamsVo,HttpServletResponse response){
		List<OperLogVo> data = logService.queryOperLogList(operLogParamsVo);
		
		String sheetName = "操作日志";
		String title = "操作日志";
		String fileName = "操作日志.xlsx";
		String[] headers = { "用户名称@userName", "访问地址@url", "操作类型@operTypeName", "耗费时间@operCost",
				"是否成功@successName","时间@createTime","日志@operContent@8000"};
		
		FileDownloadUtil.exportToExcel(response,sheetName,title,fileName,headers,data);
	}
	
	
	@RequestMapping(value="/auth/log/download")
	public void downloadAuthLog(AuthLogParamsVo authLogParamsVo,HttpServletResponse response){
		List<AuthLogVo> data = logService.queryAuthLogList(authLogParamsVo);
		
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> result = change(data.get(i));
			list.add(result);
		}
		
		String sheetName = "鉴权日志";
		String title = "鉴权日志";
		String fileName = "鉴权日志.xlsx";
		String[] headers = { "应用名称@appName", "用户名称@acctName", "操作名称@operName", "耗费时间@authCost","成功@success","时间@createTime@4000"};
		
		FileDownloadUtil.exportToExcel(response,sheetName,title,fileName,headers,list);
	}
	
	private Map<String, Object> change(AuthLogVo acctVo) {
		Map<String, Object> map = BeanCopyUtil.getObjectMapProperties(acctVo);
		
		Integer success = (Integer) map.get("success");
		map.put("success", SuccessTypeEnum.SUCCESS_DESC_MAP.get(success));
		return map;
	}
	
}
