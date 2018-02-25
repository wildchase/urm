package com.panly.urm.manager.right.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panly.urm.common.BeanCopyUtil;
import com.panly.urm.manager.common.constants.RecordStatusEnum;
import com.panly.urm.manager.common.tree.RightRela;
import com.panly.urm.manager.right.config.DbConfig;
import com.panly.urm.manager.right.dao.UrmAcctDao;
import com.panly.urm.manager.right.dao.UrmDataRightDao;
import com.panly.urm.manager.right.dao.UrmRoleDao;
import com.panly.urm.manager.right.entity.UrmDataRight;
import com.panly.urm.manager.right.entity.UrmRightValueSetConfig;
import com.panly.urm.manager.right.vo.DataRightExecResult;
import com.panly.urm.manager.right.vo.DataRightParam;
import com.panly.urm.manager.right.vo.DataRightVo;
import com.panly.urm.manager.right.vo.RightValueSetConfigSelect2;
import com.panly.urm.manager.user.UserUtil;

@Service
public class RightDataService {
	
	private final static Logger logger =LoggerFactory.getLogger(RightDataService.class);

	@Autowired
	private UrmAcctDao urmAcctDao;
	
	@Autowired
	private UrmRoleDao urmRoleDao;

	@Autowired
	private UrmDataRightDao urmDataRightDao;
	
	@Autowired
	private DbConfig dbConfig;
	
	/**
	 * 获取 该acct下所有的数据权限
	 */
	public List<RightRela> getAcctRightRela(Long acctId) {
		// 获取该用户所有的操作关联
		List<RightRela> relas = urmAcctDao.getAcctRightRela(acctId);
		if (relas != null && relas.size() > 0) {
			// 获取该用户所有的 权限数据
			List<UrmDataRight> urmDataRights = urmDataRightDao.getAcctRightData(acctId);
			for (int i = 0; i < relas.size(); i++) {
				RightRela rela = relas.get(i);
				List<DataRightVo> rights = getRights(rela, urmDataRights);
				for (DataRightVo dataRightVo : rights) {
					dataRightVo.setValueConfigName(dbConfig.getValueConfigName(dataRightVo.getValueCode(), dataRightVo.getValueConfig()));
				}
				rela.setRights(rights);
			}
		}
		return relas;
	}

	private List<DataRightVo> getRights(RightRela rela,
			List<UrmDataRight> urmDataRights) {
		List<DataRightVo> results = new ArrayList<>();
		for (UrmDataRight urmDataRight : urmDataRights) {
			if (Objects.equals(urmDataRight.getRelaId(), rela.getRelaId())
					&& Objects.equals(urmDataRight.getRelaType(),Integer.parseInt(rela.getRelaType()))) {
				results.add(BeanCopyUtil.copy(urmDataRight, DataRightVo.class));
			}
		}
		return results;
	}
	
	
	
