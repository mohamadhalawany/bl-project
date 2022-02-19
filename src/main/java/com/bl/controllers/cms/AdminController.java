package com.bl.controllers.cms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bl.dto.cms.LoginUserDTO;
import com.bl.service.cms.LoginUserService;


@Controller
public class AdminController {
	
	private String loginPage = "login" ;
	private String usersPage = "users/users" ;
	private String searchPage = "users/search" ;
	
	List<LoginUserDTO> list = new ArrayList<LoginUserDTO>() ;
	
	@Autowired
	private LoginUserService service ;
	
	
	@RequestMapping("aao/users")
	public ModelAndView users(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			mv.addObject("dto" , new LoginUserDTO()) ;
			mv.setViewName(usersPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/search")
	public ModelAndView search(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			list = service.findAll() ;
			Map<String , Object> metaData = service.metaData() ;
			
			mv.addObject("list" , list) ;		
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("totalIUsers" , list.size()) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	
	@RequestMapping(value = "aao/saveUser" , method = RequestMethod.POST , params = "save")
	public ModelAndView saveUser(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		
		String fullName = request.getParameter("fullName") ;
		String username = request.getParameter("username") ;
		String password = request.getParameter("password") ;
		
		LoginUserDTO dto = new LoginUserDTO() ;
		dto.setFullName(fullName) ;
		dto.setPassword(password) ;
		dto.setUsername(username) ;
		
		int found = service.countByUsername(username) ;
		if(found == 0) {
			dto.setActiveFlag(1) ;
			int saved = service.saveUser(dto) ;
			if(saved == 1) {
				mv.addObject("saved" , 1) ;
			}else {
				mv.addObject("saved" , null) ;
			}
			mv.addObject("dto" , dto) ;
		}else {
			mv.addObject("found" , 1) ;
			mv.addObject("dto" , new LoginUserDTO()) ;
		}
		mv.setViewName(usersPage) ;
		return mv ;
	}
	
	
	
	
	
	@RequestMapping("aao/next")
	public ModelAndView next(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			list = service.nextPage() ;
			Map<String , Object> metaData = service.metaData() ;
			
			mv.addObject("list" , list) ;		
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("totalIUsers" , list.size()) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	
	
	@RequestMapping("aao/previous")
	public ModelAndView previous(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			list = service.previousPage() ;
			Map<String , Object> metaData = service.metaData() ;
			
			mv.addObject("list" , list) ;		
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("totalIUsers" , list.size()) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/active")
	public ModelAndView activeInactive(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
				
		if(session.getAttribute("user") != null) {
			int flag = 1 ;
			Integer id = 0 ;
			
			if(request.getParameter("flag") == null) {
				flag = 1 ;
			}else {
				flag = Integer.parseInt(request.getParameter("flag")) ;
			}
			
			if(request.getParameter("id") != null) {
				id = Integer.parseInt(request.getParameter("id")) ;
			}
			
			LoginUserDTO dto = service.findById(id) ;
			if(dto != null) {
				dto.setActiveFlag(flag) ;
				service.updateActiveAndInactive(id , flag) ;
			}
			mv.addObject("list" , service.findAll()) ;
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	
	@RequestMapping(value = "aao/doSearch" , method = RequestMethod.POST , params = "save")
	public ModelAndView doSearch(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			String username = null ;
			String fullName = null ;
			
			if(request.getParameter("username") != null && !request.getParameter("username").equals("")) {
				username = request.getParameter("username") ;
			}
			
			if(request.getParameter("fullName") != null && !request.getParameter("fullName").equals("")) {
				fullName = request.getParameter("fullName") ;
			}
			
			LoginUserDTO dto = new LoginUserDTO() ;
			dto.setFullName(fullName) ;
			dto.setUsername(username) ;
			
			mv.addObject("dto" , dto) ;
			mv.addObject("list" , service.searchByUsernameOrFullName(username , fullName)) ;
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		
		return mv ;
	}
	
	
	
	
	
	
	@RequestMapping(value = "aao/doSearch" , method = RequestMethod.POST , params = "reset")
	public ModelAndView reset(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("user") != null) {
			List<LoginUserDTO> list = service.findAll() ;
			Map<String , Object> metaData = service.metaData() ;
			
			mv.addObject("list" , list) ;		
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("totalIUsers" , list.size()) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.addObject("dto" , new LoginUserDTO()) ;			
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}

	
	
	
	@RequestMapping("aao/delete")
	public ModelAndView delete(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			if(request.getParameter("id") != null) {
				Integer id = Integer.parseInt(request.getParameter("id")) ;
				LoginUserDTO dto = service.findById(id) ;
				service.delete(dto) ;
				mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER)) ;
			}
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}



	public List<LoginUserDTO> getList() {
		return list;
	}




	public void setList(List<LoginUserDTO> list) {
		this.list = list;
	}

}
