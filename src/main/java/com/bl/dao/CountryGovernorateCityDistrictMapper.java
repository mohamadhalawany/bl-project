package com.bl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bl.dto.CountryGovernorateCityDistrictDTO;

public class CountryGovernorateCityDistrictMapper implements RowMapper<CountryGovernorateCityDistrictDTO>{

	@Override
	public CountryGovernorateCityDistrictDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CountryGovernorateCityDistrictDTO dto = new CountryGovernorateCityDistrictDTO() ;
		dto.setCityDistrictId(rs.getInt(1)) ;
		dto.setGovernorateId(rs.getInt(2)) ;
		dto.setCountryId(rs.getInt(3)) ;
		dto.setCityDistrictNameEn(rs.getString(4)) ;
		dto.setCityDistrictNameAr(rs.getString(5)) ;
		dto.setGovernorateNameEn(rs.getString(6)) ;
		dto.setGovernorateNameAr(rs.getString(7)) ;
		dto.setCountryNameEn(rs.getString(8)) ;
		dto.setCountryNameAr(rs.getString(9)) ;
		return dto ;
	}
	
}
