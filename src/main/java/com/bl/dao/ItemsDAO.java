package com.bl.dao;

import java.util.List;

import com.bl.dto.ItemsDTO;

public interface ItemsDAO {
	
	public List<ItemsDTO> mostPopular() ;
	public List<ItemsDTO> lastAdded() ;
}
