package com.panly.urm.manager.right.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.panly.urm.manager.common.web.JsonResult;
import com.panly.urm.manager.right.service.RightDataService;
import com.panly.urm.manager.right.vo.DataRightExecResult;
import com.panly.urm.manager.right.vo.DataRightParam;
import com.panly.urm.manager.right.vo.RightValueSetConfigSelect2;

@Controller
@RequestMapping("/right")
public class RightDataController {
	
	@Autowired
	private RightDataService rightDataService;
	
	@RequestMapping("/exec/sql")
	@ResponseBody
	public JsonResult execSql(DataRightParam dataRightParam ){
		DataRightExecResult r  = rightDataService.execSql(dataRightParam);
		return new JsonResult().setData(r);
	}
	
	@RequestMapping("/exec/value")
	@ResponseBody
	public JsonResult execValue(DataRightParam dataRightParam ){
		DataRightExecResult r  = rightDataService.execValue(dataRightParam);
		return new JsonResult().setData(r);
	}
	
	@RequestMapping("/add/db")
	@ResponseBody
	public JsonResult addDbRight(@RequestBody DataRightParam dataRightParam ){
		rightDataService.addDbRight(dataRightParam);
		return new JsonResult();
	}
	
	@RequestMapping("/del")
	@ResponseBody
	public JsonResult del(@RequestBody DataRightParam dataRightParam ){
		rightDataService.del(dataRightParam);
		return new JsonResult();
	}
	
	
	@RequestMapping("/update/db")
	@ResponseBody
	public JsonResult updateDb(@RequestBody DataRightParam dataRightParam ){
		rightDataService.updateDbRight(dataRightParam);
		return new JsonResult();
	}
	
	
	@RequestMapping("/values")
	@ResponseBody
	public JsonResult values(){
		List<RightValueSetConfigSelect2> result = rightDataService.getRightValueSetConfigSelect2s();
		return new JsonResult().setData(result);
	}
	
	@RequestMapping("/add/value")
	@ResponseBody
	public JsonResult addValueRight(@RequestBody DataRightParam dataRightParam ){
		rightDataService.addValueRight(dataRightParam);
		return new JsonResult();
	}
	
	@RequestMapping("/update/value")
	@ResponseBody
	public JsonResult updateValueRight(@RequestBody DataRightParam dataRightParam ){
		rightDataService.updateValueRight(dataRightParam);
		return new JsonResult();
	}
	
}
