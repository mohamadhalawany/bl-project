package com.bl.service;

import java.util.List;

import com.bl.dto.OrderItemDTO;

public interface OrderItemService {
	
	public List<OrderItemDTO> findAllByOrderId(Long orderId) ;
}
