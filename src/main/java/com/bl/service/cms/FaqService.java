package com.bl.service.cms;

import java.util.List;
import java.util.Map;

import com.bl.dto.cms.FaqDTO;

public interface FaqService {
	
	public FaqDTO findById(Integer id) ;
	public List<FaqDTO> findAll() ;
	public List<FaqDTO> next() ;
	public List<FaqDTO> previous() ;
	public void save(FaqDTO dto) ;
	public void delete(Integer id) ;
	
	public Map<String, Object> metaData() ;
}
