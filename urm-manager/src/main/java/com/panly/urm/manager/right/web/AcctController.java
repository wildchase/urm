package com.panly.urm.manager.right.web;

import java.io.IOException;
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

import com.panly.urm.common.BeanCopyUtil;
import com.panly.urm.common.JsonUtil;
import com.panly.urm.manager.common.constants.OperTypeEnum;
import com.panly.urm.manager.common.constants.StatusEnum;
import com.panly.urm.manager.common.excel.FileDownloadUtil;
import com.panly.urm.page.core.PageDTO;
import com.panly.urm.page.core.PageDTOUtil;
import com.panly.urm.manager.common.tree.TreeNode;
import com.panly.urm.manager.log.anno.Log;
import com.panly.urm.manager.right.config.DbConfig;
import com.panly.urm.manager.right.service.AcctRoleRelaService;
import com.panly.urm.manager.right.service.AcctService;
import com.panly.urm.manager.right.service.OperRelaService;
import com.panly.urm.manager.right.vo.AcctParamsVo;
import com.panly.urm.manager.right.vo.AcctRelaRoleVo;
import com.panly.urm.manager.right.vo.AcctVo;
import com.panly.urm.manager.right.vo.RoleVo;
import com.panly.urm.manager.user.anno.MenuOp;
import com.panly.urm.web.JsonResult;

@MenuOp("101001")
@Controller
@RequestMapping("/acct")
public class AcctController {

	@Autowired
	private AcctService acctService;
	
	@Autowired
	private AcctRoleRelaService acctRoleRelaService;
	
	@Autowired
	private OperRelaService operRelaService;
	
	@Autowired
	private DbConfig dbConfig;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toAcct(HttpServletRequest req) {
		return "right/acct/acct-list";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(Long acctId, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView("right/acct/acct-detail");
		mav.addObject("acct", acctService.get(acctId));
		mav.addObject("dbs", dbConfig.getRightDbConfigs());
		mav.addObject("values", dbConfig.getRightValueSetConfigs());
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/page")
	public JsonResult page(AcctParamsVo acctQueryVo) {
		PageDTO<AcctVo> page = acctService.queryPage(acctQueryVo);
		return PageDTOUtil.changePageToDataTableResult(page);
	}

	@Log(type = OperTypeEnum.ACCT_ADD, value = "[${userName}]添加账号 ${acctVo.acctName} 电话号码${acctVo.phone},邮箱 ${acctVo.email} ")
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@RequestBody AcctVo acctVo) {
		acctService.addAcct(acctVo);
		return new JsonResult();
	}

	@Log(type = OperTypeEnum.ACCT_EDIT, value = "[${userName}]修改账号[${acctVo.acctId}]  账号名称${acctVo.acctName},电话号码${acctVo.phone},邮箱 ${acctVo.email},状态 ${acctVo.status}")
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JsonResult edit(@RequestBody AcctVo acctVo) {
		acctService.updateAcct(acctVo);
		return new JsonResult();
	}

	@Log(type = OperTypeEnum.ACCT_DEL, value = "[${userName}]删除账号${acctQueryVo.deleteIds}")
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonResult delete(@RequestBody AcctParamsVo acctQueryVo) {
		String deleteIds = acctQueryVo.getDeleteIds();
		String[] ids = StringUtils.tokenizeToStringArray(deleteIds, ",");
		for (String id : ids) {
			acctService.deleteAcct(Long.parseLong(id));
		}
		return new JsonResult();
	}

	@RequestMapping(value = "/download")
	public void download(AcctParamsVo acctQueryVo, HttpServletResponse response) {
		List<AcctVo> data = acctService.query(acctQueryVo);

		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> result = change(data.get(i));
			list.add(result);
		}

		String sheetName = "账户信息";
		String title = "账户信息";
		String fileName = "账户信息.xlsx";
		String[] headers = { "账户Id@acctId", "账户@acctName", "手机@phone",
				"邮箱@email", "状态@status", "上次登录@lastLoginTime",
				"登录IP@lastLoginIp", "创建人@createUserName", "创建时间@createTime" };

		FileDownloadUtil.exportToExcel(response, sheetName, title, fileName,
				headers, list);
	}

