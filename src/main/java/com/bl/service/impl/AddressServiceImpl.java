package com.bl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bl.dto.AddressDTO;
import com.bl.entity.CitiesDistrictEntity;
import com.bl.entity.CountryEntity;
import com.bl.entity.GovernorateEntity;
import com.bl.repository.CityDistrictRepository;
import com.bl.repository.CountryRepository;
import com.bl.repository.GovernorateRepository;
import com.bl.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private CountryRepository countryRepo ;
	
	@Autowired
	private GovernorateRepository governorateRepo ;
	
	@Autowired
	private CityDistrictRepository cityDistrictRepo ;
	
	@Override
	public AddressDTO findCountryById(Integer id) {
		AddressDTO dto = null ;
		CountryEntity entity = countryRepo.findById(id).get() ;
		if(entity != null) {
			dto = new AddressDTO() ;
			dto.setCountryId(id) ;
			dto.setCountryName(entity.getCountryName()) ;
			dto.setCountryNameAr(entity.getCountryNameAr()) ;
		}
		return dto ;
	}

	@Override
	public AddressDTO findGovernorateById(Integer id) {
		AddressDTO dto = null ;
		GovernorateEntity entity = governorateRepo.findById(id).get() ;
		if(entity != null) {
			dto = new AddressDTO() ;
			dto.setGovernorateId(id) ;
			dto.setCountryId(entity.getCountryId()) ;
			dto.setCountryName(entity.getGovernorateNameAr()) ;
			dto.setCountryNameAr(entity.getGovernorateNameEn()) ;
		}
		return dto ;
	}

	@Override
	public AddressDTO findCitiesDistrictById(Integer id) {
		AddressDTO dto = null ;
		CitiesDistrictEntity entity = cityDistrictRepo.findById(id).get() ;
		if(entity != null) {
			dto = new AddressDTO() ;
			dto.setCitiesDistrictId(id) ;
			dto.setGovernorateId(entity.getGovernorateId()) ;
			dto.setCountryName(entity.getNameEn()) ;
			dto.setCountryNameAr(entity.getNameAr()) ;
		}
		return dto ;
	}

}
