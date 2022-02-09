package com.bl.controllers.cms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bl.dto.cms.LoginUserDTO;
import com.bl.service.cms.LoginUserService;

@Controller()
public class HomeCmsController {
	
	private String loginPage = "login" ;
	private String homePage = "index" ;
	
	@Autowired
	private LoginUserService service ;
	
	
	@RequestMapping("aao/home")
	public ModelAndView home(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") == null) {
			mv.addObject("today" , new Date()) ;
			mv.setViewName(loginPage) ;
		}else {
			mv.setViewName(homePage) ;
		}
		
		return mv ;
	}
	
	
	
	@RequestMapping("aao/goLogin")
	public ModelAndView goLogin(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") == null) {
			mv.addObject("today" , new Date()) ;
			mv.setViewName(loginPage) ;
		}else {
			mv.setViewName(homePage) ;
		}
		
		return mv ;
	}
	
	@RequestMapping("aao/")
	public ModelAndView defaultHome(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") == null) {
			mv.addObject("today" , new Date()) ;
			mv.setViewName(loginPage) ;
		}else {
			mv.setViewName(homePage) ;
		}
		return mv ;
	}
	
	
	
	
	
	@RequestMapping("aao")
	public ModelAndView defaultCMS(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") == null) {
			mv.addObject("today" , new Date()) ;
			mv.setViewName(loginPage) ;
		}else {
			mv.setViewName(homePage) ;
		}
		return mv ;
	}
	
	
	
	
	
	
	@RequestMapping(value = "aao/login" , method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		String username = request.getParameter("username") ;
		String password = request.getParameter("password") ;
		
		LoginUserDTO dto = service.findByUsername(username,  password) ;
		if(dto != null) {
			session.setAttribute("user" , dto) ;
			session.setMaxInactiveInterval(600) ;
			mv.addObject("invalidUser" , null) ;
			mv.setViewName(homePage) ;
		}else {	
			mv.addObject("invalidUser" , 0) ;
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/logout")
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		session.setAttribute("user" , null) ;
		session.invalidate() ;
		mv.setViewName(loginPage) ;
		return mv ;
	}
}
