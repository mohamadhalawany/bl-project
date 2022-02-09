package com.bl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bl.DomainValues;
import com.bl.HelperUtils;
import com.bl.dto.OrderDTO;
import com.bl.dto.OrderStatusDTO;
import com.bl.entity.OrderEntity;
import com.bl.entity.OrderStatusEntity;
import com.bl.repository.*;
import com.bl.service.OrderItemService;
import com.bl.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository repo ; 
	
	@Autowired
	private OrderItemService orderItemService ;
	
	@Autowired
	private OrderStatusRepository orderStatusRepository ;
	
	@Override
	public OrderDTO findByCustomerIdAndOrderStatusId(Long customerId, Integer orderStatusId) {
		OrderDTO dto = null ;
		OrderEntity entity = repo.findByCustomerIdAndOrderStatusId(customerId , orderStatusId) ;
		if(entity != null) {
			dto = HelperUtils.convertEntityToDto(entity , OrderDTO.class) ;
		}
		return dto ;
	}


	@Override
	public OrderDTO findById(Long id) {
		OrderDTO dto = null ;
		OrderEntity entity = repo.findById(id).get() ;
		if(entity != null) {
			dto = HelperUtils.convertEntityToDto(entity , OrderDTO.class) ;
		}
		return dto ;
	}
	
	
	
	
	
	@Override
	public List<OrderDTO> myOrders(Long customerId) {
		List<OrderDTO> list = null ;
		List<Integer> statuses = new ArrayList<Integer>() ;
		statuses.add(DomainValues.OrderStatus.ACCEPTED) ;
		statuses.add(DomainValues.OrderStatus.CANCELLED) ;
		statuses.add(DomainValues.OrderStatus.DELIVERED) ;
		statuses.add(DomainValues.OrderStatus.ORDERED) ;
		statuses.add(DomainValues.OrderStatus.REFUESED_BY_CUSTOMER) ;
		statuses.add(DomainValues.OrderStatus.REJECTED_ADMIN) ;

		List<OrderEntity> entityList = repo.findByOrderStatusIdIn(statuses) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<OrderDTO>() ;
			for(OrderEntity entity : entityList) {
				OrderDTO dto = HelperUtils.convertEntityToDto(entity , OrderDTO.class) ;
				dto.setItems(orderItemService.findAllByOrderId(dto.getId())) ;
				list.add(dto) ;				
			}
		}
		return list ;
	}


	@Override
	public List<OrderStatusDTO> orderStatusList(Long orderId , int language) {
		List<OrderStatusDTO> list = null ;
		List<OrderStatusEntity> entityList = orderStatusRepository.findAllByOrderId(orderId) ;
		
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<OrderStatusDTO>() ;
			for(OrderStatusEntity entity : entityList) {
				OrderStatusDTO dto = HelperUtils.convertEntityToDto(entity , OrderStatusDTO.class) ;
				dto.setStatusName(statusNameLanguage(language, dto.getStatusId())) ;
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	
	
	
	
	private String statusNameLanguage(int language , int statusId) {
		String name = null ;
		if(statusId == DomainValues.OrderStatus.ACCEPTED) {
			name = HelperUtils.getValueFromBundle("ACCEPTED" , language) ;
		}else if(statusId == DomainValues.OrderStatus.ADD_TO_CART) {
			name = HelperUtils.getValueFromBundle("ADD_TO_CART" , language) ;
		}else if(statusId == DomainValues.OrderStatus.CANCELLED) {
			name = HelperUtils.getValueFromBundle("CANCELLED" , language) ;
		}else if(statusId == DomainValues.OrderStatus.DELIVERED) {
			name = HelperUtils.getValueFromBundle("DELIVERED" , language) ;
		}else if(statusId == DomainValues.OrderStatus.IN_THE_WAY) {
			name = HelperUtils.getValueFromBundle("IN_THE_WAY" , language) ;
		}else if(statusId == DomainValues.OrderStatus.ORDERED) {
			name = HelperUtils.getValueFromBundle("ORDERED" , language) ;
		}else if(statusId == DomainValues.OrderStatus.REFUESED_BY_CUSTOMER) {
			name = HelperUtils.getValueFromBundle("REFUESED_BY_CUSTOMER" , language) ;
		}else if(statusId == DomainValues.OrderStatus.REJECTED_ADMIN) {
			name = HelperUtils.getValueFromBundle("REJECTED_ADMIN" , language) ;
		}
		return name ;
	}
	

}
