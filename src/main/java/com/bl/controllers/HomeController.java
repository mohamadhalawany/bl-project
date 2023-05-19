package com.bl.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bl.DomainValues;
import com.bl.dto.CustomersDTO;
import com.bl.dto.ItemsDTO;
import com.bl.dto.cms.BrandDTO;
import com.bl.dto.cms.CareerDTO;
import com.bl.dto.cms.CompanyTermsConditionDTO;
import com.bl.dto.cms.ContactFormDTO;
import com.bl.dto.cms.FaqDTO;
import com.bl.dto.cms.MainPageBlockDTO;
import com.bl.dto.cms.SettingDTO;
import com.bl.service.ItemService;
import com.bl.service.cms.BrandService;
import com.bl.service.cms.CareerService;
import com.bl.service.cms.FaqService;
import com.bl.service.cms.MainPageBlockService;
import com.bl.service.cms.SettingService;

@Controller
public class HomeController extends BaseController {

	@Autowired
	private ItemService itemService;
	@Autowired
	private SettingService settingService;
	@Autowired
	private MainPageBlockService mainPageBlockService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private FaqService faqService ;
	@Autowired
	private CareerService careerService ;
	
	

	@RequestMapping(value = {"/" , "home"})
	public ModelAndView home(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		if(session.getAttribute(DomainValues.SessionKeys.CUSTOMER) == null) {
			session.setAttribute("customerLoginForm" , null) ;
			session.setAttribute("customerLoginFaildMsg" , null) ;
		}
		
		init(mv, request);

		List<ItemsDTO> mostPopularList = itemService.mostPopular();
		List<BrandDTO> brandsList = brandService.findAllIsActive();
		List<ItemsDTO> lastAddedList = itemService.lastAdded() ;
		
		int lastAddedSize = 0 ;
		int mostPopularSize = 0 ;
		
		if(lastAddedList != null && !lastAddedList.isEmpty()) {
			lastAddedSize = lastAddedList.size() ;
			if(lastAddedSize > 4) {
				mv.addObject("lastAddedList", lastAddedList);
			}
		}
		if(mostPopularList != null && !mostPopularList.isEmpty()) {			
			mostPopularSize = mostPopularList.size() ;
			if(mostPopularSize > 4) {
				mv.addObject("mostPopularList", mostPopularList);
			}
		}
		
		SettingDTO settingDTO = settingService.findById(1);
		MainPageBlockDTO mainPageBlockDTO = mainPageBlockService.findById(1);

		mv.addObject("mainPageBlockDTO", mainPageBlockDTO);
		mv.addObject("mainPicture", settingDTO.getMainPicture());
		mv.addObject("mainPictureDescription", settingDTO.getMainPictureDescription());
		mv.addObject("brandsList", brandsList) ;		
		mv.addObject("lastAddedSize" , lastAddedSize) ;
		mv.addObject("mostPopularSize" , mostPopularSize) ;
		mv.setViewName("home");
		return mv;
	}

//	@RequestMapping("home")
//	public ModelAndView index(HttpServletRequest request) {
//		ModelAndView mv = new ModelAndView();
//		HttpSession session = request.getSession();
//		if(session.getAttribute(DomainValues.SessionKeys.CUSTOMER) == null) {
//			session.setAttribute("customerLoginForm" , null) ;
//			session.setAttribute("customerLoginFaildMsg" , null) ;
//		}
//		
//		init(mv, request);
//
//		List<ItemsDTO> mostPopularList = itemService.mostPopular();
//		List<BrandDTO> brandsList = brandService.findAllIsActive();
//		List<ItemsDTO> lastAddedList = itemService.lastAdded() ;
//
//		SettingDTO settingDTO = settingService.findById(1);
//		MainPageBlockDTO mainPageBlockDTO = mainPageBlockService.findById(1);
//		
//		if(lastAddedList != null) {
//			int lastAddedSize = lastAddedList.size() ;
//			if(lastAddedSize > 4) {
//				mv.addObject("lastAddedList", lastAddedList);
//			}
//		}
//		
//		if(mostPopularList != null) {
//			int mostPopularSize = mostPopularList.size() ;
//			if(mostPopularSize > 4) {
//				mv.addObject("mostPopularList", mostPopularList);
//			}
//		}
//		
//		mv.addObject("mainPageBlockDTO", mainPageBlockDTO);
//		mv.addObject("mainPicture", settingDTO.getMainPicture());
//		mv.addObject("mainPictureDescription", settingDTO.getMainPictureDescription());		
//		mv.addObject("brandsList", brandsList);
//		mv.setViewName("home");
//		return mv;
//	}

