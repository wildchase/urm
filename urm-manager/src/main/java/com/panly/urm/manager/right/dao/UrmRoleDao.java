package com.panly.urm.manager.right.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.panly.urm.manager.right.entity.UrmRole;
import com.panly.urm.manager.right.vo.RoleParamsVo;

/**
 * dal Interface:UrmRole
 * @author a@panly.me
 */
public interface UrmRoleDao {

	Integer insert(UrmRole record);

    Integer insertSelective(UrmRole record);
    
    Integer delete(UrmRole record);

    Integer deleteByPrimaryKey(@Param("roleId") Long roleId);
    
    Integer updateByPrimaryKey(UrmRole record);

    List<UrmRole> findAll();

    List<UrmRole> find(UrmRole record);

    Integer getCount(UrmRole record);

    UrmRole getByPrimaryKey(@Param("roleId") Long roleId);

	List<UrmRole> query(RoleParamsVo roleQueryVo);

	


}