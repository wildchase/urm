package com.panly.urm.manager.right.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.panly.urm.manager.right.entity.UrmDataRight;

/**
 * dal Interface:UrmDataRight
 * @author a@panly.me
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

	


}