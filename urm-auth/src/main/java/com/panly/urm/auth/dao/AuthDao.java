package com.panly.urm.auth.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.panly.urm.auth.model.AcctEntity;
import com.panly.urm.auth.model.AcctRight;
import com.panly.urm.auth.model.AppEntity;
import com.panly.urm.auth.model.FuncEntity;
import com.panly.urm.auth.model.OperEntity;

public interface AuthDao {
	
	public AppEntity getApp(String appCode);
	
	public OperEntity getOper(String operCode);
	
	public AcctEntity getAcct(Long acctId);
	
	public int getAcctOperCount(@Param("acctId")Long acctId,@Param("operId")Long operId);
	
	public List<AcctRight> getAcctOperRight(@Param("acctId")Long acctId,@Param("operId")Long operId);
	
	public List<FuncEntity> getAppFuncList(Long appId);
	
	public List<FuncEntity> getAcctAppFuncList(@Param("acctId")Long acctId,@Param("appId")Long appId);
	
}
