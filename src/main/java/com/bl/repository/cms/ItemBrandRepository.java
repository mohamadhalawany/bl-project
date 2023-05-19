package com.bl.repository.cms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bl.entity.cms.ItemBrandEntity;

public interface ItemBrandRepository extends JpaRepository<ItemBrandEntity , Integer> {
	
	public Long countByBrandId(Integer brandId) ;
	public Long countByBrandIdAndItemId(Integer brandId , Long itemId) ;
	public List<ItemBrandEntity> findAllByItemIdOrderByIdDesc(Long itemId) ;
	public List<ItemBrandEntity> findAllByBrandId(Integer brandId) ;
}
