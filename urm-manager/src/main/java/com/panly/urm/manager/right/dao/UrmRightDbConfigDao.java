package com.panly.urm.manager.right.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.panly.urm.manager.right.entity.UrmRightDbConfig;

/**
 * dal Interface:UrmRightDbConfig
 * @author gen
 * @date 2018-2-19
 */
public interface UrmRightDbConfigDao {

	Integer insert(UrmRightDbConfig record);

    Integer insertSelective(UrmRightDbConfig record);
    
    Integer delete(UrmRightDbConfig record);

    Integer deleteByPrimaryKey(@Param("dbCode") String dbCode);
    
    Integer updateByPrimaryKey(UrmRightDbConfig record);

    List<UrmRightDbConfig> findAll();

    List<UrmRightDbConfig> find(UrmRightDbConfig record);

    Integer getCount(UrmRightDbConfig record);

    UrmRightDbConfig getByPrimaryKey(@Param("dbCode") String dbCode);


}