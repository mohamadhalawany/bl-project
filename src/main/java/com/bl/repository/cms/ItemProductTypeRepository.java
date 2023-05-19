package com.bl.repository.cms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bl.entity.cms.ItemProductTypeEntity;

@Repository
public interface ItemProductTypeRepository extends JpaRepository<ItemProductTypeEntity , Integer> {

	public Long countByProductTypeId(Integer productTypeId) ;
	public Long countByProductTypeIdAndItemId(Integer productTypeId , Long itemId) ;
	public List<ItemProductTypeEntity> findAllByItemId(Long itemId) ;	
}
