package com.panly.urm.manager.right.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.panly.umr.common.BeanCopyUtil;
import com.panly.urm.manager.common.constants.RecordStatusEnum;
import com.panly.urm.manager.common.constants.StatusEnum;
import com.panly.urm.manager.common.page.core.PageDTO;
import com.panly.urm.manager.common.page.core.PageDTOUtil;
import com.panly.urm.manager.common.tree.RightRela;
import com.panly.urm.manager.common.tree.RoleRightTreeNode;
import com.panly.urm.manager.common.tree.TreeNode;
import com.panly.urm.manager.right.dao.UrmAppDao;
import com.panly.urm.manager.right.dao.UrmFunctionModelDao;
import com.panly.urm.manager.right.dao.UrmOperDao;
import com.panly.urm.manager.right.dao.UrmRoleDao;
import com.panly.urm.manager.right.entity.UrmApp;
import com.panly.urm.manager.right.entity.UrmFunctionModel;
import com.panly.urm.manager.right.entity.UrmOper;
import com.panly.urm.manager.right.entity.UrmRole;
import com.panly.urm.manager.right.vo.AcctVo;
import com.panly.urm.manager.right.vo.OperParamsVo;
import com.panly.urm.manager.right.vo.RoleParamsVo;
import com.panly.urm.manager.right.vo.RoleRelaAcctVo;
import com.panly.urm.manager.right.vo.RoleVo;
import com.panly.urm.manager.user.UserUtil;
import com.panly.urm.manager.user.service.UserService;

@Service
public class RoleService {
	
	@Autowired
	private UrmRoleDao urmRoleDao;
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private UrmAppDao urmAppDao;

	@Autowired
	private UrmFunctionModelDao urmFunctionModelDao;

	@Autowired
	private UrmOperDao urmOperDao;

	@Autowired
	private RightDataService rightDataService;
	
	@Cacheable(value="role")
	public RoleVo get(Long roleId) {
		UrmRole role = urmRoleDao.getByPrimaryKey(roleId);
		return BeanCopyUtil.copy(role, RoleVo.class);
	}

	public PageDTO<RoleVo> queryPage(RoleParamsVo roleQueryVo) {
		try {
			PageDTOUtil.startDataTablePage(roleQueryVo);
			List<UrmRole> list = urmRoleDao.query(roleQueryVo);
			PageDTO<RoleVo> page = PageDTOUtil.transform(list,RoleVo.class);
			for (RoleVo vo : page.getResultData()) {
				vo.setCreateUserName(userService.getUserName(vo.getCreateBy()));
				vo.setUpdateUserName(userService.getUserName(vo.getUpdateBy()));
			}
			return page;
		}finally{
			PageDTOUtil.endPage();
		}
	}

	public int add(RoleVo role) {
		UrmRole urmRole = BeanCopyUtil.copy(role, UrmRole.class);
		urmRole.setCreateBy(UserUtil.getUserId());
		urmRole.setCreateTime(new Date());
		return urmRoleDao.insertSelective(urmRole);
	}

	@CacheEvict(value="role",key="#role.getRoleId()")
	public int update(RoleVo role) {
		UrmRole urmRole = BeanCopyUtil.copy(role, UrmRole.class);
		return urmRoleDao.updateByPrimaryKey(urmRole);
	}
	
	@CacheEvict(value="role")
	public void delete(Long deleteId) {
		UrmRole record = new UrmRole();
		record.setRoleId(deleteId);
		record.setRecordStatus(RecordStatusEnum.DELETED.getCode());
		urmRoleDao.updateByPrimaryKey(record);
	}

	public List<RoleVo> query(RoleParamsVo roleQueryVo) {
		List<UrmRole> list = urmRoleDao.query(roleQueryVo);
		List<RoleVo> xx = BeanCopyUtil.copyList(list, RoleVo.class);
		for (RoleVo vo : xx) {
			vo.setCreateUserName(userService.getUserName(vo.getCreateBy()));
			vo.setUpdateUserName(userService.getUserName(vo.getUpdateBy()));
		}
		return xx;
	}

	public PageDTO<RoleRelaAcctVo> findRoleHaveAccts(RoleParamsVo roleParamsVo) {
		try {
			PageDTOUtil.startDataTablePage(roleParamsVo);
			List<RoleRelaAcctVo> list = urmRoleDao.findRoleHaveAccts(roleParamsVo);
			PageDTO<RoleRelaAcctVo> page = PageDTOUtil.transform(list);
			for (RoleRelaAcctVo vo : page.getResultData()) {
				vo.setCreateUserName(userService.getUserName(vo.getCreateBy()));
				vo.setUpdateUserName(userService.getUserName(vo.getUpdateBy()));
			}
			return page;
		}finally{
			PageDTOUtil.endPage();
		}
	}

	public PageDTO<AcctVo> findRoleNotHaveAccts(RoleParamsVo roleParamsVo) {
		try {
			PageDTOUtil.startDataTablePage(roleParamsVo);
			List<AcctVo> list = urmRoleDao.findRoleNotHaveAccts(roleParamsVo);
			PageDTO<AcctVo> page = PageDTOUtil.transform(list);
			for (AcctVo vo : page.getResultData()) {
				vo.setCreateUserName(userService.getUserName(vo.getCreateBy()));
				vo.setUpdateUserName(userService.getUserName(vo.getUpdateBy()));
			}
			return page;
		}finally{
			PageDTOUtil.endPage();
		}
	}
	
