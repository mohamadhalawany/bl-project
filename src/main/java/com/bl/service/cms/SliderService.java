package com.bl.service.cms;

import java.util.List;

import com.bl.dto.cms.SliderDTO;

public interface SliderService {
	
	public int save(SliderDTO dto) ;
	public SliderDTO findById(Integer id) ;
	public List<SliderDTO> findAll() ;
}