	/**
	 * 获取 该acct下所有的数据权限
	 */
	public List<RightRela> getRoleRightRela(Long roleId) {
		// 获取该用户所有的操作关联
		List<RightRela> relas = urmRoleDao.getRoleRightRela(roleId);
		if (relas != null && relas.size() > 0) {
			// 获取该用户所有的 权限数据
			List<UrmDataRight> urmDataRights = urmDataRightDao.getRoleRightData(roleId);
			for (int i = 0; i < relas.size(); i++) {
				RightRela rela = relas.get(i);
				List<DataRightVo> rights = getRights(rela, urmDataRights);
				for (DataRightVo dataRightVo : rights) {
					dataRightVo.setValueConfigName(dbConfig.getValueConfigName(dataRightVo.getValueCode(), dataRightVo.getValueConfig()));
				}
				rela.setRights(rights);
			}
		}
		return relas;
	}

	
	/**
	 * 执行 并验证权限 sql语句
	 * @param dataRightParam
	 * @return
	 */
	public DataRightExecResult execSql(DataRightParam dataRightParam) {
		String sql = dataRightParam.getDataRightSql();
		Connection conn = dbConfig.getConnection(dataRightParam.getDbCode());
		try {
			dbConfig.checkSelectSql(sql, conn);
			DataRightExecResult result = dbConfig.exec(conn, sql);
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally{
			dbConfig.close(conn);
		}
	}

	/**
	 * 添加数据权限
	 * @param dataRightParam
	 */
	public void addDbRight(DataRightParam dataRightParam) {
		DataRightExecResult result = execSql(dataRightParam);
		logger.info("可访问的数据行数{}",result.getCount());
		
		UrmDataRight dataRight = BeanCopyUtil.copy(dataRightParam, UrmDataRight.class);
		dataRight.setCreateBy(UserUtil.getUserId());
		dataRight.setCreateTime(new Date());
		String dbName = dbConfig.getRightDbConfigName(dataRightParam.getDbCode());
		dataRight.setDbName(dbName);
		urmDataRightDao.insertSelective(dataRight);
	}
	
	
	/**
	 * 修改 db right 权限
	 * @param dataRightParam
	 */
	public void updateDbRight(DataRightParam dataRightParam) {
		DataRightExecResult result = execSql(dataRightParam);
		logger.info("可访问的数据行数{}",result.getCount());
		
		UrmDataRight record = new UrmDataRight();
		record.setRightId(dataRightParam.getRightId());
		record.setDbCode(dataRightParam.getDbCode());
		record.setDataRightSql(dataRightParam.getDataRightSql());
		record.setUpdateBy(UserUtil.getUserId());
		record.setUpdateTime(new Date());
		String dbName = dbConfig.getRightDbConfigName(dataRightParam.getDbCode());
		record.setDbName(dbName);
		urmDataRightDao.updateByPrimaryKey(record);
	}
	
	
	/**
	 * 删除数据权限 
	 * @param dataRightParam
	 */
	public void del(DataRightParam dataRightParam) {
		UrmDataRight record = new UrmDataRight();
		record.setRightId(dataRightParam.getRightId());
		record.setRecordStatus(RecordStatusEnum.DELETED.getCode());
		record.setUpdateBy(UserUtil.getUserId());
		record.setUpdateTime(new Date());
		urmDataRightDao.updateByPrimaryKey(record);
	}
	
	
	public List<RightValueSetConfigSelect2> getRightValueSetConfigSelect2s(){
		List<UrmRightValueSetConfig> list = dbConfig.getRightValueSetConfigs();
		List<RightValueSetConfigSelect2> results = BeanCopyUtil.copyList(list, RightValueSetConfigSelect2.class);
		for (RightValueSetConfigSelect2 select2 : results) {
			select2.setId(select2.getValueCode());
			select2.setText(select2.getValueName());
		}
		return results;
	}

	public DataRightExecResult execValue(DataRightParam dataRightParam) {
		//
		UrmRightValueSetConfig valueConfig = dbConfig.getValueSetConfig(dataRightParam.getValueCode());
		String dbCode = valueConfig.getDbCode();
		String sql = dbConfig.tranValueConfigToSql(dataRightParam.getValueCode(),dataRightParam.getValueConfig());
		dataRightParam.setDbCode(dbCode);
		dataRightParam.setDataRightSql(sql);
		return execSql(dataRightParam);
	}

	public void addValueRight(DataRightParam dataRightParam) {
		DataRightExecResult result = execValue(dataRightParam);
		logger.info("可访问的数据行数{}",result.getCount());
		
		UrmDataRight dataRight = BeanCopyUtil.copy(dataRightParam, UrmDataRight.class);
		dataRight.setCreateBy(UserUtil.getUserId());
		dataRight.setCreateTime(new Date());
		String dbName = dbConfig.getRightDbConfigName(dataRightParam.getDbCode());
		dataRight.setDbName(dbName);
		
		String valueName = dbConfig.getValueSetConfigName(dataRightParam.getValueCode());
		dataRight.setValueName(valueName);
		
		urmDataRightDao.insertSelective(dataRight);
		
	}

	
	public void updateValueRight(DataRightParam dataRightParam) {
		DataRightExecResult result = execValue(dataRightParam);
		logger.info("可访问的数据行数{}",result.getCount());
		
		UrmDataRight record = BeanCopyUtil.copy(dataRightParam, UrmDataRight.class);
		record.setUpdateBy(UserUtil.getUserId());
		record.setUpdateTime(new Date());
		String dbName = dbConfig.getRightDbConfigName(dataRightParam.getDbCode());
		record.setDbName(dbName);
		
		String valueName = dbConfig.getValueSetConfigName(dataRightParam.getValueCode());
		record.setValueName(valueName);
		urmDataRightDao.updateByPrimaryKey(record);
	}
	
	
	
}
