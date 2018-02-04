package com.panly.urm.manager.right.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.panly.urm.manager.right.entity.UrmAppLog;
import com.panly.urm.manager.right.vo.AppLogParamsVo;

/**
 * dal Interface:UrmAppLog
 * @author a@panly.me
 */
public interface UrmAppLogDao {

	Integer insert(UrmAppLog record);

    Integer insertSelective(UrmAppLog record);
    
    Integer delete(UrmAppLog record);

    Integer deleteByPrimaryKey(@Param("appLogId") Long appLogId);
    
    Integer updateByPrimaryKey(UrmAppLog record);

    List<UrmAppLog> findAll();

    List<UrmAppLog> find(UrmAppLog record);

    Integer getCount(UrmAppLog record);

    UrmAppLog getByPrimaryKey(@Param("appLogId") Long appLogId);

	List<UrmAppLog> queryAppLog(AppLogParamsVo appLogParamsVo);

	


}