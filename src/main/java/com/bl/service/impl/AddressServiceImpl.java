package com.bl.service.impl;

import java.util.Optional;

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
			dto.setCountryId(entity.getCountryId()) ;
			dto.setGovernorateNameEn(entity.getGovernorateNameEn()) ;
			dto.setGovernorateNameAr(entity.getGovernorateNameAr()) ;
		}
		return dto ;
	}

	@Override
	public AddressDTO findCitiesDistrictById(Integer id) {
		AddressDTO dto = null ;
		Optional<CitiesDistrictEntity> opt = cityDistrictRepo.findById(id) ; 		
		if(opt != null && !opt.isEmpty()) {
			CitiesDistrictEntity entity = opt.get() ;
			dto = new AddressDTO() ;
			dto.setGovernorateId(entity.getGovernorateId()) ;
			dto.setCitiesDistrictName(entity.getNameEn()) ;
			dto.setCitiesDistrictNameAr(entity.getNameAr()) ;
		}
		return dto ;
	}

}
