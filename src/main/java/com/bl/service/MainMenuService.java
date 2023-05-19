package com.bl.service;

import java.util.List;

import com.bl.dto.CategoryDTO;
import com.bl.dto.cms.BrandDTO;
import com.bl.dto.cms.CompanyInfoDTO;
import com.bl.dto.cms.MainMenuDTO;

public interface MainMenuService {
		
	public List<MainMenuDTO> findAll() ;
	public List<MainMenuDTO> findAllByIsActive() ;
	public MainMenuDTO findById(Integer id) ;
	
	public List<BrandDTO> brandsMenu() ;	
	
	public List<CategoryDTO> findAllMainCategories() ;
	
	public CompanyInfoDTO companyProfile() ;
}
