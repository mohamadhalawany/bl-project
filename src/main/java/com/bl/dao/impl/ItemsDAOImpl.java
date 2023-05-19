package com.bl.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bl.HelperUtils;
import com.bl.dao.ItemsDAO;
import com.bl.dto.ItemsDTO;
import com.bl.dto.cms.OffersDTO;
import com.bl.entity.cms.SettingEntity;
import com.bl.repository.cms.SettingRepository;
import com.bl.service.cms.OffersService;

@Component
public class ItemsDAOImpl implements ItemsDAO {
	
	@PersistenceContext
    private EntityManager em ;
	
	@Autowired
	private OffersService offersService ;
	@Autowired
	private SettingRepository settingsRepository ;
	
	
	
	@Override
	public List<ItemsDTO> mostPopular() {
		List<ItemsDTO> list = null ;
				
		String toDay = new Date().getYear() + 1900 + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate() ;

		String sql = " SELECT DISTINCT it.id , it.item_name , it.item_logo , it.ITEM_PRICE , it.ITEM_PRICE_OFFER , "				
				+ " cur.CURRENCY_NAME , cur.CURRENCY_NAME_AR , cur.INTERNATIONAL_CODE , "
				+ " (SELECT max(itoff.OFFER_ID) "
				+ " FROM item_offers itoff , offers ofr "
				+ "  WHERE itoff.OFFER_ID = ofr.ID "
				+ "  AND itoff.ITEM_ID = it.ID "
				+ "  AND ofr.IS_ACTIVE = 1)  OFFER_ID "
				
				+ " FROM order_items oi , items it , currency cur "
				+ " WHERE  oi.ITEM_ID = it.id "
				+ " AND it.CURRENCY_ID = cur.ID "
				+ " GROUP BY it.id "
				+ " ORDER BY COUNT(it.id) DESC " 
				+ " limit 10" ;
		
		Query query = em.createNativeQuery(sql) ;
		List<Object[]> objs = query.getResultList();
		if(objs != null && !objs.isEmpty()) {
			list = new ArrayList<ItemsDTO>() ;
			for(int i = 0; i < objs.size(); i++) {
				ItemsDTO dto = new ItemsDTO() ;
				dto.setId(Long.valueOf(objs.get(i)[0].toString())) ;
				dto.setItemName((String) objs.get(i)[1]) ;
				dto.setItemLogo((String) objs.get(i)[2]) ;
				dto.setItemPrice((Double) objs.get(i)[3]) ;
				dto.setItemPriceOffer((Double) objs.get(i)[4]) ;					
				dto.setCurrencyName((String) objs.get(i)[5]) ;
				dto.setCurrencyNameAr((String) objs.get(i)[6]) ;
				dto.setInternationalCode((String) objs.get(i)[7]);
				dto.setOfferId((Integer) objs.get(i)[8]);				
				if(dto.getOfferId() != null) {								
					OffersDTO offer = offersService.findById(dto.getOfferId()) ;
					if(offer != null) {
						int year = offer.getValidTo().getYear() + 1900 ;
						int month = offer.getValidTo().getMonth() + 1 ;
						int day = offer.getValidTo().getDate() ;
						String fromDate = year + "-" + month + "-" + day ;
						int before = HelperUtils.dateBeforeDate(fromDate , toDay) ;					
						if(before == 1) {
							Integer isPercent = offer.getIsPercent() == null ? 0 : offer.getIsPercent() ;
							Double itemPriceOffer = 0.0 ;
							Double offerValue = offer.getOfferValue() ;
							Double itemPrice = dto.getItemPrice() ;
							dto.setOfferValidity(before) ;							
							if(isPercent == 1) {							
								itemPriceOffer = (itemPrice * offerValue) / 100 ;
								dto.setItemPriceOffer(itemPrice - itemPriceOffer) ;
							}else {
								itemPriceOffer = itemPrice - offerValue ;
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
	public List<ItemsDTO> lastAdded() {
		List<ItemsDTO> list = null ;
		Optional<SettingEntity> settingOptional = settingsRepository.findById(5) ;		
		Integer sliceItems = 0 ;
		if(settingOptional != null && !settingOptional.isEmpty()) {
			sliceItems = settingOptional.get().getSliceItems() ;
		}else {
			sliceItems = 5 ;
		}
		
		String sql = "SELECT  it.ID , it.item_code , it.item_logo , it.item_name , it.ITEM_PRICE , "
				+ " ct.category_name , cu.CURRENCY_NAME , cu.CURRENCY_NAME_AR , ito.OFFER_ID , ofr.IS_PERCENT , cast(ofr.VALID_TO as NCHAR) , ofr.OFFER_VALUE "
				+ " FROM items it left join category ct on it.category_id = ct.id "
				+ " left join currency cu on it.CURRENCY_ID = cu.id "
				+ " left join item_offers ito on ito.ITEM_ID = it.id   "
				+ " left join offers ofr on  ito.OFFER_ID = ofr.id  "
				+ " WHERE DATE_ADD(it.CREATED_DATE , INTERVAL 30 DAY) > sysdate() "
				+ " AND it.IS_HIDDEN = 0 "
				+ " LIMIT " + sliceItems ;
		System.err.println(sql) ;
		Query query = em.createNativeQuery(sql) ;
		List<Object[]> objs = query.getResultList();
		if(objs != null && !objs.isEmpty()) {
			list = new ArrayList<ItemsDTO>() ;
			for(int i = 0; i < objs.size(); i++) {
				ItemsDTO dto = new ItemsDTO() ;
				dto.setIdInteger((Integer) objs.get(i)[0]) ;
				dto.setItemCode((String) objs.get(i)[1]) ;
				dto.setItemLogo((String) objs.get(i)[2]) ;
				dto.setItemName((String) objs.get(i)[3]) ;
				dto.setItemPrice((Double) objs.get(i)[4]) ;
				dto.setCategoryName((String) objs.get(i)[5]) ;
				dto.setCurrencyName((String) objs.get(i)[6]) ;
				dto.setCurrencyNameAr((String) objs.get(i)[7]) ;
								
				if(objs.get(i)[8] != null) {								
					dto.setOfferId((Integer) objs.get(i)[8]) ;
					dto.setIsPercent((Integer) objs.get(i)[9]) ;
					
					Integer percent = (Integer) objs.get(i)[9] ;
					Date from = HelperUtils.convertStringToDate((String) objs.get(i)[10]) ;
					
					int year = from.getYear() + 1900 ;
					int month = from.getMonth() + 1 ;
					int day = from.getDate() ;
					String fromDate = year + "-" + month + "-" + day ;
					String toDate = new Date().getYear() + 1900 + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate() ;
					int before = HelperUtils.dateBeforeDate(fromDate , toDate) ;
					
					if(before == 1) {
						Double itemPriceOffer = 0.0 ;
						Double offerValue = (Double) objs.get(i)[11] ;
						Double itemPrice = dto.getItemPrice() ;
						dto.setOfferValidity(before) ;							
						if(percent == 0) {							
							itemPriceOffer = (itemPrice * offerValue) / 100 ;
							dto.setItemPriceOffer(itemPrice - itemPriceOffer) ;
						}else {
							itemPriceOffer = itemPrice - offerValue ;
							dto.setItemPriceOffer(itemPriceOffer) ;
						}
					}					
				}
				list.add(dto) ;
			}					
		}
		return list ;
	}

}
