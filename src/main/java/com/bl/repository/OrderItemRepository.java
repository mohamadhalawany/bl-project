package com.bl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bl.entity.OrderItemEntity;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity , Long> {
	public Long countByOrderId(Long orderId) ;	
	public OrderItemEntity findByOrderIdAndItemId(Long orderId , Long itemId) ; 
	public List<OrderItemEntity> findAllByOrderId(Long orderId) ;
}
