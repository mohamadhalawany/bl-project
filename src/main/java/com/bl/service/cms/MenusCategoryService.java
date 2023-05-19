package com.bl.service.cms;

import java.util.List;

import com.bl.dto.cms.MenusCategoryDTO;

public interface MenusCategoryService {

	public int save(MenusCategoryDTO dto) ;
	public List<MenusCategoryDTO> findAll() ;
}
