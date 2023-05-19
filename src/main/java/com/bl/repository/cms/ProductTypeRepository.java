package com.bl.repository.cms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bl.entity.cms.ProductTypeEntity;

public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity , Integer> {
	
	public List<ProductTypeEntity> findAllByIsActive(Integer isActive) ;
}
