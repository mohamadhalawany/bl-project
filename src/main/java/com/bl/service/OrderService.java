package com.bl.service;

import java.util.List;
import java.util.Map;

import com.bl.dto.OrderDTO;
import com.bl.dto.OrderStatusDTO;


public interface OrderService {
	public OrderDTO findByCustomerIdAndOrderStatusId(Long customerId , Integer orderStatusId) ;	
	public OrderDTO findById(Long id) ;
//	public OrderDTO maxOrderNumber() ;
	public List<OrderDTO> myOrders(Long customerId) ;
	public List<OrderStatusDTO> orderStatusList(Long orderId , int language) ;
	
	public List<OrderStatusDTO> orderStatusListHistory(Long orderId , int language) ;
	public List<OrderStatusDTO> next(Long orderId , int language) ;
	public List<OrderStatusDTO> previous(Long orderId , int language) ;
	
	public Map<String, Object> metaData() ;
	public Long save(OrderDTO dto) ;
}
