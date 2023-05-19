package com.bl.service.cms;

import java.util.List;

import com.bl.dto.cms.CompanyInfoDTO;

public interface CompanyInfoService {
	public List<CompanyInfoDTO> findAll() ;
	public CompanyInfoDTO findById(Integer id) ;
	public void save(CompanyInfoDTO dto) ;
	public void delete(Integer dto) ;
}
