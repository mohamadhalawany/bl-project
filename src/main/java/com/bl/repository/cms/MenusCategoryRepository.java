package com.bl.repository.cms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bl.entity.cms.MenusCategoryEntity;

@Repository
public interface MenusCategoryRepository extends JpaRepository<MenusCategoryEntity , Integer> {
	
	public List<MenusCategoryEntity> findAllByCategoryId(Integer categoryId) ;
}
