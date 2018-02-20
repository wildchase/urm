package com.panly.urm.manager.right.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panly.urm.manager.common.constants.RecordStatusEnum;
import com.panly.urm.manager.common.constants.RelaTypeEnum;
import com.panly.urm.manager.right.dao.UrmAcctRelaOperDao;
import com.panly.urm.manager.right.dao.UrmRoleRelaOperDao;
import com.panly.urm.manager.right.entity.UrmAcctRelaOper;
import com.panly.urm.manager.right.entity.UrmRoleRelaOper;
import com.panly.urm.manager.user.UserUtil;

/**
 * 操作关联
 * 
 * @author lipan
 */
@Service
public class OperRelaService {
	
	@Autowired
	private UrmAcctRelaOperDao urmAcctRelaOperDao;

	@Autowired
	private UrmRoleRelaOperDao urmRoleRelaOperDao;

	public void addAcctOperRela(Long acctId, Long operId) {
		UrmAcctRelaOper record = new UrmAcctRelaOper();
		record.setAcctId(acctId);
		record.setOperId(operId);
		record.setRecordStatus(RecordStatusEnum.NORMAL.getCode());
		record.setCreateBy(UserUtil.getUserId());
		record.setCreateTime(new Date());
		urmAcctRelaOperDao.insertSelective(record);
	}

	public void addRoleOperRela(Long roleId, Long operId) {
		UrmRoleRelaOper record = new UrmRoleRelaOper();
		record.setRoleId(roleId);
		record.setOperId(operId);
		record.setRecordStatus(RecordStatusEnum.NORMAL.getCode());
		record.setCreateBy(UserUtil.getUserId());
		record.setCreateTime(new Date());
		urmRoleRelaOperDao.insertSelective(record);
	}

	public void delOperRela(Long relaId, String relaType) {
		RelaTypeEnum rela = RelaTypeEnum.RELATYPE_MAP.get(relaType);
		if(rela==null){
			throw new RuntimeException("不存在的relaType");
		}
		switch (rela) {
		case ACCT_REAL:
			UrmAcctRelaOper acctRela = new UrmAcctRelaOper();
			acctRela.setRelaId(relaId);
			acctRela.setRecordStatus(RecordStatusEnum.DELETED.getCode());
			acctRela.setUpdateBy(UserUtil.getUserId());
			acctRela.setUpdateTime(new Date());
			urmAcctRelaOperDao.updateByPrimaryKey(acctRela);
			break;
		case ROLE_REAL:
			UrmRoleRelaOper roleRela = new UrmRoleRelaOper();
			roleRela.setRelaId(relaId);
			roleRela.setRecordStatus(RecordStatusEnum.DELETED.getCode());
			roleRela.setUpdateBy(UserUtil.getUserId());
			roleRela.setUpdateTime(new Date());
			urmRoleRelaOperDao.updateByPrimaryKey(roleRela);
			break;
		default:
			throw new RuntimeException("不存在的relaType");
		}
	}

}
