package com.bl.service.cms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dao.impl.cms.OffersDAO;
import com.bl.dto.cms.OffersDTO;
import com.bl.entity.cms.OffersEntity;
import com.bl.repository.cms.OffersRepository;
import com.bl.service.cms.OffersService;


@Service
public class OffersServiceImpl implements OffersService {

	@Autowired
	private OffersRepository repo ;
	@Autowired
	private OffersDAO dao ;
	
	
	private Page<OffersEntity> offersPage ;
	private List<OffersEntity> entityList ;
	private Map<String, Object> metaData ;
	
	
	
	
	@Override
	public List<OffersDTO> findAll() {
		List<OffersDTO> list = null ;
		offersPage = repo.findAll(PageRequest.of(0 , 5)) ;
		if(offersPage != null && !offersPage.isEmpty()) {
			list = new ArrayList<OffersDTO>() ;
			entityList = offersPage.getContent() ;
			String toDay = new Date().getYear() + 1900 + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate() ;
			for(OffersEntity entity : entityList) {
				OffersDTO dto = HelperUtils.convertEntityToDto(entity , OffersDTO.class) ;
				String validTo = entity.getValidTo().getYear() + 1900 + "-" + (entity.getValidTo().getMonth() + 1) + "-" + entity.getValidTo().getDate() ;
				dto.setExpired(HelperUtils.dateBeforeDate(validTo , toDay)) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	
	@Override
	public List<OffersDTO> next() {
		List<OffersDTO> list = null ;
		if(offersPage.hasNext()) {
			offersPage = repo.findAll(offersPage.nextPageable()) ;
			list = new ArrayList<OffersDTO>() ;
			entityList = offersPage.getContent() ;
			String toDay = new Date().getYear() + 1900 + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate() ;
			for(OffersEntity entity : entityList) {
				OffersDTO dto = HelperUtils.convertEntityToDto(entity , OffersDTO.class) ;
				String validTo = entity.getValidTo().getYear() + 1900 + "-" + (entity.getValidTo().getMonth() + 1) + "-" + entity.getValidTo().getDate() ;
				dto.setExpired(HelperUtils.dateBeforeDate(validTo , toDay)) ;
				list.add(dto) ;
			}
		}
		return list ;
	}


	@Override
	public List<OffersDTO> previous() {
		List<OffersDTO> list = null ;		
		if(offersPage.hasPrevious()) {
			offersPage = repo.findAll(offersPage.previousPageable()) ;
			list = new ArrayList<OffersDTO>() ;
			entityList = offersPage.getContent() ;
			String toDay = new Date().getYear() + 1900 + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate() ;
			for(OffersEntity entity : entityList) {
				OffersDTO dto = HelperUtils.convertEntityToDto(entity , OffersDTO.class) ;
				String validTo = entity.getValidTo().getYear() + 1900 + "-" + (entity.getValidTo().getMonth() + 1) + "-" + entity.getValidTo().getDate() ;
				dto.setExpired(HelperUtils.dateBeforeDate(validTo , toDay)) ;
				list.add(dto) ;
			}
		}
		return list ;
	}


	@Override
	public Map<String, Object> metaData() {
		Map<String, Object> metaData = new HashMap<String, Object>() ;
		if(offersPage != null) {
		    metaData.put("currentPage", offersPage.getNumber() + 1);
		    metaData.put("total", offersPage.getTotalElements());
		    metaData.put("totalPages", offersPage.getTotalPages());
		    metaData.put("isFirst", offersPage.isFirst());
		     metaData.put("isLast", offersPage.isLast());
		}		
		return metaData;
	}
	
	@Override
	public List<OffersDTO> search(OffersDTO dto) {
		List<OffersDTO> list = dao.search(dto) ;
		return list ;
	}

	@Override
	public OffersDTO findById(Integer id) {
		OffersDTO dto = null ;
		Optional<OffersEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			OffersEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity , OffersDTO.class) ;
		}
		return dto ;
	}

	@Override
	public Integer save(OffersDTO dto) {
		int saved = 0 ;
		OffersEntity entity = HelperUtils.convertDtoToEntity(dto , OffersEntity.class) ;
		
		entity = repo.save(entity) ;
		if(entity.getId() > 0) {
			saved = 1 ;
		}
		return saved ;
	}

	@Override
	public Integer saveActive(OffersDTO dto) {
		int saved = 0 ;
		OffersEntity entity = null ;
		if(dto.getId() != null) {
			OffersDTO offer = findById(dto.getId()) ;
			offer.setIsActive(dto.getIsActive()) ;
			offer.setUpdatedBy(dto.getUpdatedBy()) ;
			offer.setUpdatedDate(new Date()) ;
			entity = HelperUtils.convertDtoToEntity(offer , OffersEntity.class) ;
		}else {
			entity = HelperUtils.convertDtoToEntity(dto , OffersEntity.class) ;
		}
		
		entity = repo.save(entity) ;
		if(entity.getId() > 0) {
			saved = 1 ;
		}
		return saved ;
	}


	@Override
	public Integer delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}



	public Page<OffersEntity> getOffersPage() {
		return offersPage;
	}



	public void setOffersPage(Page<OffersEntity> offersPage) {
		this.offersPage = offersPage;
	}



	public List<OffersEntity> getEntityList() {
		return entityList;
	}



	public void setEntityList(List<OffersEntity> entityList) {
		this.entityList = entityList;
	}



	public Map<String, Object> getMetaData() {
		return metaData;
	}



	public void setMetaData(Map<String, Object> metaData) {
		this.metaData = metaData;
	}

}
