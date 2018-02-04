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
import com.panly.urm.manager.common.select.CxSelect;
import com.panly.urm.manager.common.web.JsonResult;
import com.panly.urm.manager.log.anno.Log;
import com.panly.urm.manager.right.service.FuncService;
import com.panly.urm.manager.right.vo.FuncParamsVo;
import com.panly.urm.manager.right.vo.FuncVo;
import com.panly.urm.manager.user.anno.MenuOp;

@MenuOp("101004")
@Controller
@RequestMapping("/func")
public class FuncController {

	@Autowired
	private FuncService funcService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String toapp( HttpServletRequest req){
		return "right/func/func-list";
	}
	
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public ModelAndView detail(Long functionId,HttpServletRequest req){
		ModelAndView mav = new ModelAndView("right/func/func-detail");
		mav.addObject("func", funcService.get(functionId));
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/page")
	public JsonResult page(FuncParamsVo paramsVo){
		PageDTO<FuncVo> page = funcService.queryPage(paramsVo);
		return PageDTOUtil.changePageToDataTableResult(page);
	}
	
	@Log(type = OperTypeEnum.FUNC_ADD ,value = "[${userName}]添加功能[${func.functionName}] 功能编号[${func.functionCode}] ")
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public JsonResult add(@RequestBody FuncVo func){
		funcService.add(func);
		return new JsonResult();
	}
	
	@Log(type = OperTypeEnum.FUNC_EDIT ,value = "[${userName}]修改功能[${func.functionId}],修改的内容是功能名称[${func.functionName}] 功能编号[${func.functionCode}]")
	@ResponseBody
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public JsonResult edit(@RequestBody FuncVo func){
		funcService.update(func);
		return new JsonResult();
	}
	
	@Log(type = OperTypeEnum.FUNC_DEL ,value = "[${userName}]删除功能[${paramsVo.deleteIds}]")
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public JsonResult delete(@RequestBody FuncParamsVo paramsVo){
		String deleteIds = paramsVo.getDeleteIds();
		String[] ids = StringUtils.tokenizeToStringArray(deleteIds, ",");
		for (String id : ids) {
			funcService.delete(Long.parseLong(id));
		}
		return new JsonResult();
	}
	
	@RequestMapping(value="/download")
	public void download(FuncParamsVo paramsVo,HttpServletResponse response){
		List<FuncVo> data =  funcService.query(paramsVo);
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> result = change(data.get(i));
			list.add(result);
		}
		
		String sheetName = "功能信息";
		String title = "功能信息";
		String fileName = "功能信息.xlsx";
		String[] headers = { "功能编号@functionCode", "功能名称@functionName","应用名称@appName","上级功能@parentFunctionName", "状态@status", "创建人@createUserName","创建时间@createTime"};
		FileDownloadUtil.exportToExcel(response,sheetName,title,fileName,headers,list);
	}

	private Map<String, Object> change(Object obj) {
		Map<String, Object> map	= BeanCopyUtil.getObjectMapProperties(obj);
		//修改 status
		Integer status = (Integer) map.get("status");
		map.put("status",StatusEnum.STATUS_DESC_MAP.get(status));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="/app/cxselect")
	public List<CxSelect> appFunc(){
		List<CxSelect> list = funcService.getAppFuncCxSelect();
		return list;
	}
}
