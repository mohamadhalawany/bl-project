package com.bl.repository.cms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bl.entity.cms.LoginUserEntity;

public interface LoginUserRepository extends JpaRepository<LoginUserEntity , Integer>{
	
	public LoginUserEntity findByUsernameAndPasswordAndActiveFlag(String username , String password , Integer activeFlag) ;
	public int countByUsername(String username) ;
	
	@Query("SELECT o FROM LoginUserEntity o WHERE o.username LIKE %?1% OR o.fullName LIKE %?2% ")
	public List<LoginUserEntity> searchByUsernameOrFullName(String username , String fullName) ;
}
