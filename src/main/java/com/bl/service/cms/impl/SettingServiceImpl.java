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
import com.bl.dto.CategoryDTO;
import com.bl.dto.GeneralDTO;
import com.bl.dto.ItemsDTO;
import com.bl.dto.cms.CompanyInfoDTO;
import com.bl.dto.cms.CompanyTermsConditionDTO;
import com.bl.dto.cms.LoginUserDTO;
import com.bl.dto.cms.SettingDTO;
import com.bl.entity.ItemEntity;
import com.bl.entity.cms.CompanyTermsConditionEntity;
import com.bl.entity.cms.SettingEntity;
import com.bl.repository.ItemRepository;
import com.bl.repository.cms.CompanyTermsConditionRepository;
import com.bl.repository.cms.SettingRepository;
import com.bl.service.GeneralService;
import com.bl.service.cms.CategoryService;
import com.bl.service.cms.CompanyInfoService;
import com.bl.service.cms.LoginUserService;
import com.bl.service.cms.SettingService;



@Service
public class SettingServiceImpl implements SettingService {

	@Autowired
	private SettingRepository settingRepo ;	
	@Autowired
	private ItemRepository itemRepo ;	
	@Autowired
	private CategoryService categoryService ;	
	@Autowired
	private GeneralService generalService ;
	@Autowired
	private CompanyTermsConditionRepository companyTermsConditionRepository ;
	@Autowired
	private CompanyInfoService companyInfoService ;
	@Autowired
	private LoginUserService loginUserService ;
	
	
	private Page<ItemEntity> itemEntityPage ;
	private List<ItemEntity> itemEntityList ;
	private Map<String, Object> metaData ;
	
	
	@Override
	public Integer save(SettingDTO dto) {
		SettingEntity entity = HelperUtils.convertDtoToEntity(dto , SettingEntity.class) ;
		entity = settingRepo.save(entity) ;
		return entity.getId() ;
	}

	@Override
	public List<ItemsDTO> itemsShowList() {
		List<ItemsDTO> list = null ;
		itemEntityPage = itemRepo.findAllByIsHidden(0 , PageRequest.of(0 , 5)) ;
		if(itemEntityPage != null && !itemEntityPage.isEmpty()) {
			itemEntityList = itemEntityPage.getContent() ;
			if(itemEntityList != null && !itemEntityList.isEmpty()) {
				list = new ArrayList<ItemsDTO>() ;
				for(ItemEntity entity : itemEntityList) {
					ItemsDTO dto = HelperUtils.convertEntityToDto(entity , ItemsDTO.class) ;
					
					if(dto != null) {
						CategoryDTO category = categoryService.findById(dto.getCategoryId()) ;
						dto.setCategoryName(category.getCategoryName()) ;
					}
					
					GeneralDTO currency = generalService.currencyById(dto.getCurrencyId()) ;
					if(currency != null) {
						dto.setCurrencyName(currency.getValueEn());
						dto.setCurrencyNameAr(currency.getValueAr()) ;
						dto.setInternationalCode(currency.getIsoName()) ;
					}
					
					list.add(dto) ;
				}
			}
		}
		return list ;
	}

	
	
	@Override
	public List<ItemsDTO> next() {
		List<ItemsDTO> list = null ;
		if(itemEntityPage.hasNext()) {
			list = new ArrayList<ItemsDTO>() ;
			itemEntityPage = itemRepo.findAllByIsHidden(0 , itemEntityPage.nextPageable());
			itemEntityList = itemEntityPage.getContent();
			
			for(ItemEntity entity : itemEntityList) {
				ItemsDTO dto = HelperUtils.convertEntityToDto(entity, ItemsDTO.class) ;
				
				CategoryDTO category = categoryService.findById(dto.getCategoryId()) ;
				if(category != null) {
					dto.setCategoryName(category.getCategoryName()) ;
				}				
				
				GeneralDTO currency = generalService.currencyById(dto.getCurrencyId()) ;
				if(currency != null) {
					dto.setCurrencyName(currency.getValueEn());
					dto.setCurrencyNameAr(currency.getValueAr()) ;
					dto.setInternationalCode(currency.getIsoName()) ;
				}
				
				list.add(dto);
			}
		}
		return list ;
	}

