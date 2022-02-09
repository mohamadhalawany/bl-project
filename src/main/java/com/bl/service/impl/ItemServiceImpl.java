package com.bl.service.impl;

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
import com.bl.dto.GeneralDTO;
import com.bl.dto.ItemsDTO;
import com.bl.entity.ItemEntity;
import com.bl.repository.ItemRepository;
import com.bl.service.GeneralService;
import com.bl.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemRepository repo ;
	
	@Autowired
	private GeneralService generalService ;
		
	private Page< ItemEntity> itemPage ;
	private List<ItemEntity> itemEntityList ;
	
	@Override
	public List<ItemsDTO> findAll() {
		List<ItemsDTO> list = null ;
		itemPage = repo.findAll(PageRequest.of(0, 4 , Sort.by(Sort.Direction.DESC , "id"))) ;
		itemEntityList = itemPage.getContent() ;
		
		if(itemEntityList != null && itemEntityList.size() > 0 && !itemEntityList.isEmpty()) {
			list = new ArrayList<ItemsDTO>() ;
			for(ItemEntity entity : itemEntityList) {				
				ItemsDTO dto = HelperUtils.convertEntityToDto(entity , ItemsDTO.class) ;				
				GeneralDTO currency = generalService.currencyById(dto.getCurrencyId()) ;
				dto.setCurrencyName(currency.getValueEn());
				dto.setCurrencyNameAr(currency.getValueAr()) ;
				dto.setInternationalCode(currency.getIsoName()) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public List<ItemsDTO> findAllItemsNextPage() {
		List<ItemsDTO> list = new ArrayList<ItemsDTO>();

		if (itemPage.hasNext()) {
			itemPage = repo.findAll(itemPage.nextPageable());
			itemEntityList = itemPage.getContent();

		    for (ItemEntity entity : itemEntityList) {
			ItemsDTO dto = HelperUtils.convertEntityToDto(entity, ItemsDTO.class) ;
			GeneralDTO currency = generalService.currencyById(dto.getCurrencyId()) ;
			dto.setCurrencyName(currency.getValueEn());
			dto.setCurrencyNameAr(currency.getValueAr()) ;
			dto.setInternationalCode(currency.getIsoName()) ;
			list.add(dto);
		    }
		}
		return list;
	}

	
	@Override
	public List<ItemsDTO> findAllItemsPreviousPage() {
		List<ItemsDTO> list = new ArrayList<ItemsDTO>();

		if (itemPage.hasNext()) {
			itemPage = repo.findAll(itemPage.previousPageable());
			itemEntityList = itemPage.getContent();

		    for (ItemEntity entity : itemEntityList) {
			ItemsDTO dto = HelperUtils.convertEntityToDto(entity, ItemsDTO.class) ;
			GeneralDTO currency = generalService.currencyById(dto.getCurrencyId()) ;
			dto.setCurrencyName(currency.getValueEn());
			dto.setCurrencyNameAr(currency.getValueAr()) ;
			dto.setInternationalCode(currency.getIsoName()) ;
			list.add(dto);
		    }
		}
		return list;
	}
	
	
	
	@Override
	public Map<String, Object> metaData() {
		Map<String, Object> metaData = new HashMap<String, Object>() ;
		if(itemPage != null) {
		    metaData.put("currentPage", itemPage.getNumber() + 1);
		    metaData.put("totalItems", itemPage.getTotalElements());
		    metaData.put("totalPages", itemPage.getTotalPages());
		    metaData.put("isFirst", itemPage.isFirst());
		    metaData.put("isLast", itemPage.isLast());
		}
		
		return metaData;
	}

	@Override
	public ItemsDTO findById(Long id) {
		ItemsDTO dto = null ;
		ItemEntity entity = repo.findById(id).get() ;
		if(entity != null) {
			dto = HelperUtils.convertEntityToDto(entity, ItemsDTO.class) ;
			GeneralDTO currency = generalService.currencyById(dto.getCurrencyId()) ;
			dto.setCurrencyName(currency.getValueEn());
			dto.setCurrencyNameAr(currency.getValueAr()) ;
			dto.setInternationalCode(currency.getIsoName()) ;
		}
		return dto ;
	}

	@Override
	public List<ItemsDTO> findAllByCategoryIdAndNotEqualId(Long id, Long categoryId) {
		List<ItemsDTO> list = new ArrayList<ItemsDTO>() ;
		List<ItemEntity> entityList = repo.findAllByCategoryIdAndNotEqualId(id , categoryId) ;
		if(entityList != null && entityList.size() > 0 && !entityList.isEmpty()) {			
			for(ItemEntity entity : entityList) {
				ItemsDTO dto = HelperUtils.convertEntityToDto(entity , ItemsDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	public Page<ItemEntity> getItemPage() {
		return itemPage;
	}

	public void setItemPage(Page<ItemEntity> itemPage) {
		this.itemPage = itemPage;
	}

	public List<ItemEntity> getItemEntityList() {
		return itemEntityList;
	}

	public void setItemEntityList(List<ItemEntity> itemEntityList) {
		this.itemEntityList = itemEntityList;
	}
}
