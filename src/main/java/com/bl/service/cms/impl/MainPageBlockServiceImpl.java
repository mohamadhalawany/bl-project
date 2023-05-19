package com.bl.service.cms.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dto.cms.MainPageBlockDTO;
import com.bl.entity.cms.MainPageBlockEntity;
import com.bl.repository.cms.MainPageBlockRepository;
import com.bl.service.cms.MainPageBlockService;


@Service
public class MainPageBlockServiceImpl implements MainPageBlockService {

	@Autowired
	private MainPageBlockRepository repo ;
	
	
	@Override
	public List<MainPageBlockDTO> findAllIsActive() {
		List<MainPageBlockDTO> list = null ;
		List<MainPageBlockEntity> entityList = repo.findAllByIsActive(1) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<MainPageBlockDTO>() ;
			for(MainPageBlockEntity entity : entityList) {
				MainPageBlockDTO dto = HelperUtils.convertEntityToDto(entity , MainPageBlockDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public List<MainPageBlockDTO> findAll() {
		List<MainPageBlockDTO> list = null ;
		List<MainPageBlockEntity> entityList = repo.findAll() ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<MainPageBlockDTO>() ;
			for(MainPageBlockEntity entity : entityList) {
				MainPageBlockDTO dto = HelperUtils.convertEntityToDto(entity , MainPageBlockDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public int save(MainPageBlockDTO dto) {
		MainPageBlockEntity entity = HelperUtils.convertDtoToEntity(dto , MainPageBlockEntity.class) ;
		Object obj = repo.save(entity) ;
		if(obj != null) {
			return 1 ;
		}else {
			return 0 ;
		}
	}

	@Override
	public int delete(Integer id) {
		MainPageBlockDTO dto = findById(id) ;
		if(dto != null) {
			MainPageBlockEntity entity = HelperUtils.convertDtoToEntity(dto , MainPageBlockEntity.class) ;
			repo.delete(entity) ;
			return 1 ;
		}else {
			return 0;
		}
	}

	@Override
	public MainPageBlockDTO findById(Integer id) {
		MainPageBlockDTO dto = null ;
		Optional<MainPageBlockEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			MainPageBlockEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity , MainPageBlockDTO.class) ;
		}
		return dto ;
	}

}
