package com.panly.urm.manager.right.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.panly.urm.manager.right.entity.UrmFunctionModel;
import com.panly.urm.manager.right.vo.FuncParamsVo;

/**
 * dal Interface:UrmFunctionModel
 * @author a@panly.me
 */
public interface UrmFunctionModelDao {

	Integer insert(UrmFunctionModel record);

    Integer insertSelective(UrmFunctionModel record);
    
    Integer delete(UrmFunctionModel record);

    Integer deleteByPrimaryKey(@Param("functionId") Long funtionId);
    
    Integer updateByPrimaryKey(UrmFunctionModel record);

    List<UrmFunctionModel> findAll();

    List<UrmFunctionModel> find(UrmFunctionModel record);

    Integer getCount(UrmFunctionModel record);

    UrmFunctionModel getByPrimaryKey(@Param("functionId") Long funtionId);

	List<UrmFunctionModel> query(FuncParamsVo funcQueryVo);

	


}