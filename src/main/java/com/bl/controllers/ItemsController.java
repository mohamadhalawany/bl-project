package com.bl.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bl.CompanyProfile;
import com.bl.dto.CustomerReviewsDTO;
import com.bl.dto.ItemSpecificationsDTO;
import com.bl.dto.ItemsDTO;
import com.bl.service.CustomerReviewsService;
import com.bl.service.ItemService;
import com.bl.service.ItemSpecificationService;



@Controller
public class ItemsController {
	
	private String itemPage = "pages/item" ;
	
	@Autowired
	private ItemService service ;
	
	@Autowired
	private ItemSpecificationService itemSpecificationService ;
	
	@Autowired
	private CustomerReviewsService customerReviewsService ;
			
	private List<CustomerReviewsDTO> reviewsList = new ArrayList<CustomerReviewsDTO>() ;
	private List<ItemsDTO> relatedItems ;
	
	@RequestMapping("item")
	public ModelAndView viewItemDetails(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		session.setAttribute("company" , new CompanyProfile());
		
		Long id = Long.parseLong(request.getParameter("id") == null ? "0" : request.getParameter("id")) ;

		ItemsDTO dto = service.findById(id) ;
		ItemSpecificationsDTO itemSpecificationsDTO = itemSpecificationService.findAllByItemId(id) ;
		reviewsList = customerReviewsService.findAllByItemId(0 , id) ;
		relatedItems = service.findAllByCategoryIdAndNotEqualId(id , dto.getCategoryId()) ;
		Map<String , Object> metaData = customerReviewsService.metaData() ;
		
		mv.addObject("dto" , dto) ;
		mv.addObject("itemSpecificationDTO" , itemSpecificationsDTO) ;
		if(reviewsList != null) {			
			mv.addObject("reviewsList" , reviewsList) ;
			mv.addObject("countReviews" , reviewsList.size()) ;
		
			mv.addObject("relatedItems" , relatedItems) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
		}
		mv.addObject("id" , id) ;
		mv.addObject("saved" , 0) ;
 		mv.setViewName(itemPage) ;
		
		return mv ;
	}
	
	
	
	@RequestMapping("nextReview")
	public ModelAndView next(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		Long id = Long.parseLong(request.getParameter("id") == null ? "0" : request.getParameter("id")) ;
		
		ItemsDTO dto = service.findById(id) ;
		ItemSpecificationsDTO itemSpecificationsDTO = itemSpecificationService.findAllByItemId(id) ;
		reviewsList = customerReviewsService.findAllByItemIdNext() ;
		Map<String , Object> metaData = customerReviewsService.metaData() ;
		
		mv.addObject("dto" , dto) ;
		mv.addObject("itemSpecificationDTO" , itemSpecificationsDTO) ;
		mv.addObject("reviewsList" , reviewsList) ;
		mv.addObject("countReviews" , reviewsList.size()) ;
		mv.addObject("isFirst" , metaData.get("isFirst")) ;
		mv.addObject("isLast" , metaData.get("isLast")) ;
		
		mv.setViewName(itemPage);
		return mv ;
	}
	 
	
	
	@RequestMapping("previousReview")
	public ModelAndView previous(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		Long id = Long.parseLong(request.getParameter("id") == null ? "0" : request.getParameter("id")) ;
		
		ItemsDTO dto = service.findById(id) ;
		ItemSpecificationsDTO itemSpecificationsDTO = itemSpecificationService.findAllByItemId(id) ;
		reviewsList = customerReviewsService.findAllByItemIdPrevious() ;
		Map<String , Object> metaData = customerReviewsService.metaData() ;
		
		mv.addObject("dto" , dto) ;
		mv.addObject("itemSpecificationDTO" , itemSpecificationsDTO) ;
		mv.addObject("reviewsList" , reviewsList) ;
		mv.addObject("countReviews" , reviewsList.size()) ;
		mv.addObject("isFirst" , metaData.get("isFirst")) ;
		mv.addObject("isLast" , metaData.get("isLast")) ;
	
		mv.setViewName(itemPage);
		return mv ;
	}
	
	
	
	


	public List<CustomerReviewsDTO> getReviewsList() {
		return reviewsList;
	}



	public void setReviewsList(List<CustomerReviewsDTO> reviewsList) {
		this.reviewsList = reviewsList;
	}



	public List<ItemsDTO> getRelatedItems() {
		return relatedItems;
	}



	public void setRelatedItems(List<ItemsDTO> relatedItems) {
		this.relatedItems = relatedItems;
	}

}
