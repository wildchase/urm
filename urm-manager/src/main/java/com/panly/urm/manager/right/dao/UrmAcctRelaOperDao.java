package com.panly.urm.manager.right.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.panly.urm.manager.right.entity.UrmAcctRelaOper;

/**
 * dal Interface:UrmAcctRelaOper
 * @author a@panly.me
 */
public interface UrmAcctRelaOperDao {

	Integer insert(UrmAcctRelaOper record);

    Integer insertSelective(UrmAcctRelaOper record);
    
    Integer delete(UrmAcctRelaOper record);

    Integer deleteByPrimaryKey(@Param("relaId") Long relaId);
    
    Integer updateByPrimaryKey(UrmAcctRelaOper record);

    List<UrmAcctRelaOper> findAll();

    List<UrmAcctRelaOper> find(UrmAcctRelaOper record);

    Integer getCount(UrmAcctRelaOper record);

    UrmAcctRelaOper getByPrimaryKey(@Param("relaId") Long relaId);

	


}