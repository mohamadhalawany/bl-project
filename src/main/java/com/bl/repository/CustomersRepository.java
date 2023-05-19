package com.bl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bl.entity.CustomersEntity;

public interface CustomersRepository extends JpaRepository<CustomersEntity , Long> {
	
	@Query(value = "SELECT c FROM CustomersEntity c WHERE c.email = :email AND c.password = :password")
	public CustomersEntity findByEmailAndPassword(@Param("email") String email , @Param("password") String password) ;
	
	public Long countByEmail(String email) ;
	
	@Query(value = "SELECT c FROM CustomersEntity c "
			+ " WHERE (?1 IS NULL OR c.email LIKE %?1%) "
			+ " AND (?2 IS NULL OR c.fullName LIKE %?2%) "
			+ " AND (c.customerType = ?3 OR ?3 IS NULL) "
			+ " AND (c.cityDistrictId = ?4 OR ?4 IS NULL) "
			+ " AND (?5 IS NULL OR c.cityDistrictId IN (SELECT cd.id FROM CitiesDistrictEntity cd WHERE cd.governorateId = ?5)) "
			+ " AND (?6 IS NULL OR c.cityDistrictId IN (SELECT cd.id FROM CitiesDistrictEntity cd WHERE cd.governorateId IN "
			+ " 		   (SELECT gov.id FROM GovernorateEntity gov WHERE gov.countryId = ?6))) ")
	public Page<CustomersEntity> search(String email , String fullName , Integer customerType , Integer cityDistrictId , Integer governorateId , Integer countryId , 
			Pageable page) ;
	
	@Query("SELECT c FROM CustomersEntity c "
			+ " WHERE c.isBlocked = 1") 
	public Page<CustomersEntity> blockedCustomer(Pageable page) ;
	
	@Query("SELECT c FROM CustomersEntity c "
			+ " WHERE c.isBlocked = 0") 
	public Page<CustomersEntity> findAll(Pageable page) ;
}
