package com.bl.service;

import java.util.List;
import java.util.Map;

import com.bl.dto.ItemsDTO;

public interface ItemService {
	
	public List<ItemsDTO> findAll() ;
	public List<ItemsDTO> findAllItemsNextPage() ;
	public List<ItemsDTO> findAllItemsPreviousPage() ;
	public List<ItemsDTO> findAllByCategoryIdAndNotEqualId(Long id , Long categoryId) ;		
	public List<ItemsDTO> findAll(ItemsDTO dto) ;
	public List<ItemsDTO> findAllByCategoryId(Long categoryId) ;
	public List<ItemsDTO> findAllByItemName(String itemName) ;
	public List<ItemsDTO> findAllByItemName(String itemName , int sortType) ;
	public List<ItemsDTO> mostPopular() ;
	public List<ItemsDTO> lastAdded() ;
	
	public Map<String , Object> metaData() ;
	
	public ItemsDTO findById(Long id) ;
	public ItemsDTO itemDetails(Long id) ;
	
	public Long save(ItemsDTO dto) ;
	public void delete(Long id) ;
}
