package com.panly.urm.manager.right.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.panly.urm.common.BeanCopyUtil;
import com.panly.urm.manager.common.constants.RecordStatusEnum;
import com.panly.urm.manager.common.constants.StatusEnum;
import com.panly.urm.page.core.PageDTO;
import com.panly.urm.page.core.PageDTOUtil;
import com.panly.urm.manager.right.dao.UrmOperDao;
import com.panly.urm.manager.right.entity.UrmOper;
import com.panly.urm.manager.right.vo.AppVo;
import com.panly.urm.manager.right.vo.FuncVo;
import com.panly.urm.manager.right.vo.OperParamsVo;
import com.panly.urm.manager.right.vo.OperVo;
import com.panly.urm.manager.user.UserUtil;
import com.panly.urm.manager.user.service.UserService;

@Service
public class OperService {

	@Autowired
	private UserService userService;

	@Autowired
	private UrmOperDao urmOperDao;

	@Autowired
	private AppService appService;

	@Autowired
	private FuncService funcService;

	@Cacheable(value="oper")
	public OperVo get(Long operId) {
		UrmOper urmOper = urmOperDao.getByPrimaryKey(operId);
		OperVo vo = BeanCopyUtil.copy(urmOper, OperVo.class);
		changeVo(vo);
		return vo;
	}

	/**
	 * 分页查询
	 * 
	 * @param operQueryVo
	 * @return
	 */
	public PageDTO<OperVo> queryPage(OperParamsVo operQueryVo) {
		try {
			if(operQueryVo.getSecondFunctionId()!=null){
				operQueryVo.setFunctionId(operQueryVo.getSecondFunctionId());
			}else if (operQueryVo.getFirstFunctionId()!=null){
				operQueryVo.setFunctionId(operQueryVo.getFirstFunctionId());
			}
			PageDTOUtil.startDataTablePage(operQueryVo);
			List<UrmOper> list = urmOperDao.query(operQueryVo);
			PageDTO<OperVo> page = PageDTOUtil.transform(list, OperVo.class);
			for (OperVo vo : page.getResultData()) {
				changeVo(vo);
			}
			return page;
		} finally {
			PageDTOUtil.endPage();
		}
	}

	/**
	 * 添加账号
	 * 
	 * @param operParamsVo
	 */
	public int addOper(OperParamsVo operParamsVo) {
		UrmOper oper = BeanCopyUtil.copy(operParamsVo, UrmOper.class);
		oper.setStatus(StatusEnum.NORMAL.getCode());
		oper.setCreateBy(UserUtil.getUserId());
		oper.setCreateTime(new Date());
		
		Long functionId = null;
		if(operParamsVo.getSecondFunctionId()!=null){
			functionId = operParamsVo.getSecondFunctionId();
		}else if (operParamsVo.getFirstFunctionId()!=null){
			functionId = operParamsVo.getFirstFunctionId();
		}
		functionId = operParamsVo.getFunctionId();
		if(functionId==null){
			throw new RuntimeException("未选择功能");
		}
		return urmOperDao.insertSelective(oper);
	}

	/**
	 * 修改账号
	 * 
	 * @param operVo
	 */
	@CacheEvict(value="oper",key="#operVo.getOperId()")
	public int updateOper(OperVo operVo) {
		UrmOper oper = BeanCopyUtil.copy(operVo, UrmOper.class);
		oper.setUpdateBy(UserUtil.getUserId());
		oper.setUpdateTime(new Date());
		return urmOperDao.updateByPrimaryKey(oper);
	}

	@CacheEvict(value="oper")
	public void deleteOper(Long deleteId) {
		UrmOper record = new UrmOper();
		record.setOperId(deleteId);
		record.setRecordStatus(RecordStatusEnum.DELETED.getCode());
		urmOperDao.updateByPrimaryKey(record);
	}

	public List<OperVo> query(OperParamsVo operQueryVo) {
		if(operQueryVo.getSecondFunctionId()!=null){
			operQueryVo.setFunctionId(operQueryVo.getSecondFunctionId());
		}else if (operQueryVo.getFirstFunctionId()!=null){
			operQueryVo.setFunctionId(operQueryVo.getFirstFunctionId());
		}
		List<UrmOper> list = urmOperDao.query(operQueryVo);
		List<OperVo> result = BeanCopyUtil.copyList(list, OperVo.class);
		for (OperVo vo : result) {
			changeVo(vo);
		}
		return result;
	}

	private void changeVo(OperVo vo) {
		if (vo == null) {
			return;
		}
		vo.setCreateUserName(userService.getUserName(vo.getCreateBy()));
		vo.setUpdateUserName(userService.getUserName(vo.getUpdateBy()));

		FuncVo funcVo = funcService.get(vo.getFunctionId());
		if (funcVo != null) {
			vo.setFunctionName(funcVo.getFunctionName());
			AppVo appVo = appService.get(funcVo.getAppId());
			if (appVo != null) {
				vo.setAppName(appVo.getAppName());
				vo.setAppId(appVo.getAppId());
			}
		}
	}

}
