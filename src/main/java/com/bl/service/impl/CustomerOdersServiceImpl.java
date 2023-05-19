package com.bl.service.impl;

import java.util.List;

import com.bl.HelperUtils;
import com.bl.dao.OrderRequestDAO;
import com.bl.dao.impl.OrderRequestDAOImpl;
import com.bl.dto.OrderDTO;
import com.bl.dto.OrderRequestDTO;
import com.bl.service.CustomerOdersService;

public class CustomerOdersServiceImpl implements CustomerOdersService {
	
	private OrderRequestDAO dao = new OrderRequestDAOImpl() ;
	
	@Override
	public List<OrderRequestDTO> customerOrdersList(Long customerId , Integer orderStatusId) {
		Long maxOrderId = dao.maxOrderIdByCustomerIdAndOrderStatus(customerId , orderStatusId) ;		
		OrderDTO dto = dao.findById(maxOrderId) ;
		List<OrderRequestDTO> list = null ;
		if(dto != null) {
			Long expire = HelperUtils.dateDifferencesInDays(dto.getOrderDate()) ;
			Integer days = dao.orderExpireDays() ;

			if(expire <= days) {
				list = dao.customerOrdersList(customerId , orderStatusId , maxOrderId) ;
			}else {
				dao.saveOrder(dto) ;
			}
		}else {
			list = null ;
		}		
		return list ;
	}

}
