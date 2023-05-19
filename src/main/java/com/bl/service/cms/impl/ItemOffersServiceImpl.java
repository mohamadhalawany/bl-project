package com.bl.service.cms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dao.impl.cms.OffersDAO;
import com.bl.dto.ItemsDTO;
import com.bl.dto.cms.ItemOffersDTO;
import com.bl.dto.cms.OffersDTO;
import com.bl.entity.cms.ItemOffersEntity;
import com.bl.repository.cms.ItemsOfferRepository;
import com.bl.service.ItemService;
import com.bl.service.cms.ItemOffersService;
import com.bl.service.cms.OffersService;

@Service
public class ItemOffersServiceImpl implements ItemOffersService {
	
	@Autowired
	private ItemsOfferRepository repo ;
	@Autowired
	private OffersService offerService ;
	@Autowired
	private OffersDAO dao ;
	@Autowired
	private ItemService itemService ;
	
	@Override
	public List<ItemOffersDTO> findAllByItemId(Long itemId) {
		List<ItemOffersDTO> list = null ;
		List<ItemOffersEntity> entityList = repo.findAllByItemId(itemId) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<ItemOffersDTO>() ;
			for(ItemOffersEntity entity : entityList) {
				ItemOffersDTO dto = HelperUtils.convertEntityToDto(entity , ItemOffersDTO.class) ;
				OffersDTO offer = offerService.findById(dto.getOfferId()) ;
				if(offer != null) {
					dto.setOfferName(offer.getOfferName()) ;
					dto.setIsPercent(offer.getIsPercent()) ;
					dto.setOfferValue(offer.getOfferValue()) ;
					dto.setExpireDate(offer.getValidTo()) ;
				}
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public List<OffersDTO> availableOffers(Long itemId) {
		List<OffersDTO> list = dao.availableOffers(itemId) ;		
		return list ;
	}

	@Override
	public int save(ItemOffersDTO dto) {
		int saved = 0 ;
		if(dto != null) {
			ItemsDTO item = itemService.findById(dto.getItemId()) ;
			if(item != null) {
				item.setUpdatedBy(dto.getCreatedBy()) ;
				item.setUpdatedDate(new Date()) ;
				item.setItemPriceOffer(dto.getItemPriceTemp()) ;
				item.setItemLogo(dto.getItemLogo() == null ? item.getItemLogo() : dto.getItemLogo()) ;
				itemService.save(item) ;
				
				ItemOffersEntity entity = HelperUtils.convertDtoToEntity(dto , ItemOffersEntity.class) ;
				entity = repo.save(entity) ;
				if(entity != null && entity.getId() > 0) {
					saved = 1 ;
				}
			}
		}
		return saved ;
	}

	@Override
	public int delete(Integer id , Integer updatedBy) {
		Optional<ItemOffersEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			ItemOffersEntity entity = opt.get() ;
			ItemsDTO item = itemService.findById(entity.getItemId()) ; 
			item.setItemPriceOffer(0.0) ;
			item.setUpdatedDate(new Date()) ;
			item.setUpdatedBy(updatedBy) ;
			
			itemService.save(item) ;
			repo.delete(entity) ;
			
			return 0 ;
		}else {
			return 1 ;
		}
	}

	@Override
	public Long countByItemId(Long itemId) {
		Long count = repo.countByItemId(itemId) ;
		return count ;
	}

	@Override
	public Integer findMaxOfferIdByItemId(Long itemId) {
		Integer max = repo.findMaxOfferIdByItemId(itemId) ;
		return max ;
	}

	@Override
	public Integer findMaxIdByItemId(Long itemId) {
		Integer max = repo.findMaxIdByItemId(itemId) ;
		return max ;
	}

	@Override
	public ItemOffersDTO findById(Integer id) {
		ItemOffersDTO dto = null ;
		Optional<ItemOffersEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			ItemOffersEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity , ItemOffersDTO.class) ;
		}		
		return dto ;
	}

}
