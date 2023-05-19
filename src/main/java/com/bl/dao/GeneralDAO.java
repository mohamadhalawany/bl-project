package com.bl.dao;

import java.util.List;

import com.bl.dto.CountryGovernorateCityDistrictDTO;
import com.bl.dto.GeneralDTO;

public interface GeneralDAO {
	
	public List<GeneralDTO> colorsList() ;
	public List<GeneralDTO> countryList() ;
	public List<GeneralDTO> governorateList(Integer countryId) ;
	public List<GeneralDTO> cityDistrictList(Integer governorateId) ;
	public List<GeneralDTO> currencyList() ;
	public List<CountryGovernorateCityDistrictDTO> findGovernorateIdAndCountryIdByCityDistrictId(Integer cityDistrictId) ;
	
	public GeneralDTO currencyById(Integer id) ;
	public GeneralDTO colorNameById(Integer id) ;
	
	public Integer orderExpireDays() ;
}
