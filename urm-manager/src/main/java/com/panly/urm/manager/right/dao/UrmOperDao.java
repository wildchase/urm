package com.panly.urm.manager.right.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.panly.urm.manager.right.entity.UrmOper;
import com.panly.urm.manager.right.vo.OperParamsVo;

/**
 * dal Interface:UrmOper
 * @author a@panly.me
 */
public interface UrmOperDao {

	Integer insert(UrmOper record);

    Integer insertSelective(UrmOper record);
    
    Integer delete(UrmOper record);

    Integer deleteByPrimaryKey(@Param("operId") Long operId);
    
    Integer updateByPrimaryKey(UrmOper record);

    List<UrmOper> findAll();

    List<UrmOper> find(UrmOper record);

    Integer getCount(UrmOper record);

    UrmOper getByPrimaryKey(@Param("operId") Long operId);

	List<UrmOper> query(OperParamsVo operQueryVo);

	


}