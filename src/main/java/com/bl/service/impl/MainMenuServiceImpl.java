package com.bl.service.impl;

import java.util.List;

import com.bl.dao.impl.MainMenuDAO;
import com.bl.dto.CategoryDTO;
import com.bl.dto.cms.BrandDTO;
import com.bl.dto.cms.CompanyInfoDTO;
import com.bl.dto.cms.MainMenuDTO;
import com.bl.service.MainMenuService;


public class MainMenuServiceImpl implements MainMenuService{
	
	private MainMenuDAO dao = new MainMenuDAO() ;

	@Override
	public List<MainMenuDTO> findAll() {
		
		return null ;
	}
	
	
	@Override
	public List<MainMenuDTO> findAllByIsActive() {
		List<MainMenuDTO> list = dao.findAllByIsActive(1) ;
		return list ;
	}



	@Override
	public MainMenuDTO findById(Integer id) {
		MainMenuDTO dto = dao.findById(id) ;
		return dto ;
	}


	@Override
	public List<CategoryDTO> findAllMainCategories() {
		List<CategoryDTO> list = dao.findAllMainCategories() ;
		return list ;
	}


	@Override
	public List<BrandDTO> brandsMenu() {
		List<BrandDTO> list = dao.brandsMenu() ;
		return list ;
	}


	@Override
	public CompanyInfoDTO companyProfile() {
		CompanyInfoDTO company = dao.companyProfile() ;
		return company ;
	}
}