	public List<TreeNode> getFuncOperRightTreeNodeByRoleId(Long roleId) {
		if (roleId == null) {
			throw new RuntimeException("roleId不能为空");
		}
		
		//获取app
		UrmApp appRecord = new UrmApp();
		appRecord.setStatus(StatusEnum.NORMAL.getCode());
		appRecord.setRecordStatus(RecordStatusEnum.NORMAL.getCode());
		List<UrmApp> apps = urmAppDao.find(appRecord);

		//获取所有功能
		UrmFunctionModel funcRecord = new UrmFunctionModel();
		funcRecord.setStatus(StatusEnum.NORMAL.getCode());
		funcRecord.setRecordStatus(RecordStatusEnum.NORMAL.getCode());
		List<UrmFunctionModel> funcList = urmFunctionModelDao.find(funcRecord);

		//获取所有的操作
		OperParamsVo paramsVo = new OperParamsVo();
		paramsVo.setStatus(StatusEnum.NORMAL.getCode());
		List<UrmOper> operList = urmOperDao.query(paramsVo);
		
		//获取该用户下所有的right关联，
		List<RightRela> relas = rightDataService.getRoleRightRela(roleId);
		
		for (RightRela rightRela : relas) {
			rightRela.setCreateUserName(userService.getUserName(rightRela.getCreateBy()));
		}
		
		List<TreeNode> results = new ArrayList<>();
		
		for (int i = 0; i < apps.size(); i++) {
			UrmApp app = apps.get(i);
			TreeNode appNode = new TreeNode(app.getAppId().toString(),
					app.getAppCode(), app.getAppName(), TreeNode.TYPE_APP);
			
			List<TreeNode> childNodes = new ArrayList<>();

			List<UrmFunctionModel> firstLevelFuncions = getChildFunction(app.getAppId(), null,funcList);
			if (firstLevelFuncions.size() > 0) {
				for (UrmFunctionModel func : firstLevelFuncions) {
					TreeNode node = buildFuncTreeNode(func, funcList, operList,relas);
					childNodes.add(node);
				}
				appNode.setNodes(childNodes);
			}
			results.add(appNode);
		}
		return results;
	}
	
	
	private TreeNode buildFuncTreeNode(UrmFunctionModel func, List<UrmFunctionModel> funcList, List<UrmOper> operList, List<RightRela> relas) {
		TreeNode treeNode = new TreeNode(func.getFunctionId().toString(), func.getFunctionCode(),
				func.getFunctionName(), TreeNode.TYPE_FUNC);
		List<TreeNode> nodes = new ArrayList<>();
		
		//获取functionNode
		List<UrmFunctionModel> childFuncions = getChildFunction(func.getAppId(),func.getFunctionId(), funcList);
		if (childFuncions.size() > 0) {
			for (UrmFunctionModel child : childFuncions) {
				TreeNode childTree = buildFuncTreeNode(child, funcList, operList, relas);
				nodes.add(childTree);
			}
		}

		// 获取 operNode
		List<UrmOper> childOpers = getChildOper(func.getFunctionId(), operList);
		if (childOpers != null && childOpers.size() > 0) {
			for (UrmOper urmOper : childOpers) {
				RoleRightTreeNode operNode = new RoleRightTreeNode(urmOper.getOperId().toString(), urmOper.getOperCode(),
						urmOper.getOperName(), TreeNode.TYPE_OPER);
				operNode.setOperName(urmOper.getOperName());
				UrmFunctionModel parentFunction = getFunction(urmOper.getFunctionId(), funcList);
				if(parentFunction!=null){
					operNode.setFunctionName(parentFunction.getFunctionName());
					operNode.setFunctionId(parentFunction.getFunctionId());
					operNode.setFunctionCode(parentFunction.getFunctionCode());
				}
				List<RightRela> operRightRelas = getOperRightRela(urmOper.getOperId(),relas);
				if(operRightRelas.size()>0){
					operNode.setText(operNode.getText()+"(已授权)");
					RightRela rela = operRightRelas.get(0);
					operNode.setOperRelaId(rela.getRelaId());
					operNode.setCreateBy(rela.getCreateBy());
					operNode.setCreateUserName(rela.getCreateUserName());
					operNode.setCreateTime(rela.getCreateTime());
					operNode.setRights(rela.getRights());
				}else{
					operNode.setText(operNode.getText()+"(未授权)");
				}
				nodes.add(operNode);
			}
		}
		if(nodes.size()>0){
			treeNode.setNodes(nodes);
		}
		return treeNode;
	}
	
	private UrmFunctionModel getFunction(Long functionId, List<UrmFunctionModel> funcList) {
		for (UrmFunctionModel urmFunctionModel : funcList) {
			if(Objects.equals(functionId, urmFunctionModel.getFunctionId())){
				return urmFunctionModel;
			}
		}
		return null;
	}

	private List<RightRela> getOperRightRela(Long operId, List<RightRela> relas) {
		List<RightRela> list = new ArrayList<>();
		for (RightRela rightRela : relas) {
			if(Objects.equals(operId, rightRela.getOperId())){
				list.add(rightRela);
			}
		}
		return list;
	}

	private List<UrmOper> getChildOper(Long functionId, List<UrmOper> operList) {
		List<UrmOper> list = new ArrayList<>();
		for (UrmOper urmOper : operList) {
			if (ObjectUtils.equals(urmOper.getFunctionId(), functionId)) {
				list.add(urmOper);
			}
		}
		return list;
	}

	
	/**
	 * 查询
	 * @param appId
	 * @param parentId
	 * @param functionList
	 * @return
	 */
	private List<UrmFunctionModel> getChildFunction(Long appId,Long parentId,
			List<UrmFunctionModel> functionList) {
		List<UrmFunctionModel> result = new ArrayList<>();
		for (int i = 0; i < functionList.size(); i++) {
			UrmFunctionModel f = functionList.get(i);
			if (ObjectUtils.equals(f.getAppId(),appId)&&Objects.equals(f.getParentFunctionId(), parentId)) {
				result.add(f);
			}
		}
		return result;
	}
	
}
