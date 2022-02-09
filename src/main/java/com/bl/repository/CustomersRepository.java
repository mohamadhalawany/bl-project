package com.bl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bl.entity.CustomersEntity;

public interface CustomersRepository extends JpaRepository<CustomersEntity , Long> {
	
	@Query(value = "SELECT c FROM CustomersEntity c WHERE c.email = :email AND c.password = :password")
	public CustomersEntity findByEmailAndPassword(@Param("email") String email , @Param("password") String password) ;
	
	public Long countByEmail(String email) ;
}
