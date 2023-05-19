package com.bl.service.cms.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dto.cms.ItemProductTypeDTO;
import com.bl.entity.cms.ItemProductTypeEntity;
import com.bl.repository.cms.ItemProductTypeRepository;
import com.bl.service.cms.ItemProductTypeService;

@Service
public class ItemProductTypeServiceImpl implements ItemProductTypeService {

	@Autowired
	private ItemProductTypeRepository repo ;

	
	
	@Override
	public Long countByProductTypeId(Integer productTypeId) {
		Long count = repo.countByProductTypeId(productTypeId) ;
		return count ;
	}

	@Override
	public List<ItemProductTypeDTO> findAllByItemId(Long itemId) {
		List<ItemProductTypeDTO> list = null ;
		List<ItemProductTypeEntity> entityList = repo.findAllByItemId(itemId) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<ItemProductTypeDTO>() ;
			for(ItemProductTypeEntity entity : entityList) {
				ItemProductTypeDTO dto = HelperUtils.convertEntityToDto(entity, ItemProductTypeDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public ItemProductTypeDTO findById(Integer id) {
		ItemProductTypeDTO dto = null ;
		Optional<ItemProductTypeEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			ItemProductTypeEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity , ItemProductTypeDTO.class) ;
		}
		return dto ;
	}

	@Override
	public Integer save(ItemProductTypeDTO dto) {
		Long checkProductTypeIdAndItemId = repo.countByProductTypeIdAndItemId(dto.getProductTypeId() , dto.getItemId()) ;
		if(checkProductTypeIdAndItemId == 0) {
			ItemProductTypeEntity entity = HelperUtils.convertDtoToEntity(dto , ItemProductTypeEntity.class) ;
			entity = repo.save(entity) ;
			return 1 ;
		}else {
			return 0 ;
		}
	}

	@Override
	public int delete(Integer id) {
		Optional<ItemProductTypeEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			ItemProductTypeEntity entity = opt.get() ;
			repo.delete(entity) ;
			return 0 ;
		}else {
			return 1 ;
		}
	}

}
