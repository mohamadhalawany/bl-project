package com.bl.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bl.entity.CustomerReviewsEntity;

public interface CustomerReviewsRepository extends JpaRepository<CustomerReviewsEntity , Long> {
	
	public Page<CustomerReviewsEntity> findAllByItemId(Pageable pageable , Long itemId) ;
}
