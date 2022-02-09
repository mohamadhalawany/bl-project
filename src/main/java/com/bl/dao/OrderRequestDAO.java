package com.bl.dao;

import java.util.List;

import com.bl.dto.GeneralDTO;
import com.bl.dto.OrderRequestDTO;

public interface OrderRequestDAO {
	
	public List<OrderRequestDTO> customerOrdersCart(Long customerId , Integer orderStatus) ;
	public GeneralDTO generateOrderNumber() ;
}
