package com.bl.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.bl.service.cms.ItemBrandService;



@Controller
public class ItemsController {
	
	private String itemPage = "pages/item" ;
	private String categoryPage = "pages/category" ;
	private String searchResultPage = "pages/SearchResult" ;
	
	@Autowired
	private ItemService service ;	
	@Autowired
	private ItemSpecificationService itemSpecificationService ;	
	@Autowired
	private CustomerReviewsService customerReviewsService ;
	@Autowired
	private ItemBrandService itemBrandService ;
			
	private List<CustomerReviewsDTO> reviewsList = new ArrayList<CustomerReviewsDTO>() ;
	private List<ItemsDTO> relatedItems ;
	
	@RequestMapping("item")
	public ModelAndView viewItemDetails(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		session.setAttribute("company" , new CompanyProfile());
		
		Long id = Long.parseLong(request.getParameter("id") == null ? "0" : request.getParameter("id")) ;

		ItemsDTO dto = service.itemDetails(id) ;
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
	
	
	@RequestMapping("category")
	public ModelAndView category(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		Long categoryId = Long.parseLong(request.getParameter("id")) ;
		mv.addObject("itemsList" , service.findAllByCategoryId(categoryId)) ;
		mv.setViewName(categoryPage) ;
		return mv ;
	}
	
	
	
	@RequestMapping("search")
	public ModelAndView search(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		if(request.getParameter("itemName") != null && !request.getParameter("itemName").equals("")) {
			List<ItemsDTO> items = service.findAllByItemName(request.getParameter("itemName")) ;
			mv.addObject("itemsList" , items) ;
			ItemsDTO item = new ItemsDTO() ;
			item.setItemName(request.getParameter("itemName"));
			mv.addObject("itemDTO" , item) ;
			mv.setViewName(searchResultPage) ;
		}else {
			mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER)) ;
		}		
		return mv ;
	}

	
	@RequestMapping("itemsBrand")
	public ModelAndView itemsBrand(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		if(request.getParameter("id") != null && !request.getParameter("id").equals("")) {		
			Integer brandId = Integer.parseInt(request.getParameter("id")) ;
			List<ItemsDTO> itemBrandList = itemBrandService.findAllByBrandId(brandId) ;
			mv.addObject("itemsList" , itemBrandList) ;
			mv.setViewName(categoryPage) ;
		}else {
			mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER)) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("sort")
	public ModelAndView sort(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		int sortType = 0 ;
		String itemName = "" ;
		
		if(request.getParameter("type") != null) {
			sortType = Integer.parseInt(request.getParameter("type")) ;
		}
		
		if(request.getParameter("itemName") != null) {
			itemName = request.getParameter("itemName") ;
		}
		
		List<ItemsDTO> items = service.findAllByItemName(itemName , sortType) ;
		mv.addObject("itemsList" , items) ;
		ItemsDTO item = new ItemsDTO() ;
		item.setItemName(itemName) ;
		mv.addObject("dto" , item) ;
		mv.setViewName(searchResultPage) ;
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
