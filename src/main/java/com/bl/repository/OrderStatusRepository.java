package com.bl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bl.entity.OrderStatusEntity;

public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity , Long> {
	
	public List<OrderStatusEntity> findAllByOrderIdOrderByIdDesc(Long orderId) ;
	public Page<OrderStatusEntity> findAllByOrderId(Long orderId , Pageable pageable) ;
}
