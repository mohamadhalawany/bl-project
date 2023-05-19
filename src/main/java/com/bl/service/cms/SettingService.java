package com.bl.service.cms;

import java.util.List;
import java.util.Map;

import com.bl.dto.ItemsDTO;
import com.bl.dto.cms.CompanyTermsConditionDTO;
import com.bl.dto.cms.SettingDTO;

public interface SettingService {

	public List<ItemsDTO> itemsShowList() ;
	public List<ItemsDTO> next() ;
	public List<ItemsDTO> previous() ;
	
	public SettingDTO findById(Integer id) ;
	
	public Map<String, Object> metaData() ;
	
	public List<SettingDTO> findAllForSlice() ;
	
	public Integer save(SettingDTO dto) ;
	public int delete(Integer id) ;
	
	public CompanyTermsConditionDTO findTermsConditionByCompanyId(Integer companyId) ;
	public Integer saveTermsCondition(CompanyTermsConditionDTO dto) ;
}
