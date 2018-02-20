package com.panly.urm.manager.right.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panly.urm.manager.common.constants.RecordStatusEnum;
import com.panly.urm.manager.right.dao.UrmAcctRelaRoleDao;
import com.panly.urm.manager.right.entity.UrmAcctRelaRole;
import com.panly.urm.manager.user.UserUtil;

@Service
public class AcctRoleRelaService {

	@Autowired
	private UrmAcctRelaRoleDao urmAcctRelaRoleDao;
	
	/**
	 * 添加关联关系
	 * @param acctId
	 * @param roleId
	 */
	public void addRela(Long acctId, Long roleId) {
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
	public void delRela(Long relaId) {
		UrmAcctRelaRole record = new UrmAcctRelaRole();
		record.setRelaId(relaId);
		record.setRecordStatus(RecordStatusEnum.DELETED.getCode());
		urmAcctRelaRoleDao.updateByPrimaryKey(record);
	}
	
}
