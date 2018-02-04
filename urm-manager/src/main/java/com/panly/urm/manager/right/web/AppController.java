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
import com.panly.urm.manager.right.service.AppService;
import com.panly.urm.manager.right.vo.AppParamsVo;
import com.panly.urm.manager.right.vo.AppVo;
import com.panly.urm.manager.user.anno.MenuOp;

@MenuOp("101003")
@Controller
@RequestMapping("/app")
public class AppController {

	@Autowired
	private AppService appService;

	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String toapp( HttpServletRequest req){
		return "right/app/app-list";
	}
	
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public ModelAndView detail(Long appId,HttpServletRequest req){
		ModelAndView mav = new ModelAndView("right/app/app-detail");
		mav.addObject("app", appService.get(appId));
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/page")
	public JsonResult page(AppParamsVo appQueryVo){
		PageDTO<AppVo> page = appService.queryPage(appQueryVo);
		return PageDTOUtil.changePageToDataTableResult(page);
	}
	
	@Log(type = OperTypeEnum.APP_ADD ,value = "[${userName}]添加应用${app.appName} 应用编号${app.appCode} ")
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public JsonResult add(@RequestBody AppVo app){
		appService.addApp(app);
		return new JsonResult();
	}
	
	@Log(type = OperTypeEnum.APP_EDIT ,value = "[${userName}]修改应用${app.appId}, 修改的内容是 ${app.appName} 应用编号${app.appCode} ")
	@ResponseBody
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public JsonResult edit(@RequestBody AppVo app){
		appService.updateApp(app);
		return new JsonResult();
	}
	
	@Log(type = OperTypeEnum.APP_DEL ,value = "[${userName}]删除应用${appQueryVo.deleteIds} ")
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public JsonResult delete(@RequestBody AppParamsVo appQueryVo){
		String deleteIds = appQueryVo.getDeleteIds();
		String[] ids = StringUtils.tokenizeToStringArray(deleteIds, ",");
		for (String id : ids) {
			appService.deleteApp(Long.parseLong(id));
		}
		return new JsonResult();
	}
	
	@RequestMapping(value="/download")
	public void download(AppParamsVo appQueryVo,HttpServletResponse response){
		List<AppVo> data =  appService.query(appQueryVo);
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> result = change(data.get(i));
			list.add(result);
		}
		
		String sheetName = "应用信息";
		String title = "应用信息";
		String fileName = "应用信息.xlsx";
		String[] headers = { "应用编号@appId", "应用名称@appName", "状态@status", "创建人@createUserName","创建时间@createTime"};
		
		FileDownloadUtil.exportToExcel(response,sheetName,title,fileName,headers,list);
	}

	private Map<String, Object> change(Object obj) {
		Map<String, Object> map	= BeanCopyUtil.getObjectMapProperties(obj);
		//修改 status
		Integer status = (Integer) map.get("status");
		map.put("status",StatusEnum.STATUS_DESC_MAP.get(status));
		return map;
	}
	
	
}
