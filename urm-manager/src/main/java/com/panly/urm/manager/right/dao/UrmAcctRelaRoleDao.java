package com.panly.urm.manager.right.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.panly.urm.manager.right.entity.UrmAcctRelaRole;

/**
 * dal Interface:UrmAcctRelaRole
 * @author a@panly.me
 */
public interface UrmAcctRelaRoleDao {

	Integer insert(UrmAcctRelaRole record);

    Integer insertSelective(UrmAcctRelaRole record);
    
    Integer delete(UrmAcctRelaRole record);

    Integer deleteByPrimaryKey(@Param("relaId") Long relaId);
    
    Integer updateByPrimaryKey(UrmAcctRelaRole record);

    List<UrmAcctRelaRole> findAll();

    List<UrmAcctRelaRole> find(UrmAcctRelaRole record);

    Integer getCount(UrmAcctRelaRole record);

    UrmAcctRelaRole getByPrimaryKey(@Param("relaId") Long relaId);

	


}