package com.panly.urm.auth.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panly.urm.auth.dao.AuthDao;
import com.panly.urm.auth.model.AcctEntity;
import com.panly.urm.auth.model.AcctRight;
import com.panly.urm.auth.model.AppEntity;
import com.panly.urm.auth.model.FuncEntity;
import com.panly.urm.auth.model.OperEntity;
import com.panly.urm.common.BeanCopyUtil;
import com.panly.urm.tran.auth.AppDTO;
import com.panly.urm.tran.auth.AuthReqDTO;
import com.panly.urm.tran.auth.AuthRespDTO;
import com.panly.urm.tran.auth.TreeDTO;

@Service
public class AuthService {
	
	@Autowired
	private AuthDao authDao;
	
	@Autowired
	private CacheService cacheService;
	
	public AuthRespDTO getAuthRight(AuthReqDTO authReqDTO) {
		AcctEntity acct = cacheService.getAcct(authReqDTO.getAcctId());
		if(acct==null){
			throw new RuntimeException("账户不存在");
		}
		if(acct.getStatus()!=1){
			throw new RuntimeException("账户被禁用");
		}
		
		OperEntity oper = cacheService.getOper(authReqDTO.getOperCode());
		if(oper==null){
			throw new RuntimeException("操作不存在");
		}
		if(oper.getStatus()!=1){
			throw new RuntimeException("操作被禁用");
		}
		
		//先查询，acct 是否有该操作的关联权限
		int count = authDao.getAcctOperCount(authReqDTO.getAcctId(), oper.getOperId());
		if(count==0){
			throw new RuntimeException("没有权限");
		}
		
		//再 查询 acct下的数据权限
		List<AcctRight> list = authDao.getAcctOperRight(authReqDTO.getAcctId(), oper.getOperId());
		
		AuthRespDTO result = new AuthRespDTO();
		result.setOperCode(oper.getOperCode());
		result.setOperName(oper.getOperName());
		result.setOperId(oper.getOperId());
		
		List<String> rights = new ArrayList<>();
		for (AcctRight acctRight : list) {
			rights.add(acctRight.getDataRightSql());
		}
		result.setRightSqls(rights);
		return result;
	}
	
	
	public AppDTO getApp(String appCode){
		AppEntity app = cacheService.getApp(appCode);
		if(app==null){
			throw new RuntimeException("应用不存在");
		}
		if(app.getStatus()!=1){
			throw new RuntimeException("应用被禁用");
		}
		return BeanCopyUtil.copy(app, AppDTO.class);
	}

	/**
	 * 获取应用的 功能结构树
	 * @param appCode
	 * @return
	 */
	public List<TreeDTO> getAppFuncTree(String appCode){
		AppDTO app = getApp(appCode);
		Long appId = app.getAppId();
		List<FuncEntity> appFuncList = authDao.getAppFuncList(appId);
		List<TreeDTO> result = buildFuncTreeNode(null, appFuncList);
		return result;
	}
	
	private List<TreeDTO> buildFuncTreeNode(Long parentId,List<FuncEntity> appFuncList) {
		List<TreeDTO> result = new ArrayList<>();
		List<FuncEntity> firstFunc = getChildFunc(parentId, appFuncList);
		for (FuncEntity funcEntity : firstFunc) {
			TreeDTO treeVo = new TreeDTO();
			treeVo.setId(String.valueOf(funcEntity.getFunctionId()));
			treeVo.setCode(funcEntity.getFunctionCode());
			treeVo.setText(funcEntity.getFunctionName());
			treeVo.setType("2");
			List<TreeDTO> nodes = buildFuncTreeNode(funcEntity.getFunctionId(), appFuncList);
			if(nodes!=null&&nodes.size()>0){
				treeVo.setNodes(nodes);	
			}
			result.add(treeVo);
		}
		return result;
	}
	
	private List<FuncEntity> getChildFunc(Long parentId,List<FuncEntity> appFuncList) {
		List<FuncEntity> result = new ArrayList<>();
		for (int i = 0; i < appFuncList.size(); i++) {
			FuncEntity f = appFuncList.get(i);
			if (Objects.equals(f.getParentFunctionId(), parentId)) {
				result.add(f);
			}
		}
		return result;
	}
	
	/**
	 * acct在该应用下功能结构树
	 * @param appCode
	 * @param acctId
	 * @return
	 */
	public List<TreeDTO> getAcctFuncTreeForApp(String appCode, Long acctId) {
		AppDTO app = getApp(appCode);
		Long appId = app.getAppId();
		
		List<FuncEntity> appFuncList = authDao.getAppFuncList(appId);
		List<FuncEntity> acctAppFuncList= authDao.getAcctAppFuncList(acctId, appId);

		List<FuncEntity> acctAllFuncList = tran(appFuncList,acctAppFuncList);
		List<TreeDTO> result = buildFuncTreeNode(null, acctAllFuncList);
		return result;
	}


	private List<FuncEntity> tran(List<FuncEntity> appFuncList, List<FuncEntity> acctAppFuncList) {
		Set<Long> acctHaveFunSet = new HashSet<>();
		for (int i = 0; i < acctAppFuncList.size(); i++) {
			acctHaveFunSet.add(acctAppFuncList.get(i).getFunctionId());
		}
		List<FuncEntity> acctAllHaveFuncList = new ArrayList<>();
		for (int i = 0; i < appFuncList.size(); i++) {
			FuncEntity f =  appFuncList.get(i);
			if(checkAcctHave(f,acctHaveFunSet,appFuncList)){
				acctAllHaveFuncList.add(f);
			}
		}
		return acctAllHaveFuncList;
	}


	private boolean checkAcctHave(FuncEntity f, Set<Long> acctHaveFunSet, List<FuncEntity> appFuncList) {
		Long funcId = f.getFunctionId();
		if(acctHaveFunSet.contains(funcId)){
			return true;
		}
		//查询 funcId 下级有，也行
		List<FuncEntity> childs = getChildFunc(f.getFunctionId(), appFuncList);
		for (FuncEntity funcEntity : childs) {
			return checkAcctHave(funcEntity, acctHaveFunSet, appFuncList);
		}
		return false;
	}
	

}
