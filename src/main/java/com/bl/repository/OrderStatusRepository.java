package com.bl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bl.entity.OrderStatusEntity;

public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity , Long> {
	
	public List<OrderStatusEntity> findAllByOrderId(Long orderId) ;
}
