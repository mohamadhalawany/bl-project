package com.bl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bl.entity.ItemSpecificationsEntity;

public interface ItemSpecificationRepository extends JpaRepository<ItemSpecificationsEntity , Long> {
	
	public ItemSpecificationsEntity findByItemId(Long itemId) ;
}
