package com.bl.service.cms;

import java.util.List;
import java.util.Map;

import com.bl.dto.cms.OffersDTO;

public interface OffersService {
	
	public List<OffersDTO> search(OffersDTO dto) ;	
	public List<OffersDTO> findAll() ;
	public List<OffersDTO> next() ;
	public List<OffersDTO> previous() ;
	
	public Map<String, Object> metaData() ;
	
	public OffersDTO findById(Integer id) ;
	public Integer save(OffersDTO dto) ;
	public Integer saveActive(OffersDTO dto) ;
	public Integer delete(Integer id) ;
}
