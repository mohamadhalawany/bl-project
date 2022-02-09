package com.bl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bl.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity , Long>{
	public OrderEntity findByCustomerIdAndOrderStatusId(Long customerId , Integer orderStatusId) ;
	public List<OrderEntity> findByOrderStatusIdIn(List<Integer> orderStatusList) ;
}
