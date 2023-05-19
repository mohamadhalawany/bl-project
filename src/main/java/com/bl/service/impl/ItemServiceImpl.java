package com.bl.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.bl.dao.ItemsDAO;
import com.bl.dto.CategoryDTO;
import com.bl.dto.GeneralDTO;
import com.bl.dto.ItemsDTO;
import com.bl.dto.cms.ItemOffersDTO;
import com.bl.dto.cms.OffersDTO;
import com.bl.entity.ItemEntity;
import com.bl.entity.cms.SettingEntity;
import com.bl.repository.ItemRepository;
import com.bl.repository.cms.SettingRepository;
import com.bl.service.GeneralService;
import com.bl.service.ItemService;
import com.bl.service.cms.CategoryService;
import com.bl.service.cms.ItemOffersService;
import com.bl.service.cms.OffersService;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemRepository repo ;	
	@Autowired
	private GeneralService generalService ;	
	@Autowired
	private CategoryService categoryService ;
	@Autowired
	private ItemOffersService itemOffersService;  
	@Autowired
	private OffersService offersService ;
	@Autowired
	private ItemsDAO itemsDAO ;
	@Autowired
	private SettingRepository settingRepository ;
		
	private Page< ItemEntity> itemPage ;
	private List<ItemEntity> itemEntityList ;
	
	private Page< ItemEntity> itemSearchPage ;
	private List<ItemEntity> itemEntityListSearch ;
	
	private List<ItemsDTO> itemsList ;
	private Map<String, Object> metaData ;
	
	@Override
	public List<ItemsDTO> findAll() {
		Optional<SettingEntity> settingOptional = settingRepository.findById(5) ;
		Integer sliceItems = settingOptional.get().getSliceItems() ;
		
		itemPage = repo.findAll(PageRequest.of(0, sliceItems , Sort.by(Sort.Direction.DESC , "id"))) ;
		itemEntityList = itemPage.getContent() ;
		
		if(itemEntityList != null && itemEntityList.size() > 0 && !itemEntityList.isEmpty()) {
			itemsList = new ArrayList<ItemsDTO>() ;
			for(ItemEntity entity : itemEntityList) {				
				ItemsDTO dto = HelperUtils.convertEntityToDto(entity , ItemsDTO.class) ;			
				Integer offerId = itemOffersService.findMaxOfferIdByItemId(dto.getId()) ;
				if(offerId != null) {
					OffersDTO offersDTO = offersService.findById(offerId) ;
					if(offersDTO != null) {
						String toDay = (new Date().getYear() + 1900) + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate() ;
						String validTo = (offersDTO.getValidTo().getYear() + 1900) + "-" + (offersDTO.getValidTo().getMonth() + 1) + "-" + offersDTO.getValidTo().getDate() ;
						Integer isValid = HelperUtils.dateBeforeDate(validTo, toDay) ;
						if(offersDTO.getIsActive() == 1 && isValid == 1) {
							Integer isPercent = offersDTO.getIsPercent() == null ? 0 : offersDTO.getIsPercent() ;
							Double itemPriceOffer = 0.0 ;
							Double offerValue = offersDTO.getOfferValue() ;
							Double itemPrice = dto.getItemPrice() ;
							dto.setOfferValidity(isValid) ;
							if(isPercent == 1) {							
								itemPriceOffer = (itemPrice * offerValue) / 100 ;
								dto.setItemPriceOffer(itemPrice - itemPriceOffer) ;
							}else {
								itemPriceOffer = itemPrice - offerValue ;
								dto.setItemPriceOffer(itemPriceOffer) ;
							}
						}else {
							dto.setOfferValidity(0) ;
						}
					}
				}
				GeneralDTO currency = generalService.currencyById(dto.getCurrencyId()) ;
				if(currency != null) {
					dto.setCurrencyName(currency.getValueEn());
					dto.setCurrencyNameAr(currency.getValueAr()) ;
					dto.setInternationalCode(currency.getIsoName()) ;
				}
				
				CategoryDTO category = categoryService.findById(dto.getCategoryId()) ;
				if(category != null) {
					dto.setCategoryName(category.getCategoryName()) ;
				}
				
				itemsList.add(dto) ;
			}
		}
		return itemsList ;
	}

	@Override
	public List<ItemsDTO> findAllItemsNextPage() {
		itemsList = new ArrayList<ItemsDTO>();

		if (itemPage.hasNext()) {
			itemPage = repo.findAll(itemPage.nextPageable());
			itemEntityList = itemPage.getContent();

		    for (ItemEntity entity : itemEntityList) {
			ItemsDTO dto = HelperUtils.convertEntityToDto(entity, ItemsDTO.class) ;
			
			GeneralDTO currency = generalService.currencyById(dto.getCurrencyId()) ;
			if(currency != null) {
				dto.setCurrencyName(currency.getValueEn());
				dto.setCurrencyNameAr(currency.getValueAr()) ;
				dto.setInternationalCode(currency.getIsoName()) ;
			}
			
			CategoryDTO category = categoryService.findById(dto.getCategoryId()) ;
			if(category != null) {
				dto.setCategoryName(category.getCategoryName()) ;
			}
			
			itemsList.add(dto);
		    }
		}
		return itemsList ;
	}

	
	@Override
	public List<ItemsDTO> findAllItemsPreviousPage() {
		itemsList = new ArrayList<ItemsDTO>();

		if (itemPage.hasPrevious()) {
			itemPage = repo.findAll(itemPage.previousPageable());
			itemEntityList = itemPage.getContent();

		    for (ItemEntity entity : itemEntityList) {
				ItemsDTO dto = HelperUtils.convertEntityToDto(entity, ItemsDTO.class) ;
				
				GeneralDTO currency = generalService.currencyById(dto.getCurrencyId()) ;
				if(currency != null) {
					dto.setCurrencyName(currency.getValueEn());
					dto.setCurrencyNameAr(currency.getValueAr()) ;
					dto.setInternationalCode(currency.getIsoName()) ;
				}
				
				CategoryDTO category = categoryService.findById(dto.getCategoryId()) ;
				if(category != null) {
					dto.setCategoryName(category.getCategoryName()) ;
				}
				
				itemsList.add(dto);
		    }
		}
		return itemsList ;
	}
	
	
	
	@Override
	public Map<String, Object> metaData() {
		metaData = new HashMap<String, Object>() ;
		if(itemPage != null) {
		    metaData.put("currentPage", itemPage.getNumber() + 1);
		    metaData.put("total", itemPage.getTotalElements());
		    metaData.put("totalPages", itemPage.getTotalPages());
		    metaData.put("isFirst", itemPage.isFirst());
		    metaData.put("isLast", itemPage.isLast());
		}
		
		return metaData;
	}

	@Override
	public ItemsDTO findById(Long id) {
		ItemsDTO dto = null ;
		Optional<ItemEntity> opt = repo.findById(id) ; 		
		if(opt != null && !opt.isEmpty()) {
			ItemEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity, ItemsDTO.class) ;
			if(dto != null) {
				CategoryDTO category = categoryService.findById(dto.getCategoryId()) ;
				if(category != null) {
					dto.setParentCategoryId(category.getParentCategoryId()) ;
				}
				
				if(dto.getCurrencyId() != null) {
					GeneralDTO currency = generalService.currencyById(dto.getCurrencyId()) ;
					if(currency != null) {
						dto.setCurrencyName(currency.getValueEn());
						dto.setCurrencyNameAr(currency.getValueAr()) ;
						dto.setInternationalCode(currency.getIsoName()) ;
					}
				}else {
					dto.setCurrencyName(HelperUtils.getValueFromBundle("POUND" , 2));
					dto.setCurrencyNameAr(HelperUtils.getValueFromBundle("POUND" , 1)) ;
					dto.setInternationalCode(HelperUtils.getValueFromBundle("ISO_CURRENCY" , 2)) ;
				}
			}
		}
		return dto ;
	}

	@Override
	public List<ItemsDTO> findAllByCategoryIdAndNotEqualId(Long id, Long categoryId) {
		itemsList = new ArrayList<ItemsDTO>() ;
		List<ItemEntity> entityList = repo.findAllByCategoryIdAndNotEqualId(id , categoryId) ;
		if(entityList != null && entityList.size() > 0 && !entityList.isEmpty()) {			
			for(ItemEntity entity : entityList) {
				ItemsDTO dto = HelperUtils.convertEntityToDto(entity , ItemsDTO.class) ;
				itemsList.add(dto) ;
			}
		}
		return itemsList ;
	}
	
	@Override
	public List<ItemsDTO> findAll(ItemsDTO dto) {
		String itemName = "" ;
		String description = "" ;
		String itemCode = "" ;
		Long categoryId = null ;
		Long parentCategoryId = null ;
		
		if(dto != null) {
			if(dto.getParentCategoryId() != null && dto.getParentCategoryId() != 0L) {
				parentCategoryId = dto.getParentCategoryId() ;
			}
			
			if(dto.getCategoryId() != null && dto.getCategoryId() != 0L) {
				categoryId = dto.getCategoryId() ;
			}
			
			if(dto.getItemName() != null && !dto.getItemName().equals("")) {
				itemName = dto.getItemName() ;
			}
			
			if(dto.getDescription() != null && !dto.getDescription().equals("")) {
				description = dto.getDescription() ;
			}
			
			if(dto.getItemCode() != null && !dto.getItemCode().equals("")) {
				itemCode = dto.getItemCode() ;
			}			
		}
				
		itemEntityListSearch = repo.findAll(categoryId , itemName , description , itemCode , parentCategoryId) ;
		if(!itemEntityListSearch.isEmpty()) {
			itemsList = new ArrayList<ItemsDTO>() ;
			for(ItemEntity entity : itemEntityListSearch) {
				ItemsDTO item = HelperUtils.convertEntityToDto(entity , ItemsDTO.class) ;
				
				GeneralDTO currency = generalService.currencyById(item.getCurrencyId()) ;
				if(currency != null) {
					item.setCurrencyName(currency.getValueEn());
					item.setCurrencyNameAr(currency.getValueAr()) ;
					item.setInternationalCode(currency.getIsoName()) ;
				}
				
				CategoryDTO category = categoryService.findById(item.getCategoryId()) ;
				if(category != null) {
					item.setCategoryName(category.getCategoryName()) ;
				}
				
				itemsList.add(item) ;
			}
		}
		return itemsList ;
	}
	
	
		@Override
	public Long save(ItemsDTO dto) {
		Long id = null ;	
		if(dto != null) {
			ItemEntity entity = HelperUtils.convertDtoToEntity(dto , ItemEntity.class) ;
			entity = repo.save(entity) ;
			id = entity.getId() ;
		}		
		return id ;
	}

	@Override
		public void delete(Long id) {
			ItemsDTO dto = findById(id) ;
			if(dto != null) {
				ItemEntity entity = HelperUtils.convertDtoToEntity(dto , ItemEntity.class) ;
				repo.delete(entity) ;
			}
		}
	
	@Override
	public List<ItemsDTO> findAllByCategoryId(Long categoryId) {
		List<ItemsDTO> list = null ;
		List<ItemEntity> entityList = repo.findByCategoryId(categoryId) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<ItemsDTO>() ;
			for(ItemEntity entity : entityList) {
				ItemsDTO dto = HelperUtils.convertEntityToDto(entity , ItemsDTO.class) ;
				Integer offerId = itemOffersService.findMaxOfferIdByItemId(dto.getId()) ;
				if(offerId != null) {
					OffersDTO offersDTO = offersService.findById(offerId) ;
					if(offersDTO != null) {
						String toDay = (new Date().getYear() + 1900) + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate() ;
						String validTo = (offersDTO.getValidTo().getYear() + 1900) + "-" + (offersDTO.getValidTo().getMonth() + 1) + "-" + offersDTO.getValidTo().getDate() ;
						Integer isValid = HelperUtils.dateBeforeDate(validTo, toDay) ;
						if(offersDTO.getIsActive() == 1 && isValid == 0) {
							Integer isPercent = offersDTO.getIsPercent() == null ? 0 : offersDTO.getIsPercent() ;
							Double itemPriceOffer = 0.0 ;
							Double offerValue = offersDTO.getOfferValue() ;
							Double itemPrice = dto.getItemPrice() ;
							dto.setOfferValidity(0) ;
							if(isPercent == 1) {							
								itemPriceOffer = (itemPrice * offerValue) / 100 ;
								dto.setItemPriceOffer(itemPrice - itemPriceOffer) ;
							}else {
								itemPriceOffer = itemPrice - offerValue ;
							}
						}else {
							dto.setOfferValidity(1) ;
						}
					}
				}
				list .add(dto) ;
			}
		}
		return list ;
	}
	

	@Override
	public List<ItemsDTO> findAllByItemName(String itemName) {
		List<ItemsDTO> list = null ;
		List<ItemEntity> entityList = repo.findByItemNameContainingIgnoreCase(itemName) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<ItemsDTO>() ;
			for(ItemEntity entity : entityList) {
				ItemsDTO dto = HelperUtils.convertEntityToDto(entity, ItemsDTO.class) ;
				if(dto != null) {
					Integer itemOfferId = itemOffersService.findMaxIdByItemId(dto.getId()) ;
					if(itemOfferId != null) {
						ItemOffersDTO itemOffer = itemOffersService.findById(itemOfferId) ;
						if(itemOffer != null) {
							// assign offerId , then get validTo and compare before now or not
							OffersDTO offer = offersService.findById(itemOffer.getOfferId()) ;
							if(offer != null) {
								String toDay = (new Date().getYear() + 1900) + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate() ;
								String validTo = (offer.getValidTo().getYear() + 1900) + "-" + (offer.getValidTo().getMonth() + 1) + "-" + offer.getValidTo().getDate() ;
								Integer isValid = HelperUtils.dateBeforeDate(validTo, toDay) ;
								Integer isPercent = offer.getIsPercent() == null ? 0 : offer.getIsPercent() ;
								Double itemPriceOffer = 0.0 ;
								Double offerValue = offer.getOfferValue() ;
								Double itemPrice = dto.getItemPrice() ;
								dto.setOfferValidity(isValid) ;
								if(isPercent == 1) {							
									itemPriceOffer = (itemPrice * offerValue) / 100 ;
									dto.setItemPriceOffer(itemPrice - itemPriceOffer) ;
								}else {
									itemPriceOffer = itemPrice - offerValue ;
								}
							}
						}
					}
				}
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public List<ItemsDTO> findAllByItemName(String itemName , int sortType) {
		List<ItemsDTO> list = null ;
		List<ItemEntity> entityList = null ;
		if(sortType == 0) {
			entityList = repo.findByItemNameContainingIgnoreCaseOrderByItemPriceAsc(itemName) ;
		}else {
			entityList = repo.findByItemNameContainingIgnoreCaseOrderByItemPriceDesc(itemName) ;
		}
		
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<ItemsDTO>() ;
			for(ItemEntity entity : entityList) {
				ItemsDTO dto = HelperUtils.convertEntityToDto(entity, ItemsDTO.class) ;
				if(dto != null) {
					Integer itemOfferId = itemOffersService.findMaxIdByItemId(dto.getId()) ;
					if(itemOfferId != null) {
						ItemOffersDTO itemOffer = itemOffersService.findById(itemOfferId) ;
						if(itemOffer != null) {
							// assign offerId , then get validTo and compare before now or not
							OffersDTO offer = offersService.findById(itemOffer.getOfferId()) ;
							if(offer != null) {
								String toDay = (new Date().getYear() + 1900) + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate() ;
								String validTo = (offer.getValidTo().getYear() + 1900) + "-" + (offer.getValidTo().getMonth() + 1) + "-" + offer.getValidTo().getDate() ;
								Integer isValid = HelperUtils.dateBeforeDate(validTo, toDay) ;
								Integer isPercent = offer.getIsPercent() == null ? 0 : offer.getIsPercent() ;
								Double itemPriceOffer = 0.0 ;
								Double offerValue = offer.getOfferValue() ;
								Double itemPrice = dto.getItemPrice() ;
								dto.setOfferValidity(isValid) ;
								if(isPercent == 1) {							
									itemPriceOffer = (itemPrice * offerValue) / 100 ;
									dto.setItemPriceOffer(itemPrice - itemPriceOffer) ;
								}else {
									itemPriceOffer = itemPrice - offerValue ;
								}
							}
						}
					}
				}
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public List<ItemsDTO> mostPopular() {
		List<ItemsDTO> list = itemsDAO.mostPopular() ;
		return list ;
	}
	

	@Override
	public ItemsDTO itemDetails(Long id) {
		ItemsDTO dto = null ;
		Optional<ItemEntity> opt = repo.findById(id) ;
		if(opt != null && !opt.isEmpty()) {
			ItemEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity , ItemsDTO.class) ;
			if(dto != null) {
				GeneralDTO currency = generalService.currencyById(dto.getCurrencyId()) ;
				if(currency != null) {
					dto.setCurrencyName(currency.getValueEn()) ;
					dto.setCurrencyNameAr(currency.getValueAr()) ;
					dto.setInternationalCode(currency.getIsoName()) ;
				}
				Integer itemOfferId = itemOffersService.findMaxIdByItemId(dto.getId()) ;
				if(itemOfferId != null) {
					ItemOffersDTO itemOffer = itemOffersService.findById(itemOfferId) ;
					if(itemOffer != null) {
						OffersDTO offer = offersService.findById(itemOffer.getOfferId()) ;
						if(offer != null) {
							String toDay = (new Date().getYear() + 1900) + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate() ;
							String validTo = (offer.getValidTo().getYear() + 1900) + "-" + (offer.getValidTo().getMonth() + 1) + "-" + offer.getValidTo().getDate() ;
							Integer isValid = HelperUtils.dateBeforeDate(validTo, toDay) ;
							
							dto.setOfferValidity(isValid) ;
							if(isValid == 1) {
								Integer isPercent = offer.getIsPercent() == null ? 0 : offer.getIsPercent() ;
								Double itemPriceOffer = 0.0 ;
								Double offerValue = offer.getOfferValue() ;
								Double itemPrice = dto.getItemPrice() ;								
								if(isPercent == 1) {							
									itemPriceOffer = (itemPrice * offerValue) / 100 ;
									dto.setItemPriceOffer(itemPrice - itemPriceOffer) ;
								}else {
									itemPriceOffer = itemPrice - offerValue ;
									dto.setItemPriceOffer(itemPriceOffer) ;
								}								
							}
						}
					}					
				}				
			}
		}
		return dto ;
	}
	

	@Override
	public List<ItemsDTO> lastAdded() {
		return itemsDAO.lastAdded() ;
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

	public Page<ItemEntity> getItemSearchPage() {
		return itemSearchPage;
	}

	public void setItemSearchPage(Page<ItemEntity> itemSearchPage) {
		this.itemSearchPage = itemSearchPage;
	}

	public List<ItemEntity> getItemEntityListSearch() {
		return itemEntityListSearch;
	}

	public void setItemEntityListSearch(List<ItemEntity> itemEntityListSearch) {
		this.itemEntityListSearch = itemEntityListSearch;
	}

	public List<ItemsDTO> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ItemsDTO> itemsList) {
		this.itemsList = itemsList;
	}

	public Map<String, Object> getMetaData() {
		return metaData;
	}

	public void setMetaData(Map<String, Object> metaData) {
		this.metaData = metaData;
	}

	
}
