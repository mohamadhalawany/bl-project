package com.bl.service.cms;

import java.util.List;
import java.util.Map;

import com.bl.dto.cms.LoginUserDTO;


public interface LoginUserService {
	
	public List<LoginUserDTO> findAll() ;
	public List<LoginUserDTO> nextPage() ;
	public List<LoginUserDTO> previousPage() ;
	
	public LoginUserDTO findByUsername(String username , String password) ;
	public LoginUserDTO findById(Integer id) ;
	
	public int saveUser(LoginUserDTO dto) ;
	public int countByUsername(String username) ;
	public void updateActiveAndInactive(Integer id , Integer flag) ;
	
	public Map<String, Object> metaData() ;
	
	public List<LoginUserDTO> searchByUsernameOrFullName(String username , String fullName) ;
}
