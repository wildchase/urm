package com.panly.urm.manager.right.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.panly.urm.manager.right.entity.UrmAuthLog;
import com.panly.urm.manager.right.vo.AuthLogParamsVo;

/**
 * dal Interface:UrmAuthLog
 * @author a@panly.me
 */
public interface UrmAuthLogDao {

	Integer insert(UrmAuthLog record);

    Integer insertSelective(UrmAuthLog record);
    
    Integer delete(UrmAuthLog record);

    Integer deleteByPrimaryKey(@Param("authLogId") Long authLogId);
    
    Integer updateByPrimaryKey(UrmAuthLog record);

    List<UrmAuthLog> findAll();

    List<UrmAuthLog> find(UrmAuthLog record);

    Integer getCount(UrmAuthLog record);

    UrmAuthLog getByPrimaryKey(@Param("authLogId") Long authLogId);

	List<UrmAuthLog> queryAuthLog(AuthLogParamsVo authLogParamsVo);

	


}