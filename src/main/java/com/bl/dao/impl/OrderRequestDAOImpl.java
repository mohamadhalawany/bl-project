package com.bl.dao.impl;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bl.dao.GeneralMapper;
import com.bl.dao.OrderRequestDAO;
import com.bl.dao.OrderRequestMapper;
import com.bl.dto.GeneralDTO;
import com.bl.dto.OrderRequestDTO;

@Component
public class OrderRequestDAOImpl implements OrderRequestDAO {
	
	private JdbcTemplate temp ;
	
	
	
	public OrderRequestDAOImpl(DataSource ds) {
		temp = new JdbcTemplate(ds) ;
	}



	@Override
	public List<OrderRequestDTO> customerOrdersCart(Long customerId , Integer orderStatus) {
		
		String sql = "SELECT it.item_name , ord.TOTAL_PRICE , it.item_logo , ord.CURRENCY_ID , oi.QUANTITY , oi.id , oi.item_price , ord.id , ord.ORDER_NUMBER "
				+ " FROM order_items oi , orders ord , customers cu , items it "
				+ " WHERE oi.ORDER_ID = ord.ID "
				+ " AND ord.CUSTOMER_ID = cu.ID "
				+ " AND oi.ITEM_ID = it.id "
				+ " AND ord.ORDER_STATUS_ID = ? " 
				+ " AND ord.CUSTOMER_ID = ? " ;
		
		return temp.query(sql , new Object[] {customerId , orderStatus} , new OrderRequestMapper()) ;
	}



	@Override
	public GeneralDTO generateOrderNumber() {
		String sql = "SELECT ifnull(max(ORDER_NUMBER) , 1) , null , null , null "
				+ " FROM orders "
				+ " WHERE year(sysdate()) = ? " ;
		return temp.queryForObject(sql , new Object[] {new Date().getYear() + 1900} , new GeneralMapper()) ;
	}
	
}
