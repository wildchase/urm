package com.panly.urm.manager.right.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.panly.urm.common.BeanCopyUtil;
import com.panly.urm.manager.common.constants.RecordStatusEnum;
import com.panly.urm.manager.common.constants.StatusEnum;
import com.panly.urm.manager.common.tree.RightRela;
import com.panly.urm.manager.common.tree.AcctRightTreeNode;
import com.panly.urm.manager.common.tree.TreeNode;
import com.panly.urm.manager.right.dao.UrmAcctDao;
import com.panly.urm.manager.right.dao.UrmAppDao;
import com.panly.urm.manager.right.dao.UrmFunctionModelDao;
import com.panly.urm.manager.right.dao.UrmOperDao;
import com.panly.urm.manager.right.entity.UrmAcct;
import com.panly.urm.manager.right.entity.UrmApp;
import com.panly.urm.manager.right.entity.UrmFunctionModel;
import com.panly.urm.manager.right.entity.UrmOper;
import com.panly.urm.manager.right.vo.AcctParamsVo;
import com.panly.urm.manager.right.vo.AcctRelaRoleVo;
import com.panly.urm.manager.right.vo.AcctVo;
import com.panly.urm.manager.right.vo.OperParamsVo;
import com.panly.urm.manager.right.vo.RoleVo;
import com.panly.urm.manager.user.UserUtil;
import com.panly.urm.manager.user.service.UserService;
import com.panly.urm.page.core.PageDTO;
import com.panly.urm.page.core.PageDTOUtil;
import com.panly.urm.secret.Md5Util;

@Service
public class AcctService {

	@Autowired
	private UrmAcctDao urmAcctDao;

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

	@Cacheable(value = "acct")
	public AcctVo get(Long acctId) {
		return BeanCopyUtil.copy(urmAcctDao.getByPrimaryKey(acctId),
				AcctVo.class);
	}

	/**
	 * 分页查询
	 * 
	 * @param acctQueryVo
	 * @return
	 */
	public PageDTO<AcctVo> queryPage(AcctParamsVo acctQueryVo) {
		try {
			PageDTOUtil.startDataTablePage(acctQueryVo);
			List<UrmAcct> list = urmAcctDao.query(acctQueryVo);
			PageDTO<AcctVo> page = PageDTOUtil.transform(list, AcctVo.class);
			for (AcctVo vo : page.getResultData()) {
				vo.setCreateUserName(userService.getUserName(vo.getCreateBy()));
				vo.setUpdateUserName(userService.getUserName(vo.getUpdateBy()));
			}
			return page;
		} finally {
			PageDTOUtil.endPage();
		}
	}

	/**
	 * 添加账号
	 * 
	 * @param acctVo
	 */
	public int addAcct(AcctVo acctVo) {
		UrmAcct acct = BeanCopyUtil.copy(acctVo, UrmAcct.class);
		acct.setStatus(StatusEnum.NORMAL.getCode());
		String salt = RandomStringUtils.randomNumeric(4);
		String password = Md5Util.encrptWithSalt(acctVo.getPassword(), salt);
		acct.setSalt(salt);
		acct.setPassword(password);
		acct.setCreateBy(UserUtil.getUserId());
		acct.setCreateTime(new Date());
		return urmAcctDao.insertSelective(acct);
	}

	/**
	 * 修改账号
	 * 
	 * @param acctVo
	 */
	@CacheEvict(value = "acct", key = "#acctVo.acctId")
	public int updateAcct(AcctVo acctVo) {
		UrmAcct acct = BeanCopyUtil.copy(acctVo, UrmAcct.class);
		acct.setUpdateBy(UserUtil.getUserId());
		acct.setUpdateTime(new Date());
		return urmAcctDao.updateByPrimaryKey(acct);
	}

	@CacheEvict(value = "acct")
	public void deleteAcct(Long deleteId) {
		UrmAcct record = new UrmAcct();
		record.setAcctId(deleteId);
		record.setRecordStatus(RecordStatusEnum.DELETED.getCode());
		urmAcctDao.updateByPrimaryKey(record);
	}

