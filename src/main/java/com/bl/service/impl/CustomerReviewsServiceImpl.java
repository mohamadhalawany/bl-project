package com.bl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dto.CustomerReviewsDTO;
import com.bl.entity.CustomerReviewsEntity;
import com.bl.repository.CustomerReviewsRepository;
import com.bl.service.CustomerReviewsService;

@Service
public class CustomerReviewsServiceImpl implements CustomerReviewsService {

	@Autowired
	private CustomerReviewsRepository repo ;
	
	Page<CustomerReviewsEntity> page ;
	List<CustomerReviewsEntity> entityList ;
	
	@Override
	public List<CustomerReviewsDTO> findAllByItemId(int numPage , Long itemId) {
		List<CustomerReviewsDTO> list = null ;
		Pageable paging = PageRequest.of(numPage , 5) ;
		page = repo.findAllByItemId(paging , itemId) ;
		entityList = page.getContent() ;
		
		if(entityList != null && entityList.size() > 0 && !entityList.isEmpty()) {
			list = new ArrayList<CustomerReviewsDTO>() ;
			for(CustomerReviewsEntity entity : entityList) {
				CustomerReviewsDTO dto = HelperUtils.convertEntityToDto(entity , CustomerReviewsDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	@Override
	public List<CustomerReviewsDTO> findAllByItemIdNext() {
		List<CustomerReviewsDTO> list = new ArrayList<CustomerReviewsDTO>() ;
		if(page.hasNext()) {
			page = repo.findAll(page.nextPageable()) ;
			entityList = page.getContent() ;
			if(entityList != null) {
				for(CustomerReviewsEntity entity : entityList) {
					CustomerReviewsDTO dto = HelperUtils.convertEntityToDto(entity , CustomerReviewsDTO.class) ;
					list.add(dto) ;
				}
			}
		}
		return list ;
	}

	@Override
	public List<CustomerReviewsDTO> findAllByItemIdPrevious() {
		List<CustomerReviewsDTO> list = new ArrayList<CustomerReviewsDTO>() ;
		if(page.hasPrevious()) {
			page = repo.findAll(page.previousPageable()) ;
			entityList = page.getContent() ;
			if(entityList != null) {
				for(CustomerReviewsEntity entity : entityList) {
					CustomerReviewsDTO dto = HelperUtils.convertEntityToDto(entity , CustomerReviewsDTO.class) ;
					list.add(dto) ;
				}
			}
		}
		return list ;
	}

	@Override
	public Map<String, Object> metaData() {
		Map<String, Object> metaData = new HashMap<String, Object>() ;
		if(page != null) {
			metaData.put("isFirst", page.isFirst());
		    metaData.put("isLast", page.isLast());
		}
		return metaData ;
	}
	
	
	
	@Override
	public Long save(CustomerReviewsDTO dto) {
		CustomerReviewsEntity entity = HelperUtils.convertDtoToEntity(dto , CustomerReviewsEntity.class) ;
		entity = repo.save(entity) ;
		
		return entity.getId() ;
	}
	
	

	public Page<CustomerReviewsEntity> getPage() {
		return page;
	}

	public void setPage(Page<CustomerReviewsEntity> page) {
		this.page = page;
	}

	public List<CustomerReviewsEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<CustomerReviewsEntity> entityList) {
		this.entityList = entityList;
	}
}
