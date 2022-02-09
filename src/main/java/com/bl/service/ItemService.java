package com.bl.service;

import java.util.List;
import java.util.Map;

import com.bl.dto.ItemsDTO;

public interface ItemService {
	
	public List<ItemsDTO> findAll() ;
	public List<ItemsDTO> findAllItemsNextPage() ;
	public List<ItemsDTO> findAllItemsPreviousPage() ;
	public List<ItemsDTO> findAllByCategoryIdAndNotEqualId(Long id , Long categoryId) ;
	public Map<String , Object> metaData() ;
	
	public ItemsDTO findById(Long id) ;
}
