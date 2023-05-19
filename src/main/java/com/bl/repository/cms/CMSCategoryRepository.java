package com.bl.repository.cms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bl.entity.CategoryEntity;

@Repository
public interface CMSCategoryRepository extends JpaRepository<CategoryEntity , Long> {
	
	public List<CategoryEntity> findByIdNot(Long id) ;
	public List<CategoryEntity> findByMenuId(Integer menuId) ;
	public List<CategoryEntity> findByParentCategoryId(Long parentCategoryId) ;
	public List<CategoryEntity> findByParentCategoryIdAndMenuId(Long parentCategoryId , Integer menuId) ;
	public List<CategoryEntity> findByParentCategoryIdIsNotNull() ;

	@Query("SELECT c FROM CategoryEntity c WHERE c.menuId <> ?1 OR c.menuId IS NULL AND c.parentCategoryId IS NOT NULL AND isActive = 1")
	public List<CategoryEntity> findByMenuIdNot(Integer menuId) ;	
	
	@Query("SELECT c FROM CategoryEntity c WHERE (c.menuId <> ?1 OR c.menuId IS NULL) AND c.parentCategoryId IS NULL AND isActive = 1")
	public List<CategoryEntity> findByParentCategoryIdIsNull(Integer menuId) ;
	
	@Query("SELECT c FROM CategoryEntity c WHERE c.parentCategoryId = ?1 AND (c.menuId <> ?2 OR c.menuId IS NULL) AND isActive = 1")
	public List<CategoryEntity> findByParentCategoryIdAndMenuIdNotEqual(Long parentCategoryId , Integer menuId) ;
	
	@Query("SELECT count(c.id) FROM CategoryEntity c WHERE c.menuId = ?1 and c.id=?2")
	public long countByMenuId(Integer menuId , Long id) ;
}
