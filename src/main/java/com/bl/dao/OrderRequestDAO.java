package com.bl.dao;

import java.util.List;

import com.bl.dto.GeneralDTO;
import com.bl.dto.OrderDTO;
import com.bl.dto.OrderRequestDTO;

public interface OrderRequestDAO {
	
	public List<OrderRequestDTO> customerOrdersCart(Long customerId , Integer orderStatus , Long orderId) ;
	public List<OrderRequestDTO> findAllOrdersOrdered() ;
	public List<OrderRequestDTO> search(OrderRequestDTO dto) ;
	public List<OrderRequestDTO> customerOrdersList(Long customerId , Integer orderStatusId , Long orderId) ;
	
	public OrderRequestDTO findOrderRequestById(Long id) ;
	public OrderDTO findById(Long id) ;
	public GeneralDTO generateOrderNumber() ;
	
	public Long maxOrderIdByCustomerIdAndOrderStatus(Long customerId , Integer orderStatus) ;	
	public Integer orderExpireDays() ;
	public void saveOrder(OrderDTO dto) ;
}
