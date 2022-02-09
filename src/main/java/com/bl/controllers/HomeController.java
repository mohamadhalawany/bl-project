package com.bl.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bl.CompanyProfile;
import com.bl.DomainValues;
import com.bl.dto.CustomersDTO;
import com.bl.dto.ItemsDTO;
import com.bl.dto.OrderRequestDTO;
import com.bl.service.ItemService;


@Controller
public class HomeController extends BaseController{
	
	@Autowired
	private ItemService itemService ;
	
	
	
	@RequestMapping("/")
	public ModelAndView home(HttpServletRequest request) {		
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		session.setAttribute("customerLoginForm" , null) ;
		session.setAttribute("customerLoginFaildMsg" , null) ;
		session.setAttribute("orderRequestList" , null) ;
		session.setAttribute("orderRequestListSize" , null) ;
		session.setAttribute("totalPrice" , null) ;			
		init(mv , request) ;
		
		List<ItemsDTO> itemsList = itemService.findAll() ;
		Map<String , Object> metaData = itemService.metaData() ;
		
		mv.addObject("itemsList" , itemsList) ;
		mv.addObject("isFirst" , metaData.get("isFirst")) ;
		mv.addObject("isLast" , metaData.get("isLast")) ;
		mv.setViewName("home") ;
		return mv ;
	}
	
	
	@RequestMapping("home")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		session.setAttribute("orderRequestList" , null) ;
		session.setAttribute("orderRequestListSize" , 0) ;
		session.setAttribute("totalPrice" , 0) ;			
		
		init(mv , request) ;
		
		List<ItemsDTO> itemsList = itemService.findAll() ;
		Map<String , Object> metaData = itemService.metaData() ;
		
		mv.addObject("itemsList" , itemsList) ;
		mv.addObject("isFirst" , metaData.get("isFirst")) ;
		mv.addObject("isLast" , metaData.get("isLast")) ;
		mv.setViewName("home") ;
		return mv ;
	}
	
	
	@RequestMapping("next")
	public ModelAndView next(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		init(mv , request) ;
		List<ItemsDTO> itemsList = itemService.findAllItemsNextPage() ;
		Map<String , Object> metaData = itemService.metaData() ;
		
		mv.addObject("itemsList" , itemsList) ;
		mv.addObject("isFirst" , metaData.get("isFirst")) ;
		mv.addObject("isLast" , metaData.get("isLast")) ;
		mv.setViewName("home") ;
		return mv ;
	}
	
	
	
	@RequestMapping("previous")
	public ModelAndView previous(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		init(mv , request) ;
		List<ItemsDTO> itemsList = itemService.findAllItemsPreviousPage() ;
		Map<String , Object> metaData = itemService.metaData() ;
		
		mv.addObject("itemsList" , itemsList) ;
		mv.addObject("isFirst" , metaData.get("isFirst")) ;
		mv.addObject("isLast" , metaData.get("isLast")) ;
		mv.setViewName("home") ;
		return mv ;
	}
	
	
	
	@RequestMapping("lang")
	public ModelAndView changeLanguage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		init(mv , request) ;
		int type = Integer.parseInt(request.getParameter("type") == null ? "1" : request.getParameter("type")) ;
		if(type == 1) {
			session.setAttribute("language" , "En") ;
		}else {
			session.setAttribute("language" , "Ar") ;
		}
		mv.setViewName("redirect: " + request.getHeader("referer"));
		return mv ;
	}
	
	private void init(ModelAndView mv , HttpServletRequest request) {		
		HttpSession session = request.getSession() ;
		
		session.setAttribute("company" , new CompanyProfile());
		
		if(session.getAttribute("customer") != null) {
			CustomersDTO dto = (CustomersDTO) session.getAttribute("customer") ;
			
			if(dto != null) {
				List<OrderRequestDTO> orderRequestList = customerOrders(dto.getId() , DomainValues.OrderStatus.ADD_TO_CART , session) ;
				session.setAttribute("orderRequestList" , orderRequestList);
				session.setAttribute("orderRequestListSize" , orderRequestList.size());
			}
		}
	}
	
}
