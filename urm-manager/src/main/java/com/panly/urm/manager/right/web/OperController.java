package com.panly.urm.manager.right.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.panly.umr.common.BeanCopyUtil;
import com.panly.urm.manager.common.constants.OperTypeEnum;
import com.panly.urm.manager.common.constants.StatusEnum;
import com.panly.urm.manager.common.excel.FileDownloadUtil;
import com.panly.urm.manager.common.page.core.PageDTO;
import com.panly.urm.manager.common.page.core.PageDTOUtil;
import com.panly.urm.manager.common.web.JsonResult;
import com.panly.urm.manager.log.anno.Log;
import com.panly.urm.manager.right.service.OperService;
import com.panly.urm.manager.right.vo.OperParamsVo;
import com.panly.urm.manager.right.vo.OperVo;
import com.panly.urm.manager.user.anno.MenuOp;

@MenuOp("101005")
@Controller
@RequestMapping("/oper")
public class OperController {
	
	@Autowired
	private OperService operService;

	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String toAcct( HttpServletRequest req){
		return "right/oper/oper-list";
	}
	
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public ModelAndView detail(Long operId,HttpServletRequest req){
		ModelAndView mav = new ModelAndView("right/oper/oper-detail");
		mav.addObject("oper", operService.get(operId));
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/page")
	public JsonResult page(OperParamsVo operQueryVo){
		PageDTO<OperVo> page = operService.queryPage(operQueryVo);
		return PageDTOUtil.changePageToDataTableResult(page);
	}
	
	
	@Log(type = OperTypeEnum.OPER_ADD ,value = "[${userName}]添加操作[${operParamsVo.operName}], 操作编号[${operParamsVo.operCode}],所属应用[${operParamsVo.appId}]")
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public JsonResult add(@RequestBody OperParamsVo operParamsVo){
		operService.addOper(operParamsVo);
		return new JsonResult();
	}
	
	@Log(type = OperTypeEnum.OPER_EDIT ,value = "[${userName}]修改操作[${operVo.operId}],操作名称[${operVo.operName}] 操作编号[${operVo.operCode}]")
	@ResponseBody
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public JsonResult edit(@RequestBody OperVo operVo){
		operService.updateOper(operVo);
		return new JsonResult();
	}
	
	@Log(type = OperTypeEnum.OPER_DEL ,value = "[${userName}]删除操作[${paramsVo.deleteIds}]")
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public JsonResult delete(@RequestBody OperParamsVo paramsVo){
		String deleteIds = paramsVo.getDeleteIds();
		String[] ids = StringUtils.tokenizeToStringArray(deleteIds, ",");
		for (String id : ids) {
			operService.deleteOper(Long.parseLong(id));
		}
		return new JsonResult();
	}
	
	@RequestMapping(value="/download")
	public void download(OperParamsVo operQueryVo,HttpServletResponse response){
		List<OperVo> data =  operService.query(operQueryVo);
		
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> result = change(data.get(i));
			list.add(result);
		}
		
		String sheetName = "操作信息";
		String title = "操作信息";
		String fileName = "操作信息.xlsx";
		String[] headers = { "操作编号@operCode", "操作@operName", "应用@appName", "所属功能@functionName",
				"状态@status","创建人@createUserName","创建时间@createTime"};
		
		FileDownloadUtil.exportToExcel(response,sheetName,title,fileName,headers,list);
	}

	private Map<String, Object> change(OperVo operVo) {
		Map<String, Object> map	= BeanCopyUtil.getObjectMapProperties(operVo);
		//修改 status
		Integer status = (Integer) map.get("status");
		map.put("status",StatusEnum.STATUS_DESC_MAP.get(status));
		return map;
	}
	
	
	
	
	
}
