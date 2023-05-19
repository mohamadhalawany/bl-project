package com.bl.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bl.dao.CountryGovernorateCityDistrictMapper;
import com.bl.dao.EntityManagerHelper;
import com.bl.dao.GeneralDAO;
import com.bl.dao.GeneralMapper;
import com.bl.dto.CountryGovernorateCityDistrictDTO;
import com.bl.dto.GeneralDTO;
import com.bl.entity.cms.SettingEntity;


@Component
public class GeneralDAOImpl implements GeneralDAO {
	
	private JdbcTemplate temp ;
	
	
	public GeneralDAOImpl(DataSource ds) {
		temp = new JdbcTemplate(ds) ;
	}

	@Override
	public List<GeneralDTO> colorsList() {
		String sql = "SELECT ID , COLOR_NAME_EN , COLOR_NAME_AR , NULL AS ISO_NAME "
				+ " FROM COLORS" ;
		return temp.query(sql , new GeneralMapper()) ;
	}

	@Override
	public GeneralDTO colorNameById(Integer id) {
		String sql = "SELECT ID , COLOR_NAME_EN , COLOR_NAME_AR , NULL AS ISO_NAME "
				+ " FROM COLORS "
				+ " WHERE ID = " + id ;
		return temp.query(sql ,  new GeneralMapper()).get(0) ;
	}

	@Override
	public List<GeneralDTO> countryList() {
		String sql = "SELECT ID , COUNTRY_NAME , COUNTRY_NAME_AR , ISO "
				+ " FROM COUNTRIES"
				+ " WHERE ID IN(3 , 17 , 48 , 58 , 63 , 104 , 112 , 119 , 123 , 126 , 140 , 150 , 167 , 170 , 180 , 191 , 200 , 208 , 214 , 223 , 230 , 244) " ;
		return temp.query(sql , new GeneralMapper()) ;
	}

	@Override
	public List<GeneralDTO> governorateList(Integer countryId) {
		String sql = "SELECT ID , GOVERNORATE_NAME_EN , GOVERNORATE_NAME_AR , NULL "
				+ " FROM GOVERNORATES "
				+ " WHERE COUNTRY_ID = ? " ;
		return temp.query(sql , new Object[] {countryId} , new GeneralMapper()) ;
	}

	@Override
	public List<GeneralDTO> cityDistrictList(Integer governorateId) {
		String sql = "SELECT ID , name_en , name_ar , governorate_id "
				+ " FROM cities_districts "
				+ " WHERE governorate_id = ? " ;
		return temp.query(sql , new Object[] {governorateId} , new GeneralMapper()) ;
	}

	@Override
	public List<CountryGovernorateCityDistrictDTO> findGovernorateIdAndCountryIdByCityDistrictId(Integer cityDistrictId) {
		String sql = "SELECT d.id , g.ID , c.ID , "
				+ " d.name_en cities_districts_name_en , d.name_ar cities_districts_name_ar , "
				+ " g.GOVERNORATE_NAME_EN , g.GOVERNORATE_NAME_AR , "
				+ " c.COUNTRY_NAME , COUNTRY_NAME_AR "
				+ " FROM governorates g , countries c , cities_districts d "
				+ " WHERE g.COUNTRY_ID = c.ID "
				+ " AND d.governorate_id = g.ID "
				+ " AND d.id = ? " ;
		return temp.query(sql , new Object[] {cityDistrictId} , new CountryGovernorateCityDistrictMapper()) ;
	}

	@Override
	public GeneralDTO currencyById(Integer id) {
		String sql = "SELECT cu.ID , cu.CURRENCY_NAME , cu.CURRENCY_NAME_AR , cu.INTERNATIONAL_CODE "
				+ "FROM currency cu "
				+ "WHERE cu.ID = ? " ;
		return temp.queryForObject(sql , new Object[] {id} , new GeneralMapper()) ;
	}

	
	@Override
	public List<GeneralDTO> currencyList() {
		String sql = "SELECT cu.ID , cu.CURRENCY_NAME , cu.CURRENCY_NAME_AR , cu.INTERNATIONAL_CODE "
				+ "FROM currency cu " ;
		return temp.query(sql , new GeneralMapper()) ;
	}

	@Override
	public Integer orderExpireDays() {
		Integer days = 0 ;
		String sql = "SELECT s FROM SettingEntity s where s.id = 4" ;
		EntityManager em = EntityManagerHelper.open() ;
		Query query = em.createQuery(sql) ;
		List<SettingEntity> objs = query.getResultList() ;
		if(objs != null && !objs.isEmpty()) {
			for(SettingEntity entity : objs) {
				days = entity.getExpireDays() ;
			}
		}
		return days ;
	}
	
	

}
