package com.bl.service.impl;

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

}
