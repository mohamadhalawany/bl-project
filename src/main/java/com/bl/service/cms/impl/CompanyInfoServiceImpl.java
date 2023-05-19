package com.bl.service.cms.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dto.AddressDTO;
import com.bl.dto.cms.CompanyInfoDTO;
import com.bl.entity.cms.CompanyInfoEntity;
import com.bl.repository.cms.CompanyInfoRepository;
import com.bl.service.AddressService;
import com.bl.service.cms.CompanyInfoService;

@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {
	
	@Autowired
	private CompanyInfoRepository repo ;
	@Autowired
	private AddressService addressService ;
	
	@Override
	public CompanyInfoDTO findById(Integer id) {
		CompanyInfoDTO dto = null ;
		Optional<CompanyInfoEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			CompanyInfoEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity , CompanyInfoDTO.class) ;
			String address = dto.getAddress() ;
			
			if(dto.getCityId() != null) {
				AddressDTO cityDistrict = addressService.findCitiesDistrictById(dto.getCityId()) ;
				if(cityDistrict != null) {
					address += " , " + cityDistrict.getCitiesDistrictName() ;
					AddressDTO governorate = addressService.findGovernorateById(cityDistrict.getGovernorateId()) ;
					if(governorate != null) {
						address += " , " + governorate.getGovernorateNameEn() ;
						
						dto.setGovernorateId(governorate.getGovernorateId()) ;
						AddressDTO country = addressService.findCountryById(governorate.getCountryId()) ;
						if(country != null) {
							address += " , " + country.getCountryName() ;
							dto.setCountryId(country.getCountryId()) ;
						}
					}
				}
			}
			dto.setAddress(address) ;
		}
		return dto ;
	}

	@Override
	public void save(CompanyInfoDTO dto) {
		CompanyInfoEntity entity = HelperUtils.convertDtoToEntity(dto , CompanyInfoEntity.class) ;
		repo.save(entity) ;
	}

	@Override
	public void delete(Integer dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CompanyInfoDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
