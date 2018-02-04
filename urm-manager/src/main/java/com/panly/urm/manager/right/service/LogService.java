package com.panly.urm.manager.right.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panly.urm.manager.common.page.core.PageDTO;
import com.panly.urm.manager.common.page.core.PageDTOUtil;
import com.panly.urm.manager.right.dao.UrmAppLogDao;
import com.panly.urm.manager.right.dao.UrmAuthLogDao;
import com.panly.urm.manager.right.dao.UrmOperLogDao;
import com.panly.urm.manager.right.entity.UrmAppLog;
import com.panly.urm.manager.right.entity.UrmAuthLog;
import com.panly.urm.manager.right.entity.UrmOperLog;
import com.panly.urm.manager.right.vo.AppLogParamsVo;
import com.panly.urm.manager.right.vo.AppLogVo;
import com.panly.urm.manager.right.vo.AuthLogParamsVo;
import com.panly.urm.manager.right.vo.AuthLogVo;
import com.panly.urm.manager.right.vo.OperLogParamsVo;
import com.panly.urm.manager.right.vo.OperLogVo;

@Service
public class LogService {
	
	@Autowired
	private UrmOperLogDao urmOperLogDao;
	
	@Autowired
	private UrmAuthLogDao urmAuthLogDao;
	
	@Autowired
	private UrmAppLogDao urmAppLogDao;

	public PageDTO<OperLogVo> queryOperLog(OperLogParamsVo operLogParamsVo) {
		try {
			PageDTOUtil.startDataTablePage(operLogParamsVo);
			List<UrmOperLog> list = urmOperLogDao.queryOperLog(operLogParamsVo);
			PageDTO<OperLogVo> page = PageDTOUtil.transform(list,OperLogVo.class);
			return page;
		} finally {
			PageDTOUtil.endPage();
		}
	}

	public PageDTO<AuthLogVo> queryAuthLog(AuthLogParamsVo authLogParamsVo) {
		try {
			PageDTOUtil.startDataTablePage(authLogParamsVo);
			List<UrmAuthLog> list = urmAuthLogDao.queryAuthLog(authLogParamsVo);
			PageDTO<AuthLogVo> page = PageDTOUtil.transform(list,AuthLogVo.class);
			return page;
		} finally {
			PageDTOUtil.endPage();
		}
	}

	public PageDTO<AppLogVo> queryAppLog(AppLogParamsVo appLogParamsVo) {
		try {
			PageDTOUtil.startDataTablePage(appLogParamsVo);
			List<UrmAppLog> list = urmAppLogDao.queryAppLog(appLogParamsVo);
			PageDTO<AppLogVo> page = PageDTOUtil.transform(list,AppLogVo.class);
			return page;
		} finally {
			PageDTOUtil.endPage();
		}
	}

}
