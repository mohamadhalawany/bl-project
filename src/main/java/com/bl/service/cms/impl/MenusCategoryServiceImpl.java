package com.bl.service.cms.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dto.cms.MenusCategoryDTO;
import com.bl.entity.cms.MenusCategoryEntity;
import com.bl.repository.cms.MenusCategoryRepository;
import com.bl.service.cms.MenusCategoryService;

@Service
public class MenusCategoryServiceImpl implements MenusCategoryService {

	@Autowired
	private MenusCategoryRepository repo ;
	
	@Override
	public int save(MenusCategoryDTO dto) {
		MenusCategoryEntity entity = HelperUtils.convertDtoToEntity(dto , MenusCategoryEntity.class) ;
		entity = repo.save(entity) ;
		if(entity != null) {
			return entity.getId() ;
		}else {
			return 0;
		}
	}

	@Override
	public List<MenusCategoryDTO> findAll() {
		List<MenusCategoryEntity> entityList = repo.findAll() ;
		List<MenusCategoryDTO> list = null ;
		if(entityList != null && !entityList.isEmpty() && entityList.size() != 0) {
			list = new ArrayList<MenusCategoryDTO>() ;
			for(MenusCategoryEntity entity : entityList) {
				MenusCategoryDTO dto = HelperUtils.convertEntityToDto(entity , MenusCategoryDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

}
