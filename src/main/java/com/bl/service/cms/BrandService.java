package com.bl.service.cms;

import java.util.List;
import java.util.Map;

import com.bl.dto.cms.BrandDTO;

public interface BrandService {

	public List<BrandDTO> findAllIsActive() ;
	public List<BrandDTO> findAll() ;
	public List<BrandDTO> next() ;
	public List<BrandDTO> previous() ;
	
	public Map<String, Object> metaData() ;
	
	public BrandDTO findById(Integer id) ;
	public Integer save(BrandDTO dto) ;
	public int delete(Integer id) ;
}
