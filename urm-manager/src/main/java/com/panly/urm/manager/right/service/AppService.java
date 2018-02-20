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
import com.panly.urm.manager.common.tree.TreeNode;
import com.panly.urm.manager.right.dao.UrmAppDao;
import com.panly.urm.manager.right.dao.UrmFunctionModelDao;
import com.panly.urm.manager.right.dao.UrmOperDao;
import com.panly.urm.manager.right.entity.UrmApp;
import com.panly.urm.manager.right.entity.UrmFunctionModel;
import com.panly.urm.manager.right.entity.UrmOper;
import com.panly.urm.manager.right.vo.AppParamsVo;
import com.panly.urm.manager.right.vo.AppVo;
import com.panly.urm.manager.right.vo.OperParamsVo;
import com.panly.urm.manager.user.UserUtil;
import com.panly.urm.manager.user.service.UserService;

@Service
public class AppService {

	@Autowired
	private UrmAppDao urmAppDao;

	@Autowired
	private UserService userService;

	@Autowired
	private UrmFunctionModelDao urmFunctionModelDao;

	@Autowired
	private UrmOperDao urmOperDao;

	@Cacheable(value = "app")
	public AppVo get(Long appId) {
		UrmApp app = urmAppDao.getByPrimaryKey(appId);
		return BeanCopyUtil.copy(app, AppVo.class);
	}

	public PageDTO<AppVo> queryPage(AppParamsVo appQueryVo) {
		try {
			PageDTOUtil.startDataTablePage(appQueryVo);
			List<UrmApp> list = urmAppDao.query(appQueryVo);
			PageDTO<AppVo> page = PageDTOUtil.transform(list, AppVo.class);
			for (AppVo vo : page.getResultData()) {
				vo.setCreateUserName(userService.getUserName(vo.getCreateBy()));
				vo.setUpdateUserName(userService.getUserName(vo.getUpdateBy()));
			}
			return page;
		} finally {
			PageDTOUtil.endPage();
		}
	}

	public int addApp(AppVo appVo) {
		UrmApp app = BeanCopyUtil.copy(appVo, UrmApp.class);
		app.setStatus(StatusEnum.NORMAL.getCode());
		app.setCreateBy(UserUtil.getUserId());
		app.setCreateTime(new Date());
		return urmAppDao.insertSelective(app);
	}

	@CacheEvict(value = "app", key = "#appVo.getAppId()")
	public int updateApp(AppVo appVo) {
		UrmApp app = BeanCopyUtil.copy(appVo, UrmApp.class);
		app.setUpdateBy(UserUtil.getUserId());
		app.setUpdateTime(new Date());
		return urmAppDao.updateByPrimaryKey(app);
	}

	@CacheEvict(value = "app")
	public void deleteApp(Long deleteId) {
		UrmApp record = new UrmApp();
		record.setAppId(deleteId);
		record.setRecordStatus(RecordStatusEnum.DELETED.getCode());
		urmAppDao.updateByPrimaryKey(record);
	}

	public List<AppVo> query(AppParamsVo appQueryVo) {
		List<UrmApp> list = urmAppDao.query(appQueryVo);
		List<AppVo> result = BeanCopyUtil.copyList(list, AppVo.class);
		for (AppVo vo : result) {
			vo.setCreateUserName(userService.getUserName(vo.getCreateBy()));
			vo.setUpdateUserName(userService.getUserName(vo.getUpdateBy()));
		}
		return result;
	}

	/**
	 *  app下的  func 和oper Tree
	 * @param func
	 * @param funcList
	 * @param operList
	 * @return
	 */
	public TreeNode getFuncOperTreeNodeByAppId(Long appId) {
		if(appId==null){
			throw new RuntimeException("appId不能为空");
		}
		
		AppVo appVo = get(appId);
		
		
		UrmFunctionModel funcRecord = new UrmFunctionModel();
		funcRecord.setAppId(appId);
		funcRecord.setRecordStatus(RecordStatusEnum.NORMAL.getCode());
		List<UrmFunctionModel> funcList = urmFunctionModelDao.find(funcRecord);

		// 获取appId 下的所有操作
		OperParamsVo paramsVo = new OperParamsVo();
		paramsVo.setAppId(appId);
		List<UrmOper> operList = urmOperDao.query(paramsVo);
		
		TreeNode appNode = new TreeNode(appVo.getAppId().toString(), appVo.getAppCode(),
				appVo.getAppName(), TreeNode.TYPE_APP);
		
		List<TreeNode> childNodes = new ArrayList<>();

		List<UrmFunctionModel> firstLevelFuncions = getChildFunction(null, funcList);
		if (firstLevelFuncions.size() > 0) {
			for (UrmFunctionModel func : firstLevelFuncions) {
				TreeNode node = buildFuncTreeNode(func, funcList, operList);
				childNodes.add(node);
			}
			appNode.setNodes(childNodes);
		}
		return appNode;
	}

	private TreeNode buildFuncTreeNode(UrmFunctionModel func, List<UrmFunctionModel> funcList, List<UrmOper> operList) {
		TreeNode treeNode = new TreeNode(func.getFunctionId().toString(), func.getFunctionCode(),
				func.getFunctionName(), TreeNode.TYPE_FUNC);
		List<TreeNode> nodes = new ArrayList<>();
		
		//获取functionNode
		List<UrmFunctionModel> childFuncions = getChildFunction(func.getFunctionId(), funcList);
		if (childFuncions.size() > 0) {
			for (UrmFunctionModel child : childFuncions) {
				TreeNode childTree = buildFuncTreeNode(child, funcList, operList);
				nodes.add(childTree);
			}
		}

		// 获取 operNode
		List<UrmOper> childOpers = getChildOper(func.getFunctionId(), operList);
		if (childOpers != null && childOpers.size() > 0) {
			for (UrmOper urmOper : childOpers) {
				TreeNode operNode = new TreeNode(urmOper.getOperId().toString(), urmOper.getOperCode(),
						urmOper.getOperName(), TreeNode.TYPE_OPER);
				nodes.add(operNode);
			}
		}
		if(nodes.size()>0){
			treeNode.setNodes(nodes);
		}
		return treeNode;
	}

	private List<UrmOper> getChildOper(Long functionId, List<UrmOper> operList) {
		List<UrmOper> list  = new ArrayList<>();
		for (UrmOper urmOper : operList) {
			if(ObjectUtils.equals(urmOper.getFunctionId(), functionId)){
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
	private List<UrmFunctionModel> getChildFunction(Long parentId, List<UrmFunctionModel> functionList) {
		List<UrmFunctionModel> result = new ArrayList<>();
		for (int i = 0; i < functionList.size(); i++) {
			UrmFunctionModel f = functionList.get(i);
			if (Objects.equals(f.getParentFunctionId(), parentId)) {
				result.add(f);
			}
		}
		return result;
	}

}
