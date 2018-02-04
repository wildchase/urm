package com.panly.urm.manager.right.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.panly.umr.common.BeanCopyUtil;
import com.panly.urm.manager.common.constants.RecordStatusEnum;
import com.panly.urm.manager.common.constants.StatusEnum;
import com.panly.urm.manager.common.page.core.PageDTO;
import com.panly.urm.manager.common.page.core.PageDTOUtil;
import com.panly.urm.manager.right.dao.UrmAppDao;
import com.panly.urm.manager.right.entity.UrmApp;
import com.panly.urm.manager.right.vo.AppParamsVo;
import com.panly.urm.manager.right.vo.AppVo;
import com.panly.urm.manager.user.UserUtil;
import com.panly.urm.manager.user.service.UserService;

@Service
public class AppService {

	@Autowired
	private UrmAppDao urmAppDao;
	
	@Autowired
	private UserService userService;
	
	@Cacheable(value="app")
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
	
	@CacheEvict(value="app",key="#appVo.getAppId()")
	public int updateApp(AppVo appVo) {
		UrmApp app = BeanCopyUtil.copy(appVo, UrmApp.class);
		app.setUpdateBy(UserUtil.getUserId());
		app.setUpdateTime(new Date());
		return urmAppDao.updateByPrimaryKey(app);
	}

	@CacheEvict(value="app")
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
	
	
}
