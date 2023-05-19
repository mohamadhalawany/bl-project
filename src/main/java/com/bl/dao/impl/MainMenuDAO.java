package com.bl.dao.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.bl.HelperUtils;
import com.bl.dao.EntityManagerHelper;
import com.bl.dto.CategoryDTO;
import com.bl.dto.cms.BrandDTO;
import com.bl.dto.cms.CompanyInfoDTO;
import com.bl.dto.cms.MainMenuDTO;
import com.bl.entity.CitiesDistrictEntity;
import com.bl.entity.CountryEntity;
import com.bl.entity.GovernorateEntity;
import com.bl.entity.cms.CompanyInfoEntity;

@Component
public class MainMenuDAO {
		
	public List<MainMenuDTO> findAllByIsActive(Integer isActive){
		EntityManager em = EntityManagerHelper.open() ;
		List<MainMenuDTO> list = null ;
		
		String sql = "SELECT m.id , m.createdBy , m.createdDate , m.menuName , m.updatedBy , m.updatedDate , m.isActive "
				+ " FROM MainMenuEntity m "
				+ " WHERE m.isActive = 1 " ;
		
		Query query = em.createQuery(sql) ;
		List<Object[]> objs = query.getResultList();
		if(objs != null && !objs.isEmpty()) {
			list = new ArrayList<MainMenuDTO>() ;
			for(int i = 0; i < objs.size(); i++) {
				MainMenuDTO dto = new MainMenuDTO() ;
				dto.setId((Integer) objs.get(i)[0]) ;
				dto.setMenuName((String) objs.get(i)[3]) ;
				dto.setIsActive((Integer) objs.get(i)[6]) ;
				
				dto.setCategoryList(findCategoryByMenuId(dto.getId())) ;
				
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	
	public MainMenuDTO findById(Integer id) {
		MainMenuDTO dto = null ;
		EntityManager em = EntityManagerHelper.open() ;
		
		String sql = "SELECT m.id , m.createdBy , m.createdDate , m.menuName , m.updatedBy , m.updatedDate , m.isActive "
				+ " FROM MainMenuEntity m "
				+ " WHERE m.isActive = ?1 " 
				+ " AND m.id = 1 " ;
		
		Query query = em.createQuery(sql);
		query.setParameter(1 , id) ;
		List<Object[]> objs = query.getResultList() ;
		
		if(objs != null && !objs.isEmpty()) {
			dto = new MainMenuDTO() ;
			for (int i = 0 ; i < objs.size() ; i++) {		
				dto.setId((Integer) objs.get(i)[0]) ;
				dto.setMenuName((String) objs.get(i)[3]) ;
				dto.setIsActive((Integer) objs.get(i)[6]) ;
			}
		}		
		return dto ;
	}
	
	
	
	public List<CategoryDTO> findAllMainCategories(){
		List<CategoryDTO> list = null ;
		EntityManager em = EntityManagerHelper.open() ;
		
		String sql = "SELECT c.id , c.categoryName "
				+ " FROM CategoryEntity c "
				+ " WHERE c.parentCategoryId IS NULL "
				+ " AND c.menuId = 1" ;
		
		Query query = em.createQuery(sql);
		List<Object[]> objs = query.getResultList() ;
		
		if(objs != null && !objs.isEmpty()) {
			list = new ArrayList<CategoryDTO>() ;
			for(int i = 0 ; i < objs.size() ; i++) {
				CategoryDTO dto = new CategoryDTO() ;
				dto.setId((Long) objs.get(i)[0]) ;
				dto.setCategoryName((String) objs.get(i)[1]) ;
				
				dto.setSubCategoryList(findCategoryByParentCategoryId(dto.getId())) ;
				
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	
	
	private List<CategoryDTO> findCategoryByParentCategoryId(Long id){
		List<CategoryDTO> list = null ;
		EntityManager em = EntityManagerHelper.open() ;
		
		String sql = "SELECT c.id , c.categoryName "
				+ " FROM CategoryEntity c "
				+ " WHERE c.parentCategoryId = ?1 " 
				+ " AND c.menuId = 1" ;
		
		Query query = em.createQuery(sql);
		query.setParameter(1 , id) ;
		List<Object[]> objs = query.getResultList() ;
		
		if(objs != null && !objs.isEmpty()) {
			list = new ArrayList<CategoryDTO>() ;
			for(int i = 0 ; i < objs.size() ; i++) {
				CategoryDTO dto = new CategoryDTO() ;
				dto.setId((Long) objs.get(i)[0]) ;
				dto.setCategoryName((String) objs.get(i)[1]) ;
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	
	private List<CategoryDTO> findCategoryByMenuId(Integer menuId){
		List<CategoryDTO> list = null ;
		EntityManager em = EntityManagerHelper.open() ;
		
		String sql = "SELECT c.id , c.categoryName "
				+ " FROM CategoryEntity c "
				+ " WHERE c.menuId = ?1 " ;
		
		Query query = em.createQuery(sql);
		query.setParameter(1 , menuId) ;
		List<Object[]> objs = query.getResultList() ;
		
		if(objs != null && !objs.isEmpty()) {
			list = new ArrayList<CategoryDTO>() ;
			for(int i = 0 ; i < objs.size() ; i++) {
				CategoryDTO dto = new CategoryDTO() ;
				dto.setId((Long) objs.get(i)[0]) ;
				dto.setCategoryName((String) objs.get(i)[1]) ;
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	
	public List<BrandDTO> brandsMenu(){
		List<BrandDTO> list = null ;
		EntityManager em = EntityManagerHelper.open() ;
		
		String sql = "SELECT b.id , b.brandName "
				+ " FROM BrandEntity b "
				+ " WHERE b.isActive = 1 " ;
		
		Query query = em.createQuery(sql);
		List<Object[]> objs = query.getResultList() ;
		
		if(objs != null && !objs.isEmpty()) {
			list = new ArrayList<BrandDTO>() ;
			for(int i = 0 ; i < objs.size() ; i++) {
				BrandDTO dto = new BrandDTO() ;
				dto.setId((Integer) objs.get(i)[0]) ;
				dto.setBrandName((String) objs.get(i)[1]) ; 
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	
	public CompanyInfoDTO companyProfile() {
		CompanyInfoDTO company = null ;
		String sql = "SELECT c FROM CompanyInfoEntity c" ; 
		Query query = EntityManagerHelper.open() .createQuery(sql);
		CompanyInfoEntity entity = (CompanyInfoEntity) query.getResultStream().findFirst().orElse(null) ;		
		
		if(entity != null) {
			company = HelperUtils.convertEntityToDto(entity , CompanyInfoDTO.class) ;
			
			if(company != null) {
				sql = "SELECT c FROM CitiesDistrictEntity c where c.id = ?1" ;
				query = EntityManagerHelper.open() .createQuery(sql);
				query.setParameter(1 , company.getCityId()) ;
				CitiesDistrictEntity city = (CitiesDistrictEntity) query.getResultStream().findFirst().orElse(null) ;		
				if(city != null) {
					company.setCityName(city.getNameEn()) ;
					company.setCityNameAr(city.getNameAr()) ;
					
					sql = "SELECT g FROM GovernorateEntity g where g.id = ?1" ;
					query = EntityManagerHelper.open() .createQuery(sql);
					query.setParameter(1 , city.getGovernorateId()) ;
					GovernorateEntity gov = (GovernorateEntity) query.getResultStream().findFirst().orElse(null) ;
					if(gov != null) {
						company.setGovernorateName(gov.getGovernorateNameEn()) ;
						company.setGovernorateNameAr(gov.getGovernorateNameAr()) ;
						
						sql = "SELECT c FROM CountryEntity c where c.id = ?1" ;
						query = EntityManagerHelper.open() .createQuery(sql);
						query.setParameter(1 , gov.getCountryId()) ;
						CountryEntity country = (CountryEntity) query.getResultStream().findFirst().orElse(null) ;
						if(country != null) {
							company.setCountryName(country.getCountryName()) ;
							company.setCountryNameAr(country.getCountryNameAr());
						}
					}
				}
			}
		}
		return company ;
	}
}
