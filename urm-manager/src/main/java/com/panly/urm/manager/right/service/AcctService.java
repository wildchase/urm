package com.panly.urm.manager.right.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.panly.umr.common.BeanCopyUtil;
import com.panly.umr.secret.Md5Util;
import com.panly.urm.manager.common.constants.RecordStatusEnum;
import com.panly.urm.manager.common.constants.StatusEnum;
import com.panly.urm.manager.common.page.core.PageDTO;
import com.panly.urm.manager.common.page.core.PageDTOUtil;
import com.panly.urm.manager.right.dao.UrmAcctDao;
import com.panly.urm.manager.right.dao.UrmAcctRelaRoleDao;
import com.panly.urm.manager.right.entity.UrmAcct;
import com.panly.urm.manager.right.entity.UrmAcctRelaRole;
import com.panly.urm.manager.right.vo.AcctParamsVo;
import com.panly.urm.manager.right.vo.AcctRelaRoleVo;
import com.panly.urm.manager.right.vo.AcctVo;
import com.panly.urm.manager.right.vo.RoleVo;
import com.panly.urm.manager.user.UserUtil;
import com.panly.urm.manager.user.service.UserService;

@Service
public class AcctService {

	@Autowired
	private UrmAcctDao urmAcctDao;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UrmAcctRelaRoleDao urmAcctRelaRoleDao;
	
	@Cacheable(value="acct")
	public AcctVo get(Long acctId) {
		return BeanCopyUtil.copy(urmAcctDao.getByPrimaryKey(acctId), AcctVo.class);
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
	@CacheEvict(value="acct",key="#acctVo.acctId")
	public int updateAcct(AcctVo acctVo) {
		UrmAcct acct = BeanCopyUtil.copy(acctVo, UrmAcct.class);
		acct.setUpdateBy(UserUtil.getUserId());
		acct.setUpdateTime(new Date());
		return urmAcctDao.updateByPrimaryKey(acct);
	}
	
	@CacheEvict(value="acct")
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
	
	/**
	 * 添加用户
	 * @param acctId
	 * @param roleIds
	 */
	public void addRole(Long acctId, Long roleId) {
		UrmAcctRelaRole rela = new UrmAcctRelaRole();
		rela.setAcctId(acctId);
		rela.setRoleId(roleId);
		rela.setCreateBy(UserUtil.getUserId());
		rela.setCreateTime(new Date());
		urmAcctRelaRoleDao.insertSelective(rela);
	}

	/**
	 * 删除用户与角色的关联
	*/
	public void delRoleRela(Long relaId) {
		UrmAcctRelaRole record = new UrmAcctRelaRole();
		record.setRelaId(relaId);
		record.setRecordStatus(RecordStatusEnum.DELETED.getCode());
		urmAcctRelaRoleDao.updateByPrimaryKey(record);
	}

}
