package com.bl.service;

import com.bl.dto.ItemSpecificationsDTO;

public interface ItemSpecificationService {
	
	public ItemSpecificationsDTO findAllByItemId(Long itemId) ;
	public ItemSpecificationsDTO findById(Long id) ;
	public void save(ItemSpecificationsDTO dto) ;
}	
