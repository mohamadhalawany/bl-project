package com.bl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bl.dto.OrderRequestDTO;

public class OrderRequestMapper implements RowMapper<OrderRequestDTO> {

	@Override
	public OrderRequestDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrderRequestDTO dto = new OrderRequestDTO() ;
		dto.setItemName(rs.getString(1)) ;
		dto.setTotalPrice(rs.getDouble(2)) ;
		dto.setItemLogo(rs.getString(3)) ;
		dto.setCurrencyId(rs.getInt(4)) ;
		dto.setQuantity(rs.getInt(5)) ;
		dto.setOrderItemId(rs.getLong(6)) ;
		dto.setItemPrice(rs.getDouble(7)) ;
		dto.setOrderId(rs.getLong(8)) ;
		dto.setOrderNumber(rs.getLong(9)) ;
		return dto ;
	}

}
