package com.panly.urm.manager.right.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.panly.urm.manager.right.entity.UrmRightValueSetConfig;

/**
 * dal Interface:UrmRightValueSetConfig
 * @author gen
 * @date 2018-2-19
 */
public interface UrmRightValueSetConfigDao {

	Integer insert(UrmRightValueSetConfig record);

    Integer insertSelective(UrmRightValueSetConfig record);
    
    Integer delete(UrmRightValueSetConfig record);

    Integer deleteByPrimaryKey(@Param("valueCode") String valueCode);
    
    Integer updateByPrimaryKey(UrmRightValueSetConfig record);

    List<UrmRightValueSetConfig> findAll();

    List<UrmRightValueSetConfig> find(UrmRightValueSetConfig record);

    Integer getCount(UrmRightValueSetConfig record);

    UrmRightValueSetConfig getByPrimaryKey(@Param("valueCode") String valueCode);


}