package com.bl.service.cms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dto.ItemsDTO;
import com.bl.dto.cms.ItemBrandDTO;
import com.bl.dto.cms.ItemOffersDTO;
import com.bl.dto.cms.OffersDTO;
import com.bl.entity.cms.ItemBrandEntity;
import com.bl.repository.cms.ItemBrandRepository;
import com.bl.service.ItemService;
import com.bl.service.cms.ItemBrandService;
import com.bl.service.cms.ItemOffersService;
import com.bl.service.cms.OffersService;

@Service
public class ItemBrandServiceImpl implements ItemBrandService {

	@Autowired
	private ItemBrandRepository repo ;
	@Autowired
	private ItemService itemService ;
	@Autowired
	private OffersService offersService ;
	@Autowired
	private ItemOffersService itemOffersService ;
	
	@Override
	public List<ItemBrandDTO> findAllByItemId(Long itemId) {
		List<ItemBrandDTO> list = null ;
		List<ItemBrandEntity> entityList = repo.findAllByItemIdOrderByIdDesc(itemId) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<ItemBrandDTO>() ;
			for(ItemBrandEntity entity : entityList) {
				ItemBrandDTO dto = HelperUtils.convertEntityToDto(entity, ItemBrandDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public Long countItemsByBrandId(Integer brandId) {
		Long count = repo.countByBrandId(brandId) ;
		return count ;
	}

	@Override
	public ItemBrandDTO findById(Integer id) {
		ItemBrandDTO dto = null ;
		Optional<ItemBrandEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			ItemBrandEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity , ItemBrandDTO.class) ;
		}
		return dto ;
	}

	@Override
	public int save(ItemBrandDTO dto) {
		Long countByBrandIdAndItemId = repo.countByBrandIdAndItemId(dto.getBrandId() , dto.getItemId()) ;
		if(countByBrandIdAndItemId > 0) {
			return 0 ;
		}else {
			ItemBrandEntity entity = HelperUtils.convertDtoToEntity(dto , ItemBrandEntity.class) ;
			entity = repo.save(entity) ;
			return 1 ;
		}
	}

	@Override
	public int delete(Integer id) {
		Optional<ItemBrandEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			ItemBrandEntity entity = opt.get() ;
			repo.delete(entity) ;
			return 0 ;
		}else {
		return 1 ;
		}
	}

	@Override
	public List<ItemsDTO> findAllByBrandId(Integer brandId) {
		List<ItemsDTO> list = null ;
		List<ItemBrandEntity> entityList = repo.findAllByBrandId(brandId) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<ItemsDTO>() ;
			for(ItemBrandEntity entity : entityList) {
				ItemBrandDTO dto = HelperUtils.convertEntityToDto(entity, ItemBrandDTO.class) ;
				if(dto != null) {
					ItemsDTO item = itemService.findById(dto.getItemId()) ;
					Integer itemOfferId = itemOffersService.findMaxIdByItemId(item.getId()) ;
					if(itemOfferId != null) {
						ItemOffersDTO itemOffer = itemOffersService.findById(itemOfferId) ;
						if(itemOffer != null) {
							// assign offerId , then get validTo and compare before now or not
							OffersDTO offer = offersService.findById(itemOffer.getOfferId()) ;
							if(offer != null && offer.getIsActive() == 1) {
								String toDay = (new Date().getYear() + 1900) + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate() ;
								String validTo = (offer.getValidTo().getYear() + 1900) + "-" + (offer.getValidTo().getMonth() + 1) + "-" + offer.getValidTo().getDate() ;
								Integer isValid = HelperUtils.dateBeforeDate(validTo, toDay) ;
								if(isValid == 1) {
									Integer isPercent = offer.getIsPercent() == null ? 0 : offer.getIsPercent() ;
									Double itemPriceOffer = 0.0 ;
									Double offerValue = offer.getOfferValue() ;
									Double itemPrice = item.getItemPrice() ;
									item.setOfferValidity(0) ;
									
									if(isPercent == 1) {							
										itemPriceOffer = (itemPrice * offerValue) / 100 ;
										item.setItemPriceOffer(itemPrice - itemPriceOffer) ;
									}else {
										itemPriceOffer = itemPrice - offerValue ;
									}
								}								
							}
						}
					}
					list.add(item) ;
				}
			}
		}
		return list ;
	}
	
	

}
