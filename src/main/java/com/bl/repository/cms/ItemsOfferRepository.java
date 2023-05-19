package com.bl.repository.cms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bl.entity.cms.ItemOffersEntity;

@Repository
public interface ItemsOfferRepository extends JpaRepository<ItemOffersEntity , Integer> {
	
	public List<ItemOffersEntity> findAllByItemId(Long itemId) ;
	public Long countByItemId(Long itemId) ;
	
	@Query("SELECT MAX(o.offerId) FROM ItemOffersEntity o WHERE o.itemId = ?1")
	public Integer findMaxOfferIdByItemId(Long itemId) ; 
	
	@Query("SELECT MAX(o.id) FROM ItemOffersEntity o WHERE o.itemId = ?1")
	public Integer findMaxIdByItemId(Long itemId) ; 
}
