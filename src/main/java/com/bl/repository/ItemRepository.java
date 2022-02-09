package com.bl.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bl.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity , Long>{
	
	public Page<ItemEntity> findAll(Pageable pageable) ;
	
	@Query(value = "SELECT i FROM ItemEntity i WHERE i.id <> :id AND i.categoryId = :categoryId")
	public List<ItemEntity> findAllByCategoryIdAndNotEqualId(@Param("id") Long id , @Param("categoryId") Long categoryId) ;
}
