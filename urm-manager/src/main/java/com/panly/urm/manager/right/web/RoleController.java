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
import com.panly.urm.manager.right.service.OperRelaService;
import com.panly.urm.manager.right.service.RoleService;
import com.panly.urm.manager.right.vo.AcctVo;
import com.panly.urm.manager.right.vo.RoleParamsVo;
import com.panly.urm.manager.right.vo.RoleRelaAcctVo;
import com.panly.urm.manager.right.vo.RoleVo;
import com.panly.urm.manager.user.anno.MenuOp;
import com.panly.urm.web.JsonResult;


@MenuOp("101002")
@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	public RoleService roleService;
	
	@Autowired
	private AcctRoleRelaService acctRoleRelaService;
	
	@Autowired
	private OperRelaService operRelaService;
	
	@Autowired
	private DbConfig dbConfig;
	
	/**
	 * 进入登录页面
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String toList(HttpServletRequest req){
		return "right/role/role-list";
	}
	
	/**
	 * 进入详情页面
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public ModelAndView detail(Long roleId,HttpServletRequest req){
		ModelAndView mav = new ModelAndView("right/role/role-detail");
		mav.addObject("role", roleService.get(roleId));
		mav.addObject("dbs", dbConfig.getRightDbConfigs());
		mav.addObject("values", dbConfig.getRightValueSetConfigs());
		return mav;
	}
	
	/**
	 * 分页查询
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/page")
	public JsonResult page(RoleParamsVo roleQueryVo){
		PageDTO<RoleVo> page = roleService.queryPage(roleQueryVo);
		return PageDTOUtil.changePageToDataTableResult(page);
	}
	
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	@Log(type = OperTypeEnum.ROLE_ADD ,value = "[${userName}]添加角色[${role.roleName}]")
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public JsonResult add(@RequestBody RoleVo role){
		roleService.add(role);
		return new JsonResult();
	}
	
	/**
	 * 修改
	 * @param role
	 * @return
	 */
	@Log(type = OperTypeEnum.ROLE_EDIT ,value = "[${userName}]修改角色[${role.roleId}],角色名称[${role.roleName}],角色编号[${role.roleCode}]")
	@ResponseBody
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public JsonResult edit(@RequestBody RoleVo role){
		roleService.update(role);
		return new JsonResult();
	}
	
	/**
	 * 删除
	 * @param role
	 * @return
	 */
	@Log(type = OperTypeEnum.ROLE_DEL ,value = "[${userName}]删除角色[${paramsVo.deleteIds}]")
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public JsonResult delete(@RequestBody RoleParamsVo paramsVo){
		String deleteIds = paramsVo.getDeleteIds();
		String[] ids = StringUtils.tokenizeToStringArray(deleteIds, ",");
		for (String id : ids) {
			roleService.delete(Long.parseLong(id));
		}
		return new JsonResult();
	}
	
	/**
	 * 下载
	 * @param roleQueryVo
	 * @param response
	 */
	@RequestMapping(value="/download")
	public void download(RoleParamsVo roleQueryVo,HttpServletResponse response){
		List<RoleVo> data =  roleService.query(roleQueryVo);
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> result = change(data.get(i));
			list.add(result);
		}
		String sheetName = "角色信息";
		String title = "角色信息";
		String fileName = "角色信息.xlsx";
		String[] headers = { "角色Id@roleId", "角色编号@roleCode", "角色名称@roleName", "角色介绍@roleDesc",
				"状态@status", "创建人@createUserName","创建时间@createTime"};
		FileDownloadUtil.exportToExcel(response,sheetName,title,fileName,headers,list);
	}
	private Map<String, Object> change(Object object) {
		Map<String, Object> map	= BeanCopyUtil.getObjectMapProperties(object);
		//修改 status
		Integer status = (Integer) map.get("status");
		map.put("status",StatusEnum.STATUS_DESC_MAP.get(status));
		return map;
	}
	
	
	
	/**
	 * 详情页面 查询 角色对应的用户
	 * @param roleQueryVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/acct/have")
	public JsonResult queryRoleList(RoleParamsVo roleParamsVo) {
		PageDTO<RoleRelaAcctVo> page = roleService.findRoleHaveAccts(roleParamsVo);
		return PageDTOUtil.changePageToDataTableResult(page);
	}

	/**
	 * 详情页面没有该角色的用户
	 * @param roleQueryVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/acct/nothave")
	public JsonResult query(RoleParamsVo roleParamsVo) {
		PageDTO<AcctVo> page = roleService.findRoleNotHaveAccts(roleParamsVo);
		return PageDTOUtil.changePageToDataTableResult(page);
	}
	
	@Log(type = OperTypeEnum.ROLE_ACCT_ADD, value = "${userName}为角色[${roleParamsVo.roleId}]添加账户 [${roleParamsVo.acctIds}]")
	@ResponseBody
	@RequestMapping(value = "/acct/add", method = RequestMethod.POST)
	public JsonResult addAcctRela(@RequestBody RoleParamsVo roleParamsVo) {
		String[] ids = StringUtils.tokenizeToStringArray(
				roleParamsVo.getAcctIds(), ",");
		for (String acctId : ids) {
			acctRoleRelaService.addRela(Long.parseLong(acctId),roleParamsVo.getRoleId());
		}
		return new JsonResult();
	}

	@Log(type = OperTypeEnum.ROLE_ACCT_DEL, value = "${userName}为角色[${roleParamsVo.roleId}]删除关联关系[${roleParamsVo.deleteIds}]")
	@ResponseBody
	@RequestMapping(value = "/acct/del", method = RequestMethod.POST)
	public JsonResult delAcctRela(@RequestBody RoleParamsVo roleParamsVo) {
		String[] relaIds = StringUtils.tokenizeToStringArray(roleParamsVo.getDeleteIds(), ",");
		for (String relaId : relaIds) {
			acctRoleRelaService.delRela(Long.parseLong(relaId));
		}
		return new JsonResult();
	}
	
	//获取app下面的操作和功能
	@RequestMapping(value="/func/oper/tree")
	public void getFuncOperTreeNodeByRoleId(RoleParamsVo roleParamsVo,HttpServletResponse resp) throws IOException{
		List<TreeNode> nodes = roleService.getFuncOperRightTreeNodeByRoleId(roleParamsVo.getRoleId());
		JsonResult ret = new JsonResult().setData(nodes);
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(JsonUtil.toJsonDataFormat(ret));
	}
	
	
	@Log(type = OperTypeEnum.ROLE_OPER_ADD, value = "${userName}为账号[${roleParamVo.roleId}]添加操作关联 [${roleParamVo.operId}]")
	@ResponseBody
	@RequestMapping(value = "/oper/add", method = RequestMethod.POST)
	public JsonResult addOperRela(@RequestBody RoleParamsVo roleParamVo) {
		operRelaService.addRoleOperRela(roleParamVo.getRoleId(), roleParamVo.getOperId());
		return new JsonResult();
	}

	@Log(type = OperTypeEnum.ROLE_OPER_DEL, value = "${userName}为账号[${roleParamVo.roleId}]删除操作关联 [${roleParamVo.relaId}],关联类型[${roleParamVo.relaType}]")
	@ResponseBody
	@RequestMapping(value = "/oper/del", method = RequestMethod.POST)
	public JsonResult delOperRela(@RequestBody RoleParamsVo roleParamVo) {
		operRelaService.delOperRela(roleParamVo.getRelaId(), roleParamVo.getRelaType());
		return new JsonResult();
	}

}
