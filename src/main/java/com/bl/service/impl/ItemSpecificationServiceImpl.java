package com.bl.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dto.GeneralDTO;
import com.bl.dto.ItemSpecificationsDTO;
import com.bl.entity.ItemSpecificationsEntity;
import com.bl.repository.ItemSpecificationRepository;
import com.bl.service.GeneralService;
import com.bl.service.ItemSpecificationService;

@Service
public class ItemSpecificationServiceImpl implements ItemSpecificationService {
	
	@Autowired
	private ItemSpecificationRepository repo ;
	
	@Autowired
	private GeneralService generalService ;
	
	@Override
	public ItemSpecificationsDTO findAllByItemId(Long itemId) {
		ItemSpecificationsDTO dto = null ;
		ItemSpecificationsEntity entity = repo.findByItemId(itemId) ;
		
		if(entity != null) {
			dto = HelperUtils.convertEntityToDto(entity , ItemSpecificationsDTO.class) ;
			GeneralDTO color = generalService.colorNameById(dto.getColorId()) ;
			dto.setColorNameAr(color.getValueAr()) ;
			dto.setColorNameEn(color.getValueEn()) ;
		}		
		return dto ;
	}

	@Override
	public void save(ItemSpecificationsDTO dto) {
		ItemSpecificationsEntity entity = HelperUtils.convertDtoToEntity(dto , ItemSpecificationsEntity.class) ;
		repo.save(entity) ;
	}

	@Override
	public ItemSpecificationsDTO findById(Long id) {
		ItemSpecificationsDTO dto = null ;
		Optional<ItemSpecificationsEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			ItemSpecificationsEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity , ItemSpecificationsDTO.class) ;
		}
		return dto ;
	}

}
