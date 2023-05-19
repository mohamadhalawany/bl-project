package com.bl.service.cms;

import java.util.List;

import com.bl.dto.cms.MainPageBlockDTO;

public interface MainPageBlockService {
	
	public List<MainPageBlockDTO> findAllIsActive() ;
	public List<MainPageBlockDTO> findAll() ;
	public MainPageBlockDTO findById(Integer id) ;
	public int save(MainPageBlockDTO dto) ;
	public int delete(Integer id) ;
}
