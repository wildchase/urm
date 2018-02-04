package com.panly.urm.manager.right.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.panly.urm.manager.right.entity.UrmOperLog;
import com.panly.urm.manager.right.vo.OperLogParamsVo;

/**
 * dal Interface:UrmOperLog
 * @author a@panly.me
 */
public interface UrmOperLogDao {

	Integer insert(UrmOperLog record);

    Integer insertSelective(UrmOperLog record);
    
    Integer delete(UrmOperLog record);

    Integer deleteByPrimaryKey(@Param("operLogId") Long operLogId);
    
    Integer updateByPrimaryKey(UrmOperLog record);

    List<UrmOperLog> findAll();

    List<UrmOperLog> find(UrmOperLog record);

    Integer getCount(UrmOperLog record);

    UrmOperLog getByPrimaryKey(@Param("operLogId") Long operLogId);

	List<UrmOperLog> queryOperLog(OperLogParamsVo operLogParamsVo);


}