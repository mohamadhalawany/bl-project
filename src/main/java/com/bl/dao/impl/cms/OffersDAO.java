package com.bl.dao.impl.cms;

import java.util.List;

import com.bl.dto.cms.OffersDTO;

public interface OffersDAO {
	
	public List<OffersDTO> search(OffersDTO dto) ;
	public List<OffersDTO> availableOffers(Long itemId) ;
	public Integer dateBeforeDate(String from , String to) ;
}
