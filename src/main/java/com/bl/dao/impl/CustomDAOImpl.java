package com.bl.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.bl.dao.CustomDAO;
import com.bl.dao.EntityManagerHelper;
import com.bl.dto.GeneralDTO;

public class CustomDAOImpl implements CustomDAO {

	@Override
	public GeneralDTO currencyById(Integer currencyId) {
		String sql = "SELECT cu.ID , cu.CURRENCY_NAME , cu.CURRENCY_NAME_AR , cu.INTERNATIONAL_CODE "
				+ "FROM currency cu "
				+ "WHERE cu.ID = ?1 " ;
		EntityManager em = EntityManagerHelper.open() ;
		Query query = em.createNativeQuery(sql) ;
		query.setParameter(1 , currencyId) ;
		List<Object[]> objs = query.getResultList();
		List<GeneralDTO> list = null ;
		if(objs != null && !objs.isEmpty()) {
			list = new ArrayList<GeneralDTO>() ;
			
			for(int i = 0 ; i < objs.size() ; i++) {
				GeneralDTO dto = new GeneralDTO() ;
				dto.setId((Integer) objs.get(i)[0]) ;
				dto.setValueEn((String) objs.get(i)[1]) ;
				dto.setValueAr((String) objs.get(i)[2]) ;
				dto.setIsoName((String) objs.get(i)[3]) ;
				list.add(dto) ;
			}
		}
		return list.get(0) ;
	}

}
