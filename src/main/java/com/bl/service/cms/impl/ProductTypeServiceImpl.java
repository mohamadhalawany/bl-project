package com.bl.service.cms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dto.cms.ProductTypeDTO;
import com.bl.entity.cms.ProductTypeEntity;
import com.bl.repository.cms.ProductTypeRepository;
import com.bl.service.cms.ItemProductTypeService;
import com.bl.service.cms.ProductTypeService;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

	@Autowired
	private ProductTypeRepository repo ;
	@Autowired
	private ItemProductTypeService itemProductTypeService ;
	
	
	private Page<ProductTypeEntity> page ;
	private List<ProductTypeEntity> entityList ;
	
	@Override
	public List<ProductTypeDTO> findAll() {
		List<ProductTypeDTO> list = null ;
		page = repo.findAll(PageRequest.of(0 , 5 , Sort.by("id").descending())) ;
		if(page != null && !page.isEmpty()) {
			list = new ArrayList<ProductTypeDTO>() ;
			entityList = page.getContent() ;
			for(ProductTypeEntity entity : entityList) {
				ProductTypeDTO dto = HelperUtils.convertEntityToDto(entity , ProductTypeDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public List<ProductTypeDTO> next() {
		List<ProductTypeDTO> list = null ;
		if(page.hasNext()) {
			list = new ArrayList<ProductTypeDTO>() ;
			page = repo.findAll(page.nextPageable()) ;
			entityList = page.getContent() ;
			for(ProductTypeEntity entity : entityList) {
				ProductTypeDTO dto = HelperUtils.convertEntityToDto(entity , ProductTypeDTO.class) ;				
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public List<ProductTypeDTO> previous() {
		List<ProductTypeDTO> list = null ;
		if(page.hasPrevious()) {
			list = new ArrayList<ProductTypeDTO>() ;
			page = repo.findAll(page.previousPageable()) ;
			entityList = page.getContent() ;
			for(ProductTypeEntity entity : entityList) {
				ProductTypeDTO dto = HelperUtils.convertEntityToDto(entity , ProductTypeDTO.class) ;				
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public Map<String, Object> metaData() {
		Map<String, Object> metaData = new HashMap<String, Object>() ;
		if(page != null) {
		    metaData.put("currentPage", page.getNumber() + 1);
		    metaData.put("total", page.getTotalElements());
		    metaData.put("totalPages", page.getTotalPages());
		    metaData.put("isFirst", page.isFirst());
		     metaData.put("isLast", page.isLast());
		}		
		return metaData;
	}

	@Override
	public ProductTypeDTO findById(Integer id) {
		ProductTypeDTO dto = null ;
		Optional<ProductTypeEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			ProductTypeEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity , ProductTypeDTO.class) ;
		}
		return dto ;
	}

	@Override
	public Integer save(ProductTypeDTO dto) {
		ProductTypeEntity entity = HelperUtils.convertDtoToEntity(dto , ProductTypeEntity.class) ;
		entity = repo.save(entity) ;
		return entity.getId() ;
	}

	@Override
	public Integer delete(Integer id) {
		Long count = itemProductTypeService.countByProductTypeId(id) ;
		if(count < 1) {
			Optional<ProductTypeEntity> opt = repo.findById(id) ;
			if(opt != null && !opt.isEmpty()) {
				ProductTypeEntity entity = opt.get() ;
				repo.delete(entity) ;
			}
			return 0 ;
		}else {
			return 1 ;
		}		
	}

	@Override
	public List<ProductTypeDTO> findAllIsActive() {
		List<ProductTypeDTO> list = null ;
		List<ProductTypeEntity> productTypeEntityList = repo.findAllByIsActive(1) ;
		if(productTypeEntityList != null && !productTypeEntityList.isEmpty()) {
			list = new ArrayList<ProductTypeDTO>() ;
			for(ProductTypeEntity entity : productTypeEntityList) {
				ProductTypeDTO dto = HelperUtils.convertEntityToDto(entity , ProductTypeDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	public Page<ProductTypeEntity> getPage() {
		return page;
	}

	public void setPage(Page<ProductTypeEntity> page) {
		this.page = page;
	}

	public List<ProductTypeEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<ProductTypeEntity> entityList) {
		this.entityList = entityList;
	}

}
