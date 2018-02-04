package com.panly.urm.manager.right.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.panly.urm.manager.right.entity.UrmRoleRelaOper;

/**
 * dal Interface:UrmRoleRelaOper
 * @author a@panly.me
 */
public interface UrmRoleRelaOperDao {

	Integer insert(UrmRoleRelaOper record);

    Integer insertSelective(UrmRoleRelaOper record);
    
    Integer delete(UrmRoleRelaOper record);

    Integer deleteByPrimaryKey(@Param("relaId") Long relaId);
    
    Integer updateByPrimaryKey(UrmRoleRelaOper record);

    List<UrmRoleRelaOper> findAll();

    List<UrmRoleRelaOper> find(UrmRoleRelaOper record);

    Integer getCount(UrmRoleRelaOper record);

    UrmRoleRelaOper getByPrimaryKey(@Param("relaId") Long relaId);

	


}