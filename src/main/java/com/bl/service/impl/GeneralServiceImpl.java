package com.bl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bl.dao.GeneralDAO;
import com.bl.dto.CountryGovernorateCityDistrictDTO;
import com.bl.dto.GeneralDTO;
import com.bl.service.GeneralService;

@Service
public class GeneralServiceImpl implements GeneralService {
	
	@Autowired
	private GeneralDAO dao ;
	
	@Override
	public List<GeneralDTO> colorsList() {
		List<GeneralDTO> list = dao.colorsList() ;
		return list ;
	}
	@Override
	public GeneralDTO colorNameById(Integer id) {
		GeneralDTO dto = dao.colorNameById(id) ;
		return dto ;
	}
	@Override
	public List<GeneralDTO> countryList() {		
		return dao.countryList() ;
	}
	@Override
	public List<GeneralDTO> governorateList(Integer countryId) {
		return dao.governorateList(countryId) ;
	}
	@Override
	public List<GeneralDTO> cityDistrictList(Integer governorateId) {
		return dao.cityDistrictList(governorateId) ;
	}
	@Override
	public List<CountryGovernorateCityDistrictDTO> findGovernorateIdAndCountryIdByCityDistrictId(Integer cityDistrictId) {
		return dao.findGovernorateIdAndCountryIdByCityDistrictId(cityDistrictId) ;
	}
	@Override
	public GeneralDTO currencyById(Integer id) {
		return dao.currencyById(id) ;
	}
	@Override
	public List<GeneralDTO> currencyList() {
		return dao.currencyList() ;
	}
	@Override
	public Integer orderExpireDays() {
		return dao.orderExpireDays() ;
	}
}
