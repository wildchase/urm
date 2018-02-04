package com.panly.urm.manager.right.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.panly.urm.manager.right.entity.UrmApp;
import com.panly.urm.manager.right.vo.AppParamsVo;

/**
 * dal Interface:UrmApp
 * @author a@panly.me
 */
public interface UrmAppDao {

	Integer insert(UrmApp record);

    Integer insertSelective(UrmApp record);
    
    Integer delete(UrmApp record);

    Integer deleteByPrimaryKey(@Param("appId") Long appId);
    
    Integer updateByPrimaryKey(UrmApp record);

    List<UrmApp> findAll();

    List<UrmApp> find(UrmApp record);

    Integer getCount(UrmApp record);

    UrmApp getByPrimaryKey(@Param("appId") Long appId);

	List<UrmApp> query(AppParamsVo appQueryVo);

	


}