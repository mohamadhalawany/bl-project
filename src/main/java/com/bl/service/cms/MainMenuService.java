package com.bl.service.cms;

import java.util.List;

import com.bl.dto.CategoryDTO;
import com.bl.dto.cms.MainMenuDTO;

public interface MainMenuService {
	
	public List<MainMenuDTO> findAll() ;
	public MainMenuDTO findById(Integer id) ;
	public List<CategoryDTO> findAllCategoryByMenuId(Integer menuId) ;
	public int save(MainMenuDTO dto) ;
	public int saveCategoryMenuId(CategoryDTO dto , CategoryDTO parent) ;
}