	public List<AcctVo> query(AcctParamsVo acctQueryVo) {
		List<UrmAcct> list = urmAcctDao.query(acctQueryVo);
		List<AcctVo> result = BeanCopyUtil.copyList(list, AcctVo.class);
		for (AcctVo vo : result) {
			vo.setCreateUserName(userService.getUserName(vo.getCreateBy()));
			vo.setUpdateUserName(userService.getUserName(vo.getUpdateBy()));
		}
		return result;
	}

	/**
	 * 获取acct 对应的拥有的role的信息
	 * 
	 * @param acctId
	 * @return
	 */
	public PageDTO<AcctRelaRoleVo> findAcctHaveRoles(AcctParamsVo acctQueryVo) {
		try {
			PageDTOUtil.startDataTablePage(acctQueryVo);
			List<AcctRelaRoleVo> list = urmAcctDao.findAcctRoles(acctQueryVo);
			PageDTO<AcctRelaRoleVo> page = PageDTOUtil.transform(list);
			for (AcctRelaRoleVo vo : page.getResultData()) {
				vo.setCreateUserName(userService.getUserName(vo.getCreateBy()));
				vo.setUpdateUserName(userService.getUserName(vo.getUpdateBy()));
			}
			return page;
		} finally {
			PageDTOUtil.endPage();
		}
	}

	public PageDTO<RoleVo> findAcctNotHaveRoles(AcctParamsVo acctQueryVo) {
		try {
			PageDTOUtil.startDataTablePage(acctQueryVo);
			List<RoleVo> list = urmAcctDao.findAcctNotHaveRoles(acctQueryVo);
			PageDTO<RoleVo> page = PageDTOUtil.transform(list);
			for (RoleVo vo : page.getResultData()) {
				vo.setCreateUserName(userService.getUserName(vo.getCreateBy()));
				vo.setUpdateUserName(userService.getUserName(vo.getUpdateBy()));
			}
			return page;
		} finally {
			PageDTOUtil.endPage();
		}
	}

	public List<TreeNode> getAcctFuncOperRightTreeNode(AcctParamsVo acctParamsVo) {
		Long acctId = acctParamsVo.getAcctId();
		if (acctId == null) {
			throw new RuntimeException("acctId不能为空");
		}
		UrmApp appRecord = new UrmApp();
		appRecord.setStatus(StatusEnum.NORMAL.getCode());
		appRecord.setRecordStatus(RecordStatusEnum.NORMAL.getCode());
		List<UrmApp> apps = urmAppDao.find(appRecord);

		UrmFunctionModel funcRecord = new UrmFunctionModel();
		funcRecord.setStatus(StatusEnum.NORMAL.getCode());
		funcRecord.setRecordStatus(RecordStatusEnum.NORMAL.getCode());
		List<UrmFunctionModel> funcList = urmFunctionModelDao.find(funcRecord);

		// 获取所有的操作
		OperParamsVo paramsVo = new OperParamsVo();
		paramsVo.setStatus(StatusEnum.NORMAL.getCode());
		List<UrmOper> operList = urmOperDao.query(paramsVo);

		// 获取该用户下所有的right关联，
		List<RightRela> relas = rightDataService.getAcctRightRela(acctId);
		for (RightRela rightRela : relas) {
			rightRela.setCreateUserName(userService.getUserName(rightRela
					.getCreateBy()));
		}

		List<TreeNode> results = new ArrayList<>();

		for (int i = 0; i < apps.size(); i++) {
			UrmApp app = apps.get(i);
			TreeNode appNode = new TreeNode(app.getAppId().toString(),
					app.getAppCode(), app.getAppName(), TreeNode.TYPE_APP);

			List<TreeNode> childNodes = new ArrayList<>();

			List<UrmFunctionModel> firstLevelFuncions = getChildFunction(
					app.getAppId(), null, funcList);
			if (firstLevelFuncions.size() > 0) {
				for (UrmFunctionModel func : firstLevelFuncions) {
					TreeNode node = buildFuncTreeNode(func, funcList, operList,
							relas);
					childNodes.add(node);
				}
				appNode.setNodes(childNodes);
			}
			results.add(appNode);
		}
		return results;
	}


