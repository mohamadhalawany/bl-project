package com.bl.controllers.cms;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bl.DomainValues;
import com.bl.HelperUtils;
import com.bl.dto.ItemsDTO;
import com.bl.dto.cms.ItemOffersDTO;
import com.bl.dto.cms.LoginUserDTO;
import com.bl.dto.cms.OffersDTO;
import com.bl.service.ItemService;
import com.bl.service.cms.ItemOffersService;
import com.bl.service.cms.OffersService;

@Controller
public class CmsOffersController {
	
	private String offersPage = "offers/offers" ;
	private String editOffersPage = "offers/edit" ;
	private String itemOfferPage = "offers/item" ;
	private String loginPage = "login" ;
	
	@Autowired
	private OffersService service ;
	@Autowired
	private ItemOffersService itemOffersService ;
	@Autowired
	private ItemService itemService ;	
	
	private OffersDTO dto  ;
	
	
	@RequestMapping("aao/offers")
	public ModelAndView offers(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			session.setAttribute("searchNextPrevious" , 1) ;
			
			List<OffersDTO> list = service.findAll() ;
			Map<String , Object> metaData = service.metaData() ; 
					
			mv.addObject("list" , list) ;
			mv.addObject("dto" , dto) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(offersPage) ;
		}else {
			session.setAttribute("searchNextPrevious" , null) ;
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/nextOffer")
	public ModelAndView nextOffer(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			List<OffersDTO> list = service.next() ;
			Map<String , Object> metaData = service.metaData() ; 
					
			mv.addObject("list" , list) ;
			mv.addObject("dto" , dto) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(offersPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/previousOffer")
	public ModelAndView previousOffer(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			List<OffersDTO> list = service.previous() ;
			Map<String , Object> metaData = service.metaData() ; 
					
			mv.addObject("list" , list) ;
			mv.addObject("dto" , dto) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(offersPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping(value = "aao/searchOffers" , method = RequestMethod.GET , params = "search")
	public ModelAndView searchOffers(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			session.setAttribute("searchNextPrevious" , null) ;
			
			String offerName = null ;
			Integer isActive = null ;
			String from = null ;
			String to = null ;
			
			if(request.getParameter("offerName") != null && !request.getParameter("offerName").equals("")) {
				offerName = request.getParameter("offerName") ;
			}
			if(request.getParameter("isActive") != null && !request.getParameter("isActive").equals("") && !request.getParameter("isActive").equals("3")) {
				isActive = Integer.parseInt(request.getParameter("isActive")) ;
			}
			if(request.getParameter("validFrom") != null && !request.getParameter("validFrom").equals("")) {
				from = request.getParameter("validFrom") ;
			}
			if(request.getParameter("validTo") != null && !request.getParameter("validTo").equals("")) {
				to = request.getParameter("validTo") ;
			}
			
			dto = new OffersDTO() ;
			dto.setOfferName(offerName) ;
			dto.setFrom(from) ;
			dto.setTo(to) ;
			dto.setIsActive(isActive) ;
			
			List<OffersDTO> list = service.search(dto) ;			
			
			mv.addObject("list" , list) ;
			mv.addObject("dto" , dto) ;
			mv.addObject("total" ,  list.size()) ;
			mv.setViewName(offersPage) ;
		}else {
			session.setAttribute("searchNextPrevious" , null) ;
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
		
	
	
	@RequestMapping(value = "aao/searchOffers" , method = RequestMethod.GET , params = "reset")
	public ModelAndView resetSearchOffers(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {		
			session.setAttribute("searchNextPrevious" , 1) ;
			mv.setViewName("redirect: offers") ;
		}else {
			session.setAttribute("searchNextPrevious" , null) ;
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping(value = "aao/searchOffers" , method = RequestMethod.GET , params = "add")
	public ModelAndView addOffers(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {		
			mv.addObject("dto" , new OffersDTO()) ;
			mv.setViewName(editOffersPage) ;
		}else {
			session.setAttribute("searchNextPrevious" , null) ;
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/activeOffer")
	public ModelAndView activeOffer(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			if(request.getParameter("id") != null) {
				LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
				
				Integer id = Integer.parseInt(request.getParameter("id")) ;
				Integer isActive = Integer.parseInt(request.getParameter("flag")) ;
				OffersDTO dto = new OffersDTO() ;
				dto.setId(id) ;
				dto.setIsActive(isActive) ;
				dto.setCreatedBy(user.getId()) ;
				service.saveActive(dto) ;
			}
			mv.setViewName("redirect: offers") ;
		}else {
			session.setAttribute("searchNextPrevious" , null) ;
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}


	
	@RequestMapping(value = "aao/saveOffers" , method = RequestMethod.POST , params = "save")
	public ModelAndView saveOffers(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			OffersDTO dto = null ;
			Integer id = null ;
			if(request.getParameter("id") != null) {
				id = Integer.parseInt(request.getParameter("id")) ;
				dto = service.findById(id) ;
				dto.setUpdatedBy(user.getId()) ;
				dto.setUpdatedDate(new Date()) ;
			}else {
				dto = new OffersDTO() ;
				dto.setCreatedBy(user.getId()) ;
				dto.setCreatedDate(new Date()) ;
			}
			String offerName = request.getParameter("offerName") ;
			Integer isActive = Integer.parseInt(request.getParameter("isActive")) ;
			String validFrom = request.getParameter("validFrom") ;
			String validTo = request.getParameter("validTo") ;
			Integer isPercent = Integer.parseInt(request.getParameter("isPercent")) ;
			Double offerValue = Double.parseDouble(request.getParameter("offerValue").equals("") ? "0.0" : request.getParameter("offerValue")) ;
			
			int before = HelperUtils.dateBeforeDate(validFrom , validTo) ;
			if(before == 0) {
				mv.addObject("invalidDate" , 1) ;
			}else if(offerValue <= 0.0) {
				mv.addObject("offerValueError" , 1) ;
			}else if(offerValue >= 100.0 && isPercent == 1){
				mv.addObject("offerValueIsPercentError" , 1) ;
			}else{			
				dto.setOfferName(offerName) ;
				dto.setIsActive(isActive) ;
				dto.setValidFrom(HelperUtils.convertStringToDate(validFrom)) ;
				dto.setValidTo(HelperUtils.convertStringToDate(validTo)) ;
				dto.setIsPercent(isPercent) ;
				dto.setOfferValue(offerValue) ;
				dto.setFrom(validFrom) ;
				dto.setTo(validTo) ;
				int saved = service.save(dto) ;
				mv.addObject("saved" , saved) ;
			}
			
			mv.addObject("dto", dto) ;			
			mv.setViewName(editOffersPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping(value = "aao/saveOffers" , method = RequestMethod.POST , params = "cancel")
	public ModelAndView cancelSaveOffers(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			mv.setViewName("redirect: offers") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/editOffer")
	public ModelAndView editOffer(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			Integer id = null ;
			if(request.getParameter("id") != null) {
				id = Integer.parseInt(request.getParameter("id")) ;
				dto = service.findById(id) ;
				mv.addObject("dto" , dto) ;
			}
			mv.setViewName(editOffersPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping(value = "aao/tempOffer" , method = RequestMethod.GET)
	public ModelAndView tempOffer(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			if(request.getParameter("offerId") != null) {
				Integer offerId = Integer.parseInt(request.getParameter("offerId")) ;				
				Long itemId = Long.parseLong(session.getAttribute("item").toString()) ;
				List<OffersDTO> availableOffersList = itemOffersService.availableOffers(itemId) ;
				ItemsDTO item = itemService.findById(itemId) ;
				mv.addObject("item" , item) ;
				mv.addObject("availableOffersList" , availableOffersList) ;
				
				if(offerId != 0) {
					OffersDTO offer = service.findById(offerId) ;					
					mv.addObject("offer" , offer) ;					
					
					Double itemPrice = item.getItemPrice() ;
					Double offerValue = offer.getOfferValue() ;
					Integer isPercent = offer.getIsPercent() ;
					Double itemPriceTemp = 0.0 ;
					if(isPercent == 0) {
						itemPriceTemp = itemPrice - offerValue ;
					}else {
						Double percentPrice = itemPrice * (offerValue / 100) ;
						itemPriceTemp = itemPrice - percentPrice ;
					}
					
					if(itemPriceTemp <= 0.0) {
						mv.addObject("itemPriceTempError" , 1) ;
					}
					ItemOffersDTO dto = new ItemOffersDTO() ;
					dto.setItemPriceTemp(itemPriceTemp) ;
					mv.addObject("dto" , dto) ;
				}else {
					ItemOffersDTO dto = new ItemOffersDTO() ;
					OffersDTO offer = new OffersDTO() ;
					mv.addObject("offer" , offer) ;
					mv.addObject("dto" , dto) ;
				}
				
				mv.setViewName(itemOfferPage) ;
			}
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping(value = "aao/saveItemOffers" , method = RequestMethod.POST , params = "save")
	public ModelAndView saveItemOffers(HttpServletRequest request , @RequestParam("fileItemLogo") MultipartFile fileItemLogo) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			if(request.getParameter("offerId") == null || request.getParameter("offerId").equals("0") || request.getParameter("offerId").equals("")) {
				mv.addObject("offerIdError" , 1) ;
			}else {
				LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
				ItemOffersDTO dto = new ItemOffersDTO() ;
				
				Long itemId = Long.parseLong(session.getAttribute("item").toString()) ;
				Integer offerId = Integer.parseInt(request.getParameter("offerId")) ;
				Double itemPriceTemp = Double.parseDouble(request.getParameter("itemPriceTemp")) ;			
				String itemLogo = null ;
				String extension = null ;
				
				if(fileItemLogo != null && !fileItemLogo.isEmpty()) {
					String fileName = fileItemLogo.getOriginalFilename() ;
					itemLogo = fileName.substring(0 , fileName.lastIndexOf(".")) ;
					extension = fileName.substring(fileName.lastIndexOf(".") + 1 , fileName.length()) ;
					itemLogo = fileName ;
					if(!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("jpeg") && !extension.equalsIgnoreCase("png")) {
						mv.addObject("extensionError" , 1) ;
						mv.addObject("saved" , null) ;
						mv.setViewName("redirect: createItemOffer") ;
					}else if(fileItemLogo.getSize() > (10 * 1024 * 1024)) {
						mv.addObject("sizeError" , 1) ;
						mv.addObject("saved" , null) ;
						mv.setViewName("redirect: createItemOffer") ;
					}else {
						itemLogo = new Date().getTime() + "_" + user.getId() + "." + extension ;
						HelperUtils.upload(itemLogo , fileItemLogo) ;
						dto.setItemLogo(itemLogo) ;
						dto.setCreatedBy(user.getId()) ;
						dto.setCreatedDate(new Date()) ;
						dto.setItemId(itemId) ;
						dto.setOfferId(offerId) ;
						dto.setItemPriceTemp(itemPriceTemp) ;
						int saved = itemOffersService.save(dto) ;
						mv.addObject("saved" , saved) ;
						mv.setViewName("redirect: showtabs?tab=4") ;
					}
				}else {
					dto.setCreatedBy(user.getId()) ;
					dto.setCreatedDate(new Date()) ;
					dto.setItemId(itemId) ;
					dto.setOfferId(offerId) ;
					dto.setItemPriceTemp(itemPriceTemp) ;
					int saved = itemOffersService.save(dto) ;
					mv.addObject("saved" , saved) ;
					mv.setViewName("redirect: showtabs?tab=4") ;
				}
			}			
			
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}

	
	
	@RequestMapping(value = "aao/saveItemOffers" , method = RequestMethod.POST , params = "cancel")
	public ModelAndView cancelSaveItemOffers(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			mv.setViewName("redirect: showtabs?tab=4") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}

	
	
	@RequestMapping("aao/deleteItemOffers")
	public ModelAndView deleteItemOffers(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			if(request.getParameter("id") != null) {
				LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
				Integer id = Integer.parseInt(request.getParameter("id")) ;
				int delete = itemOffersService.delete(id , user.getId()) ;
				mv.addObject("delete" , delete) ;
			}
			mv.setViewName("redirect: showtabs?tab=4") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}

	
	

	public OffersDTO getDto() {
		return dto;
	}




	public void setDto(OffersDTO dto) {
		this.dto = dto;
	}
}
