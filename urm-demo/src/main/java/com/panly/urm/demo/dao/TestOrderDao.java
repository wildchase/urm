package com.panly.urm.demo.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.panly.urm.demo.entity.TestOrder;

/**
 * dal Interface:TestOrder
 * @author a@panly.me
 */
public interface TestOrderDao {

	Integer insert(TestOrder record);

    Integer insertSelective(TestOrder record);
    
    Integer delete(TestOrder record);

    Integer deleteByPrimaryKey(@Param("orderId") Long orderId);
    
    Integer updateByPrimaryKey(TestOrder record);

    List<TestOrder> findAll();

    List<TestOrder> find(TestOrder record);

    Integer getCount(TestOrder record);

    TestOrder getByPrimaryKey(@Param("orderId") Long orderId);

	


}