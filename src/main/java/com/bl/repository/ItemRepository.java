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
	
	@Query(value = 
			"SELECT i FROM ItemEntity i , CategoryEntity c "
			+ " WHERE i.categoryId = c.id "
			+ " AND (?1 IS NULL OR c.id = ?1) "
			+ " AND i.itemName LIKE %?2% "
			+ " AND i.description LIKE %?3% "
			+ " AND i.itemCode LIKE %?4% "
			+ " AND (?5 IS NULL OR c.parentCategoryId = ?5) ")
	public List<ItemEntity> findAll(Long categoryId , String itemName , String description , String itemCode ,  Long parentCategoryId) ;
	
	public Long countByCategoryId(Long categoryId) ;	
	public Page<ItemEntity> findAllByIsHidden(Integer isHidden , Pageable page) ;
	public List<ItemEntity> findByCategoryId(Long categoryId) ;
	public List<ItemEntity> findByItemNameContainingIgnoreCase(String itemName) ;
	public List<ItemEntity> findByItemNameContainingIgnoreCaseOrderByItemPriceAsc(String itemName) ;
	public List<ItemEntity> findByItemNameContainingIgnoreCaseOrderByItemPriceDesc(String itemName) ;
}
