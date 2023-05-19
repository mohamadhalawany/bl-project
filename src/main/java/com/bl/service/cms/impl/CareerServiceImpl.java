package com.bl.service.cms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dto.cms.CareerDTO;
import com.bl.entity.cms.CareerEntity;
import com.bl.repository.cms.CareerRepository;
import com.bl.service.cms.CareerService;


@Service
public class CareerServiceImpl implements CareerService {
	
	@Autowired
	private CareerRepository repo ;
	
	private Page<CareerEntity> page ;
	private List<CareerEntity> entityList ;
	
	
	@Override
	public List<CareerDTO> findAll() {
		List<CareerDTO> list = null ;
		page = repo.findAll(PageRequest.of(0 , 10 , Sort.by("id").descending())) ;
		if(page != null && !page.isEmpty()) {
			list = new ArrayList<CareerDTO>() ;
			entityList = page.getContent() ;
			for(CareerEntity entity : entityList) {
				CareerDTO dto = HelperUtils.convertEntityToDto(entity , CareerDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public List<CareerDTO> next() {
		List<CareerDTO> list = null ;
		if(page.hasContent() && page.hasNext()) {
			list = new ArrayList<CareerDTO>() ;
			page = repo.findAll(page.nextPageable()) ;
			entityList = page.getContent() ;
			for(CareerEntity entity : entityList) {
				CareerDTO dto = HelperUtils.convertEntityToDto(entity , CareerDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public List<CareerDTO> previous() {
		List<CareerDTO> list = null ;
		if(page.hasContent() && page.hasPrevious()) {
			list = new ArrayList<CareerDTO>() ;
			page = repo.findAll(page.previousPageable()) ;
			entityList = page.getContent() ;
			for(CareerEntity entity : entityList) {
				CareerDTO dto = HelperUtils.convertEntityToDto(entity , CareerDTO.class) ;
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
	public CareerDTO findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(CareerDTO dto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}
	
	
	
	

	public Page<CareerEntity> getPage() {
		return page;
	}
	public void setPage(Page<CareerEntity> page) {
		this.page = page;
	}

	public List<CareerEntity> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<CareerEntity> entityList) {
		this.entityList = entityList;
	}

}
