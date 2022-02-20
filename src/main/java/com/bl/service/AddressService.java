package com.bl.service;

import com.bl.dto.AddressDTO;

public interface AddressService {
	
	public AddressDTO findCountryById(Integer id) ;
	public AddressDTO findGovernorateById(Integer id) ;
	public AddressDTO findCitiesDistrictById(Integer id) ;
}
