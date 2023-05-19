package com.bl.service.cms;

import java.util.List;

import com.bl.dto.cms.ItemProductTypeDTO;

public interface ItemProductTypeService {

	public Long countByProductTypeId(Integer productTypeId) ;
	public List<ItemProductTypeDTO> findAllByItemId(Long itemId) ;
	public ItemProductTypeDTO findById(Integer id) ;
	public Integer save(ItemProductTypeDTO dto) ;
	public int delete(Integer id) ;
}
