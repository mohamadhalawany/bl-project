package com.bl.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bl.dto.CustomerReviewsDTO;
import com.bl.service.CustomerReviewsService;


@Controller
public class CustomerReviewsController {
	
	@Autowired
	private CustomerReviewsService service ;
	
	@RequestMapping("saveReview")
	public ModelAndView save(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		
		String customerName = request.getParameter("customerName") ;
		String customerEmail = request.getParameter("customerEmail") ;
		String customerComment = request.getParameter("customerComment") ;
		Long itemId = Long.parseLong(request.getParameter("id") == null ? "0" : request.getParameter("id")) ;
		
		if(customerName.equals("") || customerComment.equals("")) {
			mv.addObject("requiredReviewData" , 1) ;
		}else {
			CustomerReviewsDTO dto = new CustomerReviewsDTO() ;
			dto.setCustomerComment(customerComment) ;
			dto.setCustomerEmail(customerEmail) ;
			dto.setCustomerName(customerName) ;
			dto.setItemId(itemId) ;
			dto.setCreatedDate(new Date()) ;
			
			Long id = service.save(dto) ;
			
			mv.addObject("requiredReviewData" , null) ;			
			if(id > 0L) {
				mv.addObject("savedReview" , 1) ;
			}
		}
		
		mv.addObject("id" , request.getParameter("id")) ;
		mv.setViewName("redirect: item");
		return mv ;
	}
}
