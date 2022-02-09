package com.bl.service;

import java.util.List;

import com.bl.dto.OrderDTO;
import com.bl.dto.OrderItemDTO;
import com.bl.dto.OrderRequestDTO;
import com.bl.dto.OrderStatusDTO;

public interface OrderRequestService {
	
	public List<OrderRequestDTO> customerOrdersCart(Long customerId , Integer orderStatus) ;
	public Long saveOrder(OrderDTO dto) ;
	public Long saveOrderItem(OrderItemDTO dto) ;
	public Long saveOrderStatus(OrderStatusDTO dto) ;
	public void delete(Long id) ;
	public void increaseQuantity(Integer quantity , Long orderItemId , Long orderId , int type) ;
	public void decreaseQuantity(Integer quantity , Long orderItemId , Long orderId , int type) ;
	public OrderDTO findById(Long id) ;
	
}
