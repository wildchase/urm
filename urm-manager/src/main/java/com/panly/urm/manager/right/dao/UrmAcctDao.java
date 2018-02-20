package com.panly.urm.manager.right.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.panly.urm.manager.common.tree.RightRela;
import com.panly.urm.manager.right.entity.UrmAcct;
import com.panly.urm.manager.right.vo.AcctParamsVo;
import com.panly.urm.manager.right.vo.AcctRelaRoleVo;
import com.panly.urm.manager.right.vo.RoleVo;

/**
 * dal Interface:UrmAcct
 * @author a@panly.me
 */
public interface UrmAcctDao {

	Integer insert(UrmAcct record);

    Integer insertSelective(UrmAcct record);
    
    Integer delete(UrmAcct record);

    Integer deleteByPrimaryKey(@Param("acctId") Long acctId);
    
    Integer updateByPrimaryKey(UrmAcct record);

    List<UrmAcct> findAll();

    List<UrmAcct> find(UrmAcct record);

    Integer getCount(UrmAcct record);

    UrmAcct getByPrimaryKey(@Param("acctId") Long acctId);

	List<UrmAcct> query(AcctParamsVo acctQueryVo);

	List<AcctRelaRoleVo> findAcctRoles(AcctParamsVo acctQueryVo);

	List<RoleVo> findAcctNotHaveRoles(AcctParamsVo acctQueryVo);

	List<RightRela> getAcctRightRela(@Param("acctId")Long acctId);


}