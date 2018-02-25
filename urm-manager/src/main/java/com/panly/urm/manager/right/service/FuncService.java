package com.panly.urm.manager.right.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.panly.urm.common.BeanCopyUtil;
import com.panly.urm.manager.common.constants.RecordStatusEnum;
import com.panly.urm.manager.common.constants.StatusEnum;
import com.panly.urm.page.core.PageDTO;
import com.panly.urm.page.core.PageDTOUtil;
import com.panly.urm.manager.common.select.CxSelect;
import com.panly.urm.manager.right.dao.UrmAppDao;
import com.panly.urm.manager.right.dao.UrmFunctionModelDao;
import com.panly.urm.manager.right.entity.UrmApp;
import com.panly.urm.manager.right.entity.UrmFunctionModel;
import com.panly.urm.manager.right.vo.AppVo;
import com.panly.urm.manager.right.vo.FuncParamsVo;
import com.panly.urm.manager.right.vo.FuncVo;
import com.panly.urm.manager.user.UserUtil;
import com.panly.urm.manager.user.service.UserService;

@Service
public class FuncService {

	@Autowired
	private UrmFunctionModelDao urmFunctionModelDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UrmAppDao urmAppDao;
	
	@Autowired
	private AppService appService;
	
	
	@Cacheable(value="func")
	public FuncVo get(Long functionId) {
		UrmFunctionModel func = urmFunctionModelDao.getByPrimaryKey(functionId);
		FuncVo funcVo = BeanCopyUtil.copy(func, FuncVo.class);
		changeVo(funcVo);
		return funcVo;
	}
	
	private void changeVo(FuncVo funcVo){
		if(funcVo==null){
			return ;
		}
		funcVo.setCreateUserName(userService.getUserName(funcVo.getCreateBy()));
		funcVo.setUpdateUserName(userService.getUserName(funcVo.getUpdateBy()));
		AppVo appVo =  appService.get(funcVo.getAppId());
		funcVo.setAppName(appVo!=null?appVo.getAppName():"");
		if(null!=funcVo.getParentFunctionId()){
			FuncVo parentFuncVo = get(funcVo.getParentFunctionId());
			funcVo.setParentFunctionName(parentFuncVo!=null?parentFuncVo.getFunctionName():"");
		}
	}
	

	public PageDTO<FuncVo> queryPage(FuncParamsVo funcQueryVo) {
		try {
			PageDTOUtil.startDataTablePage(funcQueryVo);
			List<UrmFunctionModel> list = urmFunctionModelDao.query(funcQueryVo);
			PageDTO<FuncVo> page = PageDTOUtil.transform(list, FuncVo.class);
			for (FuncVo vo : page.getResultData()) {
				changeVo(vo);
			}
			return page;
		} finally {
			PageDTOUtil.endPage();
		}
	}

	public int add(FuncVo FuncVo) {
		UrmFunctionModel func = BeanCopyUtil.copy(FuncVo, UrmFunctionModel.class);
		func.setStatus(StatusEnum.NORMAL.getCode());
		func.setCreateBy(UserUtil.getUserId());
		func.setCreateTime(new Date());
		return urmFunctionModelDao.insertSelective(func);
	}

	@CacheEvict(value="func",key="#funcVo.getFunctionId()")
	public int update(FuncVo funcVo) {
		UrmFunctionModel func = BeanCopyUtil.copy(funcVo, UrmFunctionModel.class);
		func.setUpdateBy(UserUtil.getUserId());
		func.setUpdateTime(new Date());
		return urmFunctionModelDao.updateByPrimaryKey(func);
	}

	@CacheEvict(value="func")
	public void delete(Long deleteId) {
		UrmFunctionModel record = new UrmFunctionModel();
		record.setFunctionId(deleteId);
		record.setRecordStatus(RecordStatusEnum.DELETED.getCode());
		urmFunctionModelDao.updateByPrimaryKey(record);
	}

	public List<FuncVo> query(FuncParamsVo funcQueryVo) {
		List<UrmFunctionModel> list = urmFunctionModelDao.query(funcQueryVo);
		List<FuncVo> result = BeanCopyUtil.copyList(list, FuncVo.class);
		for (FuncVo vo : result) {
			changeVo(vo);
		}
		return result;
	}

	public List<CxSelect> getAppFuncCxSelect() {
		List<CxSelect> result = new ArrayList<>();
		
		UrmApp record = new UrmApp();
		record.setRecordStatus(RecordStatusEnum.NORMAL.getCode());
		record.setStatus(StatusEnum.NORMAL.getCode());
		List<UrmApp> appList = urmAppDao.find(record);
		
		UrmFunctionModel f = new UrmFunctionModel();
		f.setRecordStatus(RecordStatusEnum.NORMAL.getCode());
		f.setStatus(StatusEnum.NORMAL.getCode());
		List<UrmFunctionModel> functionList= urmFunctionModelDao.find(f);
		
		for (UrmApp urmApp : appList) {
			CxSelect cx = new CxSelect(String.valueOf(urmApp.getAppId()),urmApp.getAppName());
			List<UrmFunctionModel> appChildFuncions = getChildFunction(urmApp.getAppId(),null,functionList);
			if(appChildFuncions.size()>0){
				List<CxSelect> childCxs = new ArrayList<>();
				for (UrmFunctionModel urmFuncModel : appChildFuncions) {
					CxSelect childCx = buildChildCx(urmFuncModel,functionList);
					childCxs.add(childCx);
				}
				cx.setS(childCxs);
			}
			result.add(cx);
		}
		return result;
	}
	
	/**
	 * @param urmFuncModel
	 * @param functionList
	 * @return
	 */
	private CxSelect buildChildCx(UrmFunctionModel urmFuncModel,
			List<UrmFunctionModel> functionList) {
		CxSelect cx = new CxSelect(String.valueOf(urmFuncModel.getFunctionId()),urmFuncModel.getFunctionName());
		List<UrmFunctionModel> appChildFuncions = getChildFunction(urmFuncModel.getAppId(), urmFuncModel.getFunctionId(), functionList);
		if(appChildFuncions.size()>0){
			List<CxSelect> childCxs = new ArrayList<>();
			for (UrmFunctionModel f : appChildFuncions) {
				CxSelect childCx = buildChildCx(f,functionList);
				childCxs.add(childCx);
			}
			cx.setS(childCxs);
		}
		return cx;
	}
	

	/**
	 * 查询
	 * @param appId
	 * @param parentId
	 * @param functionList
	 * @return
	 */
	private List<UrmFunctionModel> getChildFunction(Long appId, Long parentId,List<UrmFunctionModel> functionList) {
		List<UrmFunctionModel> result = new ArrayList<>();
		for (int i = 0; i < functionList.size(); i++) {
			UrmFunctionModel f  = functionList.get(i);
			if(Objects.equals(f.getAppId(), appId) 
					&&Objects.equals(f.getParentFunctionId(), parentId) ){
				result.add(f);
			}
		}
		return result;
	}
	
	
}