	private TreeNode buildFuncTreeNode(UrmFunctionModel func,
			List<UrmFunctionModel> funcList, List<UrmOper> operList,
			List<RightRela> relas) {
		TreeNode treeNode = new TreeNode(func.getFunctionId().toString(),
				func.getFunctionCode(), func.getFunctionName(),
				TreeNode.TYPE_FUNC);
		List<TreeNode> nodes = new ArrayList<>();

		// 获取functionNode
		List<UrmFunctionModel> childFuncions = getChildFunction(
				func.getAppId(), func.getFunctionId(), funcList);
		if (childFuncions.size() > 0) {
			for (UrmFunctionModel child : childFuncions) {
				TreeNode childTree = buildFuncTreeNode(child, funcList,
						operList, relas);
				nodes.add(childTree);
			}
		}

		// 获取 operNode
		List<UrmOper> childOpers = getChildOper(func.getFunctionId(), operList);
		if (childOpers != null && childOpers.size() > 0) {
			for (UrmOper urmOper : childOpers) {
				AcctRightTreeNode operNode = new AcctRightTreeNode(urmOper
						.getOperId().toString(), urmOper.getOperCode(),
						urmOper.getOperName(), TreeNode.TYPE_OPER);

				UrmFunctionModel parentFunction = getFunction(
						urmOper.getFunctionId(), funcList);
				if (parentFunction != null) {
					operNode.setFunctionName(parentFunction.getFunctionName());
					operNode.setFunctionId(parentFunction.getFunctionId());
					operNode.setFunctionCode(parentFunction.getFunctionCode());
				}

				List<RightRela> operRightRelas = getOperRightRela(
						urmOper.getOperId(), relas);
				operNode.setRelas(operRightRelas);
				if (operRightRelas.size() > 0) {
					operNode.setText(operNode.getText() + "(已授权)");
				} else {
					operNode.setText(operNode.getText() + "(未授权)");
				}
				operNode.setOperName(urmOper.getOperName());

				RightRela acctRightRela = null;

				// 若是所有的授权中，存在账户授权
				a: for (RightRela rightRela : operRightRelas) {
					if (Objects.equals(rightRela.getRelaType(), "1")) {
						acctRightRela = rightRela;
						break a;
					}
				}
				if (acctRightRela == null) {
					operNode.setHaveAcctOper(false);
				} else {
					operNode.setHaveAcctOper(true);
					operNode.setAcctOperRelaId(acctRightRela.getRelaId());
					operNode.setCreateBy(acctRightRela.getCreateBy());
					operNode.setCreateUserName(acctRightRela
							.getCreateUserName());
					operNode.setCreateTime(acctRightRela.getCreateTime());
				}
				nodes.add(operNode);
			}
		}
		if (nodes.size() > 0) {
			treeNode.setNodes(nodes);
		}
		return treeNode;
	}

	private UrmFunctionModel getFunction(Long functionId,
			List<UrmFunctionModel> funcList) {
		for (UrmFunctionModel urmFunctionModel : funcList) {
			if (Objects.equals(functionId, urmFunctionModel.getFunctionId())) {
				return urmFunctionModel;
			}
		}
		return null;
	}

	private List<RightRela> getOperRightRela(Long operId, List<RightRela> relas) {
		List<RightRela> list = new ArrayList<>();
		for (RightRela rightRela : relas) {
			if (Objects.equals(operId, rightRela.getOperId())) {
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
	 * 
	 * @param appId
	 * @param parentId
	 * @param functionList
	 * @return
	 */
	private List<UrmFunctionModel> getChildFunction(Long appId, Long parentId,
			List<UrmFunctionModel> functionList) {
		List<UrmFunctionModel> result = new ArrayList<>();
		for (int i = 0; i < functionList.size(); i++) {
			UrmFunctionModel f = functionList.get(i);
			if (ObjectUtils.equals(f.getAppId(), appId)
					&& Objects.equals(f.getParentFunctionId(), parentId)) {
				result.add(f);
			}
		}
		return result;
	}

}
