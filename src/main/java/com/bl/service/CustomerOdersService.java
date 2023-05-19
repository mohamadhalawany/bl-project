package com.bl.service;

import java.util.List;

import com.bl.dto.OrderRequestDTO;

public interface CustomerOdersService {
	public List<OrderRequestDTO>	 customerOrdersList(Long customerId , Integer orderStatusId) ;
}
