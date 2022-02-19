package com.bl.service.cms.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dto.cms.LoginUserDTO;
import com.bl.entity.cms.LoginUserEntity;
import com.bl.repository.cms.LoginUserRepository;
import com.bl.service.cms.LoginUserService;


@Service
public class LoginUserServiceImpl implements LoginUserService {

	@Autowired
	private LoginUserRepository repo ;
	
	private Page<LoginUserEntity> userPage ;
	private List<LoginUserEntity> userList ;
	
	
	@Override
	public LoginUserDTO findByUsername(String username, String password) {
		password = HelperUtils.encrypt(password) ;
		LoginUserDTO dto = null ;
		LoginUserEntity entity = repo.findByUsernameAndPasswordAndActiveFlag(username, password , 1) ; 
		if(entity != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" , new Locale("en", "EG")) ;	    	
			entity.setLastLoginDate(sdf.format(new Date())) ;
			entity = repo.save(entity) ;
			dto = HelperUtils.convertEntityToDto(entity , LoginUserDTO.class) ;
		}
		return dto ;
	}


	@Override
	public int saveUser(LoginUserDTO dto) {
		dto.setPassword(HelperUtils.encrypt(dto.getPassword())) ;
		LoginUserEntity entity = HelperUtils.convertDtoToEntity(dto , LoginUserEntity.class) ;
		if(dto.getActiveFlag() == 1) {
			entity.setActiveFlag(1) ;
		}else {
			entity.setActiveFlag(0) ;
		}
		entity = repo.save(entity) ;
		if(entity != null) {
			return 1 ;
		}else {
			return 0 ;
		}
	}


	@Override
	public int countByUsername(String username) {
		int found = repo.countByUsername(username) ;		
		return found ;
	}


	@Override
	public List<LoginUserDTO> findAll() {
		List<LoginUserDTO> list = null ;
		userPage = repo.findAll(PageRequest.of(0 , 3)) ;
		userList = userPage.getContent() ;
		if(userList != null && !userList.isEmpty()) {
			list = new ArrayList<LoginUserDTO>() ;
			for(LoginUserEntity entity : userList) {
				LoginUserDTO dto = HelperUtils.convertEntityToDto(entity , LoginUserDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	
	
	

	@Override
	public List<LoginUserDTO> nextPage() {
		List<LoginUserDTO> list = new ArrayList<LoginUserDTO>() ;
		if(userPage.hasNext()) {
			userPage = repo.findAll(userPage.nextPageable()) ;
			userList = userPage.getContent() ;
			for(LoginUserEntity entity : userList) {
				LoginUserDTO dto = HelperUtils.convertEntityToDto(entity , LoginUserDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}


	@Override
	public List<LoginUserDTO> previousPage() {
		List<LoginUserDTO> list = new ArrayList<LoginUserDTO>() ;
		if(userPage.hasPrevious()) {
			userPage = repo.findAll(userPage.previousPageable()) ;
			userList = userPage.getContent() ;
			for(LoginUserEntity entity : userList) {
				LoginUserDTO dto = HelperUtils.convertEntityToDto(entity , LoginUserDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	
	
	


	@Override
	public Map<String, Object> metaData() {
		Map<String, Object> metaData = new HashMap<String, Object>() ;
		if(userPage != null) {
		    metaData.put("currentPage", userPage.getNumber() + 1);
		    metaData.put("totalIUsers", userPage.getTotalElements());
		    metaData.put("totalPages", userPage.getTotalPages());
		    metaData.put("isFirst", userPage.isFirst());
		    metaData.put("isLast", userPage.isLast());
		}
		
		return metaData;
	}


	@Override
	public LoginUserDTO findById(Integer id) {
		LoginUserDTO dto = null ;
		LoginUserEntity entity = repo.findById(id).get() ;
		if(entity != null) {
			dto = HelperUtils.convertEntityToDto(entity , LoginUserDTO.class) ;
		}
		return dto ;
	}
	
	
	
	
	@Override
	public void updateActiveAndInactive(Integer id , Integer flag) {
		LoginUserDTO dto = findById(id) ;
		dto.setActiveFlag(flag) ;
		LoginUserEntity entity = HelperUtils.convertDtoToEntity(dto , LoginUserEntity.class) ;
		repo.save(entity) ;
	}
	
	


	@Override
	public List<LoginUserDTO> searchByUsernameOrFullName(String username , String fullName) {
		List<LoginUserDTO> list = null ;
		List<LoginUserEntity> entityList = repo.searchByUsernameOrFullName(username , fullName) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<LoginUserDTO>() ;
			for(LoginUserEntity entity : entityList) {
				LoginUserDTO dto = HelperUtils.convertEntityToDto(entity , LoginUserDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	
	
	
	@Override
	public void delete(LoginUserDTO dto) {
		LoginUserEntity entity = HelperUtils.convertDtoToEntity(dto, LoginUserEntity.class) ;
		repo.delete(entity) ;
	}

	public Page<LoginUserEntity> getUserPage() {
		return userPage;
	}


	public void setUserPage(Page<LoginUserEntity> userPage) {
		this.userPage = userPage;
	}


	public List<LoginUserEntity> getUserList() {
		return userList;
	}


	public void setUserList(List<LoginUserEntity> userList) {
		this.userList = userList;
	}	
}
