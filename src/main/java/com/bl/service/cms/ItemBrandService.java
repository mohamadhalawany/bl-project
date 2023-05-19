package com.bl.service.cms;

import java.util.List;

import com.bl.dto.ItemsDTO;
import com.bl.dto.cms.ItemBrandDTO;

public interface ItemBrandService {
	
	public List<ItemBrandDTO> findAllByItemId(Long itemId) ;
	public List<ItemsDTO> findAllByBrandId(Integer brandId) ;
	public ItemBrandDTO findById(Integer id) ;
	public Long countItemsByBrandId(Integer brandId) ;
	public int save(ItemBrandDTO dto) ;
	public int delete(Integer id) ;
}
