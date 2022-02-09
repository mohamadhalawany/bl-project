package com.bl.service;

import com.bl.dto.CustomersDTO;
import com.bl.dto.OrderDTO;
import com.bl.dto.OrderItemDTO;
import com.bl.dto.OrderStatusDTO;

public interface CustomersService {

	public CustomersDTO findByEmailAndPassword(String email , String password) ;
	public CustomersDTO findById(Long id) ;
	public Long save(CustomersDTO dto) ;
	public Long countByEmail(String email) ;
	public Long saveOrder(OrderDTO order , OrderItemDTO orderItem , OrderStatusDTO orderStatus) ;
}
