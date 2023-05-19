package com.bl.service;

import java.util.List;
import java.util.Map;

import com.bl.dto.OrderItemDTO;

public interface OrderItemService {
	
	public List<OrderItemDTO> findAllByOrderId(Long orderId) ;
	public List<OrderItemDTO> findByOrderIdForDetails(Long orderId) ;
	public List<OrderItemDTO> findByOrderIdForDetailsNext(Long orderId) ;
	public List<OrderItemDTO> findByOrderIdForDetailsPrevious(Long orderId) ;
	
	public Map<String, Object> metaData() ;	
}