	private Map<String, Object> change(AcctVo acctVo) {
		Map<String, Object> map = BeanCopyUtil.getObjectMapProperties(acctVo);
		// 修改 status
		Integer status = (Integer) map.get("status");
		map.put("status", StatusEnum.STATUS_DESC_MAP.get(status));
		return map;
	}

	/**
	 * 详情页面 查询 用户拥有的角色
	 * 
	 * @param acctQueryVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/role/have")
	public JsonResult queryRoleList(AcctParamsVo acctQueryVo) {
		PageDTO<AcctRelaRoleVo> page = acctService
				.findAcctHaveRoles(acctQueryVo);
		return PageDTOUtil.changePageToDataTableResult(page);
	}

	/**
	 * 详情页面 获取所有该用户没有的角色
	 * 
	 * @param acctQueryVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/role/nothave")
	public JsonResult query(AcctParamsVo acctQueryVo) {
		PageDTO<RoleVo> page = acctService.findAcctNotHaveRoles(acctQueryVo);
		return PageDTOUtil.changePageToDataTableResult(page);
	}
	
	@Log(type = OperTypeEnum.ACCT_ROLE_ADD, value = "${userName}为账号[${acctQueryVo.acctId}]添加角色 [${acctQueryVo.roleIds}]")
	@ResponseBody
	@RequestMapping(value = "/role/add", method = RequestMethod.POST)
	public JsonResult addRoleRela(@RequestBody AcctParamsVo acctQueryVo) {
		String[] ids = StringUtils.tokenizeToStringArray(
				acctQueryVo.getRoleIds(), ",");
		for (String roleId : ids) {
			acctRoleRelaService
					.addRela(acctQueryVo.getAcctId(), Long.parseLong(roleId));
		}
		return new JsonResult();
	}

	@Log(type = OperTypeEnum.ACCT_ROLE_DEL, value = "${userName}为账号[${acctQueryVo.acctId}]删除角色 [${acctQueryVo.deleteIds}]")
	@ResponseBody
	@RequestMapping(value = "/role/del", method = RequestMethod.POST)
	public JsonResult delRoleRela(@RequestBody AcctParamsVo acctQueryVo) {
		String[] relaIds = StringUtils.tokenizeToStringArray(
				acctQueryVo.getDeleteIds(), ",");
		for (String relaId : relaIds) {
			acctRoleRelaService.delRela(Long.parseLong(relaId));
		}
		return new JsonResult();
	}
	
	
	//获取app下面的操作和功能
	@RequestMapping(value="/func/oper/tree")
	public void getFuncOperTreeNodeByAcctId(AcctParamsVo acctParamsVo,HttpServletResponse resp) throws IOException{
		List<TreeNode> nodes = acctService.getAcctFuncOperRightTreeNode(acctParamsVo);
		JsonResult ret = new JsonResult().setData(nodes);
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(JsonUtil.toJsonDataFormat(ret));
	}
	
	
	@Log(type = OperTypeEnum.ACCT_OPER_ADD, value = "${userName}为账号[${acctParamVo.acctId}]添加操作关联 [${acctParamVo.operId}]")
	@ResponseBody
	@RequestMapping(value = "/oper/add", method = RequestMethod.POST)
	public JsonResult addOperRela(@RequestBody AcctParamsVo acctParamVo) {
		operRelaService.addAcctOperRela(acctParamVo.getAcctId(), acctParamVo.getOperId());
		return new JsonResult();
	}

	@Log(type = OperTypeEnum.ACCT_OPER_DEL, value = "${userName}为账号[${acctParamVo.acctId}]删除操作关联 [${acctParamVo.relaId}],关联类型[${acctParamVo.relaType}]")
	@ResponseBody
	@RequestMapping(value = "/oper/del", method = RequestMethod.POST)
	public JsonResult delOperRela(@RequestBody AcctParamsVo acctParamVo) {
		operRelaService.delOperRela(acctParamVo.getRelaId(), acctParamVo.getRelaType());
		return new JsonResult();
	}
	

}
