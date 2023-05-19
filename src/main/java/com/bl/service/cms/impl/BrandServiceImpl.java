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
import com.bl.dto.cms.BrandDTO;
import com.bl.entity.cms.BrandEntity;
import com.bl.repository.cms.BrandRepository;
import com.bl.service.cms.BrandService;
import com.bl.service.cms.ItemBrandService;


@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandRepository repo ;
	@Autowired
	private ItemBrandService itemBrandService ;
	

	private Page<BrandEntity> page ;
	private List<BrandEntity> entityList ;
	
	
	@Override
	public List<BrandDTO> findAll() {
		List<BrandDTO> list = null ;
		page = repo.findAll(PageRequest.of(0 , 5 , Sort.by("id").descending())) ;
		if(page != null && !page.isEmpty()) {
			entityList = page.getContent() ;
			list = new ArrayList<BrandDTO>() ;
			for(BrandEntity entity : entityList) {
				BrandDTO dto = HelperUtils.convertEntityToDto(entity , BrandDTO.class) ;				
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public List<BrandDTO> next() {
		List<BrandDTO> list = null ;
		if(page.hasNext()) {
			list = new ArrayList<BrandDTO>() ;
			page = repo.findAll(page.nextPageable()) ;
			entityList = page.getContent() ;
			for(BrandEntity entity : entityList) {
				BrandDTO dto = HelperUtils.convertEntityToDto(entity , BrandDTO.class) ;				
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public List<BrandDTO> previous() {
		List<BrandDTO> list = null ;
		if(page.hasPrevious()) {
			list = new ArrayList<BrandDTO>() ;
			page = repo.findAll(page.previousPageable()) ;
			entityList = page.getContent() ;
			for(BrandEntity entity : entityList) {
				BrandDTO dto = HelperUtils.convertEntityToDto(entity , BrandDTO.class) ;				
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
	public BrandDTO findById(Integer id) {
		BrandDTO dto = null ;
		Optional<BrandEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			BrandEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity , BrandDTO.class) ;
		}
		return dto ;
	}

	@Override
	public Integer save(BrandDTO dto) {
		BrandEntity entity = HelperUtils.convertDtoToEntity(dto , BrandEntity.class) ;
		entity = repo.save(entity) ;
		return entity.getId() ;
	}

	@Override
	public int delete(Integer id) {		
		Long count = itemBrandService.countItemsByBrandId(id) ;
		if(count == 0) {
			Optional<BrandEntity> opt = repo.findById(id) ;
			if(opt != null && !opt.isEmpty()) {
				BrandEntity entity = opt.get() ;
				repo.delete(entity) ;
			}
			return 0 ;
		}else {
			return 1 ;
		}
	}

	@Override
	public List<BrandDTO> findAllIsActive() {
		List<BrandDTO> list = null ;
		List<BrandEntity> entityList = repo.findAllByIsActive(1) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<BrandDTO>() ;
			for(BrandEntity entity : entityList) {
				BrandDTO dto = HelperUtils.convertEntityToDto(entity, BrandDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	public Page<BrandEntity> getPage() {
		return page;
	}

	public void setPage(Page<BrandEntity> page) {
		this.page = page;
	}

	public List<BrandEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<BrandEntity> entityList) {
		this.entityList = entityList;
	}
	
}
