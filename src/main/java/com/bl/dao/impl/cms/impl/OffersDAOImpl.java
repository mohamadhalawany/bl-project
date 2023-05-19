package com.bl.dao.impl.cms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.bl.HelperUtils;
import com.bl.dao.EntityManagerHelper;
import com.bl.dao.impl.cms.OffersDAO;
import com.bl.dto.cms.OffersDTO;

@Component
public class OffersDAOImpl implements OffersDAO {

	@PersistenceContext
    private EntityManager em ;
	
	@Override
	public List<OffersDTO> search(OffersDTO dto) {
		List<OffersDTO> list = null ;
		String sql = "SELECT o.id , o.createdBy , cast(o.createdDate as string) , o.isActive , o.isPercent ,o.offerName , o.offerValue , o.updatedBy ,  "
				+ " cast(o.updatedDate as string) , cast(o.validFrom as string) , cast(o.validTo as string) "
				+ " FROM OffersEntity o "
				+ " WHERE 1 = 1 " ;

		String condition = "" ;
		
		if(dto.getOfferName() != null) {
			condition += " AND o.offerName LIKE :offerName " ;
		}
		if(dto.getIsActive() != null) {
			condition += " AND o.isActive = :isActive " ;
		}
		if(dto.getFrom() != null && dto.getTo() != null) {
			condition += " AND cast(o.validFrom as string) BETWEEN :form AND :to " ;
		}
		
		Query query = em.createQuery(sql + condition);
		if(dto.getOfferName() != null) {
			query.setParameter("offerName" , "%" + dto.getOfferName() + "%") ;
		}
		if(dto.getIsActive() != null) {
			query.setParameter("isActive" , dto.getIsActive()) ;
		}
		if(dto.getFrom() != null && dto.getTo() != null) {
			query.setParameter("form" , dto.getFrom()) ;
			query.setParameter("to" , dto.getTo()) ;
		}
		
		String toDay = new Date().getYear() + 1900 + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate() ;
		
		List<Object[]> objs = query.getResultList();
		if(objs != null && !objs.isEmpty()) {
			list = new ArrayList<OffersDTO>() ;
			for(int i = 0; i < objs.size(); i++) {
				OffersDTO offer = new OffersDTO() ;
				offer.setId((Integer) objs.get(i)[0]);
				offer.setCreatedBy((Integer)objs.get(i)[1]) ;
				offer.setCreatedDateString((String) objs.get(i)[2]) ;
				offer.setIsActive((Integer) objs.get(i)[3]) ;
				offer.setIsPercent((Integer) objs.get(i)[4]) ;
				offer.setOfferName((String) objs.get(i)[5]) ;
				offer.setOfferValue((Double) objs.get(i)[6]) ;
				offer.setUpdatedBy((Integer) objs.get(i)[7]) ;
				offer.setUpdatedDateString((String) objs.get(i)[8]) ;
				offer.setFrom((String) objs.get(i)[9]) ;
				offer.setTo((String) objs.get(i)[10]) ;
				offer.setExpired(HelperUtils.dateBeforeDate((String) objs.get(i)[9] , toDay)) ;
								
				list.add(offer) ;
			}
		}
		return list ;
	}

	@Override
	public List<OffersDTO> availableOffers(Long itemId) {
		List<OffersDTO> list = new ArrayList<OffersDTO>() ;
		String sql = "SELECT o.id , o.isActive , o.isPercent ,o.offerName , o.offerValue , cast(o.validFrom as string) , cast(o.validTo as string) "
				+ " FROM OffersEntity o "
				+ " WHERE o.isActive = 1 "
				+ " AND o.id NOT IN (SELECT i.offerId FROM ItemOffersEntity i WHERE i.itemId = " + itemId + ")" ;
		
		Query query = em.createQuery(sql);
		String toDay = new Date().getYear() + 1900 + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate() ;		
		List<Object[]> objs = query.getResultList();
		if(objs != null && !objs.isEmpty()) {			
			for(int i = 0; i < objs.size(); i++) {
				int expired = HelperUtils.dateBeforeDate((String) objs.get(i)[6] , toDay) ;
				if(expired == 0) {
					OffersDTO offer = new OffersDTO() ;
					offer.setId((Integer) objs.get(i)[0]) ;
					offer.setIsPercent((Integer) objs.get(i)[2]) ;
					offer.setOfferName((String) objs.get(i)[3]) ;
					offer.setOfferValue((Double) objs.get(i)[4]);
					offer.setFrom((String) objs.get(i)[5]) ;
					offer.setTo((String) objs.get(i)[6]) ;
					
					list.add(offer) ;
				}
			}
		}
		return list ;
	}
	
	
	@Override
	public Integer dateBeforeDate(String from , String to) {		
		Integer before = HelperUtils.dateBeforeDate(from, to) ;
		return before ;
	}

}
