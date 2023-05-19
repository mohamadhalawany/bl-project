package com.bl.service.cms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dto.cms.FaqDTO;
import com.bl.entity.cms.FaqEntity;
import com.bl.repository.cms.FaqRepository;
import com.bl.service.cms.FaqService;

@Service
public class FaqServiceImpl implements FaqService {

	@Autowired
	private FaqRepository repo ;
	
	private Page<FaqEntity> page ;
	private List<FaqEntity> entityList ;
	
	@Override
	public FaqDTO findById(Integer id) {
		FaqDTO dto = null ;
		Optional<FaqEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			FaqEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity , FaqDTO.class) ;
		}
		return dto ;
	}

	@Override
	public List<FaqDTO> findAll() {
		List<FaqDTO> list = null ;
		page = repo.findAll(PageRequest.of(0 , 10)) ;
		if(page != null && !page.isEmpty()) {
			entityList = page.getContent() ;
			if(entityList != null && !entityList.isEmpty()) {
				list = new ArrayList<FaqDTO>() ;
				for(FaqEntity entity : entityList) {
					FaqDTO dto = HelperUtils.convertEntityToDto(entity , FaqDTO.class) ;
					list.add(dto) ;
				}
			}
		}
		return list ;
	}

	@Override
	public List<FaqDTO> next() {
		List<FaqDTO> list = null ;
		if(page.hasNext()) {
			list = new ArrayList<FaqDTO>() ;
			page = repo.findAll(page.nextPageable()) ;
			entityList = page.getContent() ;
			for(FaqEntity entity : entityList) {
				FaqDTO dto = HelperUtils.convertEntityToDto(entity , FaqDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public List<FaqDTO> previous() {
		List<FaqDTO> list = null ;
		if(page.hasPrevious()) {
			list = new ArrayList<FaqDTO>() ;
			page = repo.findAll(page.previousPageable()) ;
			entityList = page.getContent() ;
			for(FaqEntity entity : entityList) {
				FaqDTO dto = HelperUtils.convertEntityToDto(entity , FaqDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	@Override
	public Map<String, Object> metaData() {
		Map<String, Object> metaData = new HashMap<String, Object>() ;
		if(page != null) {
			int currentPage = 0 ;
			if(page.getNumber() > 0 || page.getTotalElements() > 0) {
				currentPage = page.getNumber() + 1 ;
			}
			
		    metaData.put("currentPage", currentPage);
		    metaData.put("total", page.getTotalElements());
		    metaData.put("totalPages", page.getTotalPages());
		    metaData.put("isFirst", page.isFirst());
		     metaData.put("isLast", page.isLast());
		}		
		return metaData;
	}

	@Override
	public void save(FaqDTO dto) {
		if(dto != null) {
			FaqEntity entity = HelperUtils.convertDtoToEntity(dto , FaqEntity.class) ;
			repo.save(entity) ;
		}
	}

	@Override
	public void delete(Integer id) {
		if(id != null) {
			Optional<FaqEntity> opt = repo.findById(id) ;
			if(opt != null && !opt.isEmpty()) {
				FaqEntity entity = opt.get() ;
				repo.delete(entity) ;
			}
		}
	}

	public Page<FaqEntity> getPage() {
		return page;
	}

	public void setPage(Page<FaqEntity> page) {
		this.page = page;
	}

	public List<FaqEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<FaqEntity> entityList) {
		this.entityList = entityList;
	}

}
