package com.bl.dao;

import java.util.List;

import com.bl.dto.CountryGovernorateCityDistrictDTO;
import com.bl.dto.GeneralDTO;

public interface GeneralDAO {
	
	public List<GeneralDTO> colorsList() ;
	public GeneralDTO colorNameById(Integer id) ;
	public List<GeneralDTO> countryList() ;
	public List<GeneralDTO> governorateList(Long countryId) ;
	public List<GeneralDTO> cityDistrictList(Long governorateId) ;
	public List<CountryGovernorateCityDistrictDTO> findGovernorateIdAndCountryIdByCityDistrictId(Long cityDistrictId) ;
	public GeneralDTO currencyById(Integer id) ;
}
