package com.bl.service.cms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dto.CategoryDTO;
import com.bl.dto.cms.MainMenuDTO;
import com.bl.entity.CategoryEntity;
import com.bl.entity.cms.MainMenuEntity;
import com.bl.repository.cms.CMSCategoryRepository;
import com.bl.repository.cms.MainMenuRepository;
import com.bl.service.cms.CategoryService;
import com.bl.service.cms.MainMenuService;

@Service
public class MainMenuServiceImpl implements MainMenuService {
	
	@Autowired
	private MainMenuRepository repo ;
	@Autowired 
	private CMSCategoryRepository categoryRepository ;
	@Autowired
	private CategoryService categoryService ;
	
	@Override
	public List<MainMenuDTO> findAll() {
		List<MainMenuDTO> list = null ;
		List<MainMenuEntity> entityList = repo.findAll() ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<MainMenuDTO>() ;
			for(MainMenuEntity entity : entityList) {				
				MainMenuDTO dto = HelperUtils.convertEntityToDto(entity , MainMenuDTO.class) ;
				dto.setUpdatedDateString(entity.getUpdatedDate().toString());
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public MainMenuDTO findById(Integer id) {
		MainMenuDTO dto = null ;
		Optional<MainMenuEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			MainMenuEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity , MainMenuDTO.class) ;
			dto.setUpdatedDateString(HelperUtils.formatDate(dto.getUpdatedDate())) ;
		}
		return dto ;
	}

	@Override
	public int save(MainMenuDTO dto) {
		MainMenuEntity entity = HelperUtils.convertDtoToEntity(dto , MainMenuEntity.class) ;
		entity = repo.save(entity) ;
		if(entity != null && entity.getId() > 0) {
			return 1 ;
		}else {
			return 0;
		}
	}

	@Override
	public List<CategoryDTO> findAllCategoryByMenuId(Integer menuId) {
		List<CategoryDTO> list = null ;
		List<CategoryEntity> categoryList = categoryRepository.findByMenuId(menuId) ;
		if(categoryList != null && !categoryList.isEmpty()) {
			list = new ArrayList<CategoryDTO>() ;
			for(CategoryEntity entity : categoryList) {
				CategoryDTO dto = HelperUtils.convertEntityToDto(entity , CategoryDTO.class) ;
				if(dto != null && dto.getParentCategoryId() != null) {
					CategoryDTO parent = categoryService.findById(dto.getParentCategoryId()) ;
					dto.setParentCategoryName(parent.getCategoryName()) ;
				}else {
					dto.setParentCategoryName(dto.getCategoryName()) ;
					dto.setCategoryName(null) ;
				}
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	@Override
	public int saveCategoryMenuId(CategoryDTO dto , CategoryDTO parent) {
		parent = categoryService.findById(parent.getId()) ;
		parent.setMenuId(1) ;
		parent.setUpdatedBy(parent.getUpdatedBy()) ;
		parent.setUpdatedDate(new Date()) ;
		categoryService.save(parent) ;
		
		dto = categoryService.findById(dto.getId()) ;
		parent.setMenuId(1) ;
		parent.setUpdatedBy(dto.getUpdatedBy()) ;
		parent.setUpdatedDate(new Date()) ;
		categoryService.save(dto) ;
		return 0;
	}	
}
