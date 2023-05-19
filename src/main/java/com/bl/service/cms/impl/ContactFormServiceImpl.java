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
import com.bl.dto.cms.ContactFormDTO;
import com.bl.entity.cms.ContactFormEntity;
import com.bl.repository.cms.ContactFormRepository;
import com.bl.service.cms.ContactFormService;

@Service
public class ContactFormServiceImpl implements ContactFormService {

	@Autowired
	private ContactFormRepository repo ;
	
	private Page<ContactFormEntity> page ;
	private List<ContactFormEntity> entityList ;
	
	@Override
	public ContactFormDTO findById(Integer id) {
		ContactFormDTO dto = null ;
		Optional<ContactFormEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty() && opt.isPresent()) {
			ContactFormEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity , ContactFormDTO.class) ;
		}
		return dto ;
	}

	@Override
	public List<ContactFormDTO> findAll() {
		List<ContactFormDTO> list = null ;
		page = repo.findAll(PageRequest.of(0 , 10 , Sort.by("id").descending())) ;
		if(page != null) {
			entityList = page.getContent() ;
			list = new ArrayList<ContactFormDTO>() ;
			for(ContactFormEntity entity : entityList) {
				ContactFormDTO dto = HelperUtils.convertEntityToDto(entity , ContactFormDTO.class) ;
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
	public List<ContactFormDTO> next() {
		List<ContactFormDTO> list = null ;
		if(page.hasNext()) {
			page = repo.findAll(page.nextPageable()) ;
			entityList = page.getContent() ;
			list = new ArrayList<ContactFormDTO>() ;
			for(ContactFormEntity entity : entityList) {
				ContactFormDTO dto = HelperUtils.convertEntityToDto(entity , ContactFormDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public List<ContactFormDTO> previous() {
		List<ContactFormDTO> list = null ;
		if(page.hasPrevious()) {
			page = repo.findAll(page.previousPageable()) ;
			entityList = page.getContent() ;
			list = new ArrayList<ContactFormDTO>() ;
			for(ContactFormEntity entity : entityList) {
				ContactFormDTO dto = HelperUtils.convertEntityToDto(entity , ContactFormDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public void delete(Integer id) {
		repo.deleteById(id) ;// TRY IT BECAUSE THIS IS THE FIRST TIME I USE IT
	}

	@Override
	public void save(ContactFormDTO dto) {
		ContactFormEntity entity = HelperUtils.convertDtoToEntity(dto , ContactFormEntity.class) ;
		repo.save(entity) ;
	}

	public Page<ContactFormEntity> getPage() {
		return page;
	}
	public void setPage(Page<ContactFormEntity> page) {
		this.page = page;
	}

	public List<ContactFormEntity> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<ContactFormEntity> entityList) {
		this.entityList = entityList;
	}

	

}
