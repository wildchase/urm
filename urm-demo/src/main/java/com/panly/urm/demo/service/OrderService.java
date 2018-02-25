package com.panly.urm.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panly.urm.common.BeanCopyUtil;
import com.panly.urm.demo.dao.TestOrderDao;
import com.panly.urm.demo.entity.TestOrder;
import com.panly.urm.demo.vo.OrderParamVo;
import com.panly.urm.demo.vo.TestOrderVo;
import com.panly.urm.page.core.PageDTO;
import com.panly.urm.page.core.PageDTOUtil;
import com.panly.urm.right.anno.GenOp;

@Service
public class OrderService {

	@Autowired
	private TestOrderDao testOrderDao;
	
	@GenOp("yun-order-select")
	public PageDTO<TestOrderVo> queryPage(OrderParamVo orderParamVo) {
		try {
			PageDTOUtil.startDataTablePage(orderParamVo);
			TestOrder record = BeanCopyUtil.copy(orderParamVo, TestOrder.class);
			List<TestOrder> list = testOrderDao.find(record);
			return PageDTOUtil.transform(list, TestOrderVo.class);
		} finally {
			PageDTOUtil.endPage();
		}
	}

	@GenOp("yun-order-update-amount")
	public void updateOrderAmount(TestOrderVo vo) {
		TestOrder o = new TestOrder();
		o.setOrderId(vo.getOrderId());
		o.setOrderAmount(vo.getOrderAmount());
		testOrderDao.updateByPrimaryKey(o);
	}

	@GenOp("yun-order-del")
	public void deleteOrder(Long orderId) {
		testOrderDao.deleteByPrimaryKey(orderId);
	}
	
	
}
