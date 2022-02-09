package com.bl.service;

import java.util.List;
import java.util.Map;

import com.bl.dto.CustomerReviewsDTO;

public interface CustomerReviewsService {
	
	public List<CustomerReviewsDTO> findAllByItemId(int numPage , Long itemId) ;
	public List<CustomerReviewsDTO> findAllByItemIdNext() ;
	public List<CustomerReviewsDTO> findAllByItemIdPrevious() ;
	public Long save(CustomerReviewsDTO dto) ;
	public Map<String, Object> metaData() ;
}
