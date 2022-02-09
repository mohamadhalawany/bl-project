package com.bl.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dao.OrderRequestDAO;
import com.bl.dto.GeneralDTO;
import com.bl.dto.OrderDTO;
import com.bl.dto.OrderItemDTO;
import com.bl.dto.OrderRequestDTO;
import com.bl.dto.OrderStatusDTO;
import com.bl.entity.OrderEntity;
import com.bl.entity.OrderItemEntity;
import com.bl.entity.OrderStatusEntity;
import com.bl.repository.OrderItemRepository;
import com.bl.repository.OrderRepository;
import com.bl.repository.OrderStatusRepository;
import com.bl.service.OrderRequestService;

@Service
public class OrderRequestServiceImpl implements OrderRequestService {

	@Autowired
	private OrderRequestDAO dao ;
	
	@Autowired
	private OrderRepository orderRepository ;
	
	@Autowired
	private OrderItemRepository orderItemRepository ;
	
	@Autowired
	private OrderStatusRepository orderStatusRepository ;
	
	@Override
	public List<OrderRequestDTO> customerOrdersCart(Long customerId , Integer orderStatus) {
		List<OrderRequestDTO> list = dao.customerOrdersCart(customerId , orderStatus) ;		
		return list ;
	}


	@Override
	public Long saveOrder(OrderDTO dto) {
		OrderEntity entity = HelperUtils.convertDtoToEntity(dto , OrderEntity.class) ;

		if(entity.getId() == null) {
			entity.setOrderNumber(generateOrderNumber()) ;
		}		
		entity.setPaymentMethod(1) ;
		entity = orderRepository.save(entity) ;
		Long id = entity.getId() ;
		return id ;
	}


	@Override
	public Long saveOrderItem(OrderItemDTO dto) {
		OrderItemEntity entity = HelperUtils.convertDtoToEntity(dto , OrderItemEntity.class) ;
		Long id = 0L ;
		OrderItemEntity oldEntity = orderItemRepository.findByOrderIdAndItemId(entity.getOrderId() , entity.getItemId()) ;
		
		if(oldEntity != null) {
			Long itemId = entity.getItemId() ;
			Long oldItemId = oldEntity.getItemId() ;
			Long orderId = entity.getOrderId() ;
			Long oldOrderId = oldEntity.getOrderId() ;
			
			if(itemId == oldItemId && orderId == oldOrderId) {
				entity.setId(oldEntity.getId()) ;
				entity.setQuantity(oldEntity.getQuantity() + entity.getQuantity()) ;
			}
			id = orderItemRepository.save(entity).getId() ;
		}else {
			id = orderItemRepository.save(entity).getId() ;
		}
		return id ;
	}


	@Override
	public Long saveOrderStatus(OrderStatusDTO dto) {
		OrderStatusEntity entity = HelperUtils.convertDtoToEntity(dto, OrderStatusEntity.class) ;
		Long id = orderStatusRepository.save(entity).getId() ;
		return id ;
	}


	@Override
	public void delete(Long id) {
		OrderItemEntity entity = orderItemRepository.findById(id).get() ;
		if(entity != null) {
			OrderEntity orderEntity = orderRepository.findById(entity.getOrderId()).get() ;
			Double itemPrice = entity.getItemPrice() * entity.getQuantity() ;			
			Double totalPrice = orderEntity.getTotalPrice() ;
			totalPrice = totalPrice - itemPrice ;
			orderEntity.setTotalPrice(totalPrice) ;
			
			Long count = orderItemRepository.countByOrderId(orderEntity.getId()) ;
			if(count > 1) {
				orderRepository.save(orderEntity) ;
			}else {
				orderRepository.delete(orderEntity) ;
			}
			orderItemRepository.delete(entity) ;
		}
		
	}


	@Override
	public void increaseQuantity(Integer quantity, Long orderItemId , Long orderId , int type) {
		OrderItemEntity entity = orderItemRepository.findById(orderItemId).get() ;
		if(entity != null) {
			Integer oldQuantity = entity.getQuantity() ;
			entity.setQuantity(quantity + oldQuantity) ;
			orderItemRepository.save(entity) ;
				
			Double price = entity.getItemPrice() ;
				
			OrderEntity orderEntity = orderRepository.findById(orderId).get() ;
			if(orderEntity != null) {
				Double totalPrice = orderEntity.getTotalPrice() ;
				Double total = totalPrice + price ;
				orderEntity.setUpdatedDate(new Date()) ;
				orderEntity.setTotalPrice(total) ;
				orderRepository.save(orderEntity) ;
			}			
		}
	}
	
	
	
	
	
	@Override
	public void decreaseQuantity(Integer quantity, Long orderItemId, Long orderId, int type) {
		OrderItemEntity entity = orderItemRepository.findById(orderItemId).get() ;
		if(entity != null) {
			Integer oldQuantity = entity.getQuantity() ;
			if(oldQuantity == 1) {
				orderItemRepository.delete(entity) ;
			}else {
				entity.setQuantity(oldQuantity - quantity) ;
				orderItemRepository.save(entity) ;
			}
			Double price = entity.getItemPrice() ;
			
			OrderEntity orderEntity = orderRepository.findById(orderId).get() ;
			if(orderEntity != null) {
				Double totalPrice = orderEntity.getTotalPrice() ;
				Double total = totalPrice - price ;
				orderEntity.setUpdatedDate(new Date()) ;
				orderEntity.setTotalPrice(total) ;
				orderRepository.save(orderEntity) ;
			}
		}
	}


	private Long generateOrderNumber() {
		Long orderNumber = 0L ;
		GeneralDTO dto = dao.generateOrderNumber() ;
		if(dto != null) {
			orderNumber = dto.getKey() ;
		}
		return orderNumber ;
	}


	@Override
	public OrderDTO findById(Long id) {
		OrderDTO dto = null ;
		OrderEntity entity = orderRepository.findById(id).get() ; 
		if(entity != null) {
			dto = HelperUtils.convertEntityToDto(entity , OrderDTO.class) ;
		}
		return dto ;
	}
	
	
}
