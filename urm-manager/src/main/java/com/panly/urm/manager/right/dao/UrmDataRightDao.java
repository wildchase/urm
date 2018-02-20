package com.panly.urm.manager.right.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.panly.urm.manager.right.entity.UrmDataRight;


/**
 * dal Interface:UrmDataRight
 * @author gen
 * @date 2018-2-19
 */
public interface UrmDataRightDao {

	Integer insert(UrmDataRight record);

    Integer insertSelective(UrmDataRight record);
    
    Integer delete(UrmDataRight record);

    Integer deleteByPrimaryKey(@Param("rightId") Long rightId);
    
    Integer updateByPrimaryKey(UrmDataRight record);

    List<UrmDataRight> findAll();

    List<UrmDataRight> find(UrmDataRight record);

    Integer getCount(UrmDataRight record);

    UrmDataRight getByPrimaryKey(@Param("rightId") Long rightId);

	List<UrmDataRight> getAcctRightData(@Param("acctId")Long acctId);
	
	/** 获取 角色权限 */
	List<UrmDataRight> getRoleRightData(@Param("roleId")Long roleId);


}