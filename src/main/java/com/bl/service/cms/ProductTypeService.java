package com.bl.service.cms;

import java.util.List;
import java.util.Map;

import com.bl.dto.cms.ProductTypeDTO;

public interface ProductTypeService {

	public List<ProductTypeDTO> findAll() ;
	public List<ProductTypeDTO> next() ;
	public List<ProductTypeDTO> previous() ;
	public List<ProductTypeDTO> findAllIsActive() ;
	
	public Map<String, Object> metaData() ;
	
	public ProductTypeDTO findById(Integer id) ;
	
	public Integer save(ProductTypeDTO dto) ;
	public Integer delete(Integer id) ;
}
