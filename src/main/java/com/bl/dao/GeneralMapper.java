package com.bl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bl.dto.GeneralDTO;

public class GeneralMapper implements RowMapper<GeneralDTO> {

	@Override
	public GeneralDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		GeneralDTO dto = new GeneralDTO() ;
		dto.setKey(rs.getLong(1)) ;
		dto.setValueEn(rs.getString(2)) ;
		dto.setValueAr(rs.getString(3)) ;
		dto.setIsoName(rs.getString(4)) ;
		return dto ;
	}

}
