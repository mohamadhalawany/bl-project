package com.bl.service;

import java.util.List;
import java.util.Map;

import com.bl.dto.CustomersDTO;
import com.bl.dto.OrderDTO;
import com.bl.dto.OrderItemDTO;
import com.bl.dto.OrderStatusDTO;

public interface CustomersService {

	public CustomersDTO findByEmailAndPassword(String email , String password) ;
	public CustomersDTO findById(Long id) ;
	public Long save(CustomersDTO dto) ;
	public Long countByEmail(String email) ;
	public Long saveOrder(OrderDTO order , OrderItemDTO orderItem , OrderStatusDTO orderStatus) ;
	public List<CustomersDTO>  findAll(int language) ;
	public List<CustomersDTO> next(int language) ;
	public List<CustomersDTO> previous(int language) ;
	public List<CustomersDTO> search(CustomersDTO dto , int language) ;
	
	public List<CustomersDTO> blocked(int language) ;
	public List<CustomersDTO> nextBlocked(int language) ;
	public List<CustomersDTO> previousBlocked(int language) ;
	
	public Map<String, Object> metaData() ;
	public Map<String, Object> metaDataBlocked() ;
}
