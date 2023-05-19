package com.bl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bl.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity , Long>{
	
	public OrderEntity findByCustomerIdAndOrderStatusId(Long customerId , Integer orderStatusId) ;
	public List<OrderEntity> findByCustomerIdOrderByIdDesc(Long customerId) ;
	
	@Query("SELECT o FROM OrderEntity o "
				+ " WHERE o.orderStatusId = 2")
	public Page<OrderEntity> newOrders(Pageable pageable) ;
	
	@Query(value = "SELECT max(orderNumber) from OrderEntity")
	public Long maxOrderNumber() ;
}