	@Override
	public List<ItemsDTO> previous() {
		List<ItemsDTO> list = null ;
		if(itemEntityPage.hasPrevious()) {
			list = new ArrayList<ItemsDTO>() ;
			itemEntityPage = itemRepo.findAllByIsHidden(0 , itemEntityPage.previousPageable());
			itemEntityList = itemEntityPage.getContent();
			
			for(ItemEntity entity : itemEntityList) {
				ItemsDTO dto = HelperUtils.convertEntityToDto(entity, ItemsDTO.class) ;
				
				CategoryDTO category = categoryService.findById(dto.getCategoryId()) ;
				if(category != null) {
					dto.setCategoryName(category.getCategoryName()) ;
				}				
				
				GeneralDTO currency = generalService.currencyById(dto.getCurrencyId()) ;
				if(currency != null) {
					dto.setCurrencyName(currency.getValueEn());
					dto.setCurrencyNameAr(currency.getValueAr()) ;
					dto.setInternationalCode(currency.getIsoName()) ;
				}
				
				list.add(dto);
			}
		}
		return list ;
	}

	@Override
	public Map<String, Object> metaData() {
		metaData = new HashMap<String, Object>() ;
		if(itemEntityPage != null) {
		    metaData.put("currentPage", itemEntityPage.getNumber() + 1);
		    metaData.put("total", itemEntityPage.getTotalElements());
		    metaData.put("totalPages", itemEntityPage.getTotalPages());
		    metaData.put("isFirst", itemEntityPage.isFirst());
		    metaData.put("isLast", itemEntityPage.isLast());
		}
		
		return metaData;
	}
	
	
	
	
	@Override
	public SettingDTO findById(Integer id) {
		SettingDTO dto = null ;
		Optional<SettingEntity> opt = settingRepo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			SettingEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity , SettingDTO.class) ;
		}else {
			dto = new SettingDTO() ;
		}
		return dto ;
	}

		
	@Override
	public List<SettingDTO> findAllForSlice() {
		List<SettingDTO> list = null ;
		List<SettingEntity> entityList = settingRepo.findAllForSlice() ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<SettingDTO>() ;
			for(SettingEntity entity : entityList) {
				SettingDTO dto = HelperUtils.convertEntityToDto(entity , SettingDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	
	@Override
	public int delete(Integer id) {
		Optional<SettingEntity> opt = settingRepo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			SettingEntity entity = opt.get() ;
			settingRepo.delete(entity);
			return 0 ;
		}else {
			return 1 ;
		}
	}

	@Override
	public CompanyTermsConditionDTO findTermsConditionByCompanyId(Integer companyId) {
		CompanyTermsConditionDTO dto = null ;
		CompanyTermsConditionEntity entity = companyTermsConditionRepository.findByCompanyId(companyId) ;
		if(entity != null) {
			dto = HelperUtils.convertEntityToDto(entity , CompanyTermsConditionDTO.class) ;
			if(dto != null && dto.getCompanyId() != null) {
				CompanyInfoDTO company = companyInfoService.findById(dto.getCompanyId()) ;
				if(company != null) {
					dto.setCompanyName(company.getCompanyName()) ;
				}
			}
			if(dto != null && dto.getUpdatedBy() != null) {
				LoginUserDTO user = loginUserService.findById(dto.getUpdatedBy()) ;
				dto.setUpdatedByName(user.getFullName()) ;
			}
		}
		return dto ;
	}

	@Override
	public Integer saveTermsCondition(CompanyTermsConditionDTO dto) {
		CompanyTermsConditionEntity entity = HelperUtils.convertDtoToEntity(dto , CompanyTermsConditionEntity.class) ;
		companyTermsConditionRepository.save(entity) ;
		return entity.getId() ;
	}

	public List<ItemEntity> getItemEntityList() {
		return itemEntityList;
	}

	public void setItemEntityList(List<ItemEntity> itemEntityList) {
		this.itemEntityList = itemEntityList;
	}

	public Map<String, Object> getMetaData() {
		return metaData;
	}

	public void setMetaData(Map<String, Object> metaData) {
		this.metaData = metaData;
	}
}
