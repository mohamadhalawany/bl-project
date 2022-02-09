package com.bl.service;

import java.util.List;

import com.bl.dto.OrderDTO;
import com.bl.dto.OrderStatusDTO;


public interface OrderService {
	public OrderDTO findByCustomerIdAndOrderStatusId(Long customerId , Integer orderStatusId) ;	
	public OrderDTO findById(Long id) ;
	public List<OrderDTO> myOrders(Long customerId) ;
	public List<OrderStatusDTO> orderStatusList(Long orderId , int language) ;
}
