package com.panly.urm.manager.right.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.panly.umr.common.BeanCopyUtil;
import com.panly.urm.manager.common.constants.RecordStatusEnum;
import com.panly.urm.manager.common.page.core.PageDTO;
import com.panly.urm.manager.common.page.core.PageDTOUtil;
import com.panly.urm.manager.right.dao.UrmRoleDao;
import com.panly.urm.manager.right.entity.UrmRole;
import com.panly.urm.manager.right.vo.RoleParamsVo;
import com.panly.urm.manager.right.vo.RoleVo;
import com.panly.urm.manager.user.UserUtil;
import com.panly.urm.manager.user.service.UserService;

@Service
public class RoleService {
	
	@Autowired
	private UrmRoleDao urmRoleDao;
	
	@Autowired
	private UserService userService;
	
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
	
}
