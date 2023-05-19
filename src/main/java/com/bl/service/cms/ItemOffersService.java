package com.bl.service.cms;

import java.util.List;

import com.bl.dto.cms.ItemOffersDTO;
import com.bl.dto.cms.OffersDTO;

public interface ItemOffersService {
	
	public List<ItemOffersDTO> findAllByItemId(Long itemId) ;
	public ItemOffersDTO findById(Integer id) ;
	public List<OffersDTO> availableOffers(Long itemId) ;
	public int save(ItemOffersDTO dto) ;
	public int delete(Integer id , Integer updatedBy) ;
	public Long countByItemId(Long itemId) ;
	public Integer findMaxOfferIdByItemId(Long itemId) ; 
	public Integer findMaxIdByItemId(Long itemId) ; 
}
