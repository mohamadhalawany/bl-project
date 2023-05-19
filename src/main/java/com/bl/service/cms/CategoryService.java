package com.bl.service.cms;

import java.util.List;
import java.util.Map;

import com.bl.dto.CategoryDTO;

public interface CategoryService {
	
	public List<CategoryDTO> findAll() ;
	public List<CategoryDTO> next() ;
	public List<CategoryDTO> previous() ;
	public Map<String, Object> metaData() ;
	
	public List<CategoryDTO> findAllNot(Long id) ;
	public List<CategoryDTO> findAllCategories() ;
	public List<CategoryDTO> findByParentCategoryId(Long parentCategoryId) ;
	public List<CategoryDTO> findByMenuIdNot(Integer menuId) ;
	public List<CategoryDTO> findByParentCategoryIdIsNull(Integer menuId) ;
	public List<CategoryDTO> findByParentCategoryIdIsNotNull() ;
	public List<CategoryDTO> findByParentCategoryIdAndMenuId(Long parentCategoryId , Integer menuId) ;
	
	public CategoryDTO findById(Long id) ;
	
	public Long save(CategoryDTO dto) ;
	public void delete(Long id) ;
	
	public int checkCategoryInItems(Long id) ;
}