	@RequestMapping("next")
	public ModelAndView next(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		init(mv, request);
		List<ItemsDTO> itemsList = itemService.findAllItemsNextPage();
		Map<String, Object> metaData = itemService.metaData();

		SettingDTO settingDTO = settingService.findById(1);
		MainPageBlockDTO mainPageBlockDTO = mainPageBlockService.findById(1);

		mv.addObject("itemsList", itemsList);
		mv.addObject("mainPageBlockDTO", mainPageBlockDTO);
		mv.addObject("isFirst", metaData.get("isFirst"));
		mv.addObject("isLast", metaData.get("isLast"));
		mv.addObject("mainPicture", settingDTO.getMainPicture());
		mv.addObject("mainPictureDescription", settingDTO.getMainPictureDescription());
		mv.setViewName("home");
		return mv;
	}

	@RequestMapping("previous")
	public ModelAndView previous(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		init(mv, request);
		List<ItemsDTO> itemsList = itemService.findAllItemsPreviousPage();
		Map<String, Object> metaData = itemService.metaData();

		SettingDTO settingDTO = settingService.findById(1);
		MainPageBlockDTO mainPageBlockDTO = mainPageBlockService.findById(1);

		mv.addObject("itemsList", itemsList);
		mv.addObject("mainPageBlockDTO", mainPageBlockDTO);
		mv.addObject("isFirst", metaData.get("isFirst"));
		mv.addObject("isLast", metaData.get("isLast"));
		mv.addObject("mainPicture", settingDTO.getMainPicture());
		mv.addObject("mainPictureDescription", settingDTO.getMainPictureDescription());
		mv.setViewName("home");
		return mv;
	}

	@RequestMapping("lang")
	public ModelAndView changeLanguage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		init(mv, request);
		int type = Integer.parseInt(request.getParameter("type") == null ? "1" : request.getParameter("type"));
		if (type == 1) {
			session.setAttribute("language", "En");
		} else {
			session.setAttribute("language", "Ar");
		}

		mv.setViewName("redirect: " + request.getHeader("referer"));
		return mv;
	}

	@RequestMapping("faq")
	public ModelAndView faq(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		List<FaqDTO> list = faqService.findAll() ;
		mv.addObject("list" , list) ;
		mv.addObject("size" , list.size()) ;
		mv.setViewName("faq");
		return mv;
	}
	
	@RequestMapping("contact")
	public ModelAndView contact(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession() ;
		ContactFormDTO dto = new ContactFormDTO() ;
		
		if(session.getAttribute(DomainValues.SessionKeys.CUSTOMER) != null) {
			CustomersDTO customer = (CustomersDTO) session.getAttribute(DomainValues.SessionKeys.CUSTOMER) ;
			dto.setCustomerName(customer.getFullName()) ;
			dto.setCustomerMail(customer.getEmail()) ;
		}
		mv.addObject("contactDto" , dto) ;
		mv.setViewName("contact_form");
		return mv;
	}
	
	@RequestMapping("termsCondition")
	public ModelAndView termsCondition(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		CompanyTermsConditionDTO termsCondition = settingService.findTermsConditionByCompanyId(1) ;
		mv.addObject("termsConditionDTO" ,  termsCondition) ;
		mv.setViewName("terms_conditions") ;
		return mv;
	}
	
	@RequestMapping("aboutUs")
	public ModelAndView aboutUs(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("about_us") ;
		return mv;
	}
	
	@RequestMapping("career")
	public ModelAndView career(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		List<CareerDTO> list = careerService.findAll() ;
		mv.addObject("list" , list) ;
		mv.setViewName("career") ;
		return mv ;
	}
	
	private void init(ModelAndView mv, HttpServletRequest request) {
		MainPageBlockDTO mainPageBlockDTO = mainPageBlockService.findById(1);
		mv.addObject("mainPageBlockDTO", mainPageBlockDTO);
	}

}
