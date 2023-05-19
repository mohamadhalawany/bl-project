package com.bl.service.cms;

import java.util.List;
import java.util.Map;

import com.bl.dto.cms.CareerDTO;

public interface CareerService {
	
	public List<CareerDTO> findAll() ;
	public List<CareerDTO> next() ;
	public List<CareerDTO> previous() ;
	
	public Map<String, Object> metaData() ;
	
	public CareerDTO findById(Integer id) ;
	
	public void save(CareerDTO dto) ;
	public void delete(Integer id) ;
}
