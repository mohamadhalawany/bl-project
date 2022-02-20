package com.bl.controllers.cms;

import java.util.ArrayList;
import java.util.Date;
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

import com.bl.HelperUtils;
import com.bl.controllers.BaseController;
import com.bl.dto.CustomersDTO;
import com.bl.dto.GeneralDTO;
import com.bl.service.CustomersService;
import com.bl.service.GeneralService;


@Controller(value = "customers")
public class CMSCustomerControllers extends BaseController {

	private String loginPage = "login" ;
	private String customerPage = "customers/customer" ;
	private String searchPage = "customers/search" ;
	
	private List<CustomersDTO> list = new ArrayList<CustomersDTO>() ;
	private List<GeneralDTO> countryList = new ArrayList<GeneralDTO>() ;
	private List<GeneralDTO> governorateList = new ArrayList<GeneralDTO>() ;
	private List<GeneralDTO> cityDistrictList = new ArrayList<GeneralDTO>() ;
	
	@Autowired
	private CustomersService service ;
	
	@Autowired
	private GeneralService generalService ;
	
	
	@RequestMapping("aao/customer")
	public ModelAndView customer(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		session.setAttribute("countryId" , null) ;
		
		if(session.getAttribute("user") != null) {
			countryList = generalService.countryList() ;
			governorateList = new ArrayList<GeneralDTO>() ;
			cityDistrictList = new ArrayList<GeneralDTO>() ;
						
			mv.addObject("countryList" , countryList) ;
			mv.addObject("governorateList" , governorateList) ;
			mv.addObject("cityDistrictList" , cityDistrictList) ;
			mv.setViewName(customerPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/govs")
	public ModelAndView govs(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("user") != null) {
			Integer countryId = Integer.parseInt(request.getParameter("countryId")) ;
			session.setAttribute("countryId" , countryId) ;

			governorateList = generalService.governorateList(countryId) ;
			cityDistrictList = new ArrayList<GeneralDTO>() ;
			
			mv.addObject("countryId" , countryId) ;
			mv.addObject("countryList" , countryList) ;
			mv.addObject("governorateList" , governorateList) ;
			mv.addObject("cityDistrictList" , cityDistrictList) ;
			mv.setViewName(customerPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	
	
	@RequestMapping("aao/cityDist")
	public ModelAndView cityDist(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("user") != null) {
			Integer countryId = Integer.parseInt(session.getAttribute("countryId") == null ? "60" : session.getAttribute("countryId").toString()) ;
			Integer governorateId = Integer.parseInt(request.getParameter("governorateId")) ;
			
			cityDistrictList = generalService.cityDistrictList(governorateId) ;
			
			mv.addObject("countryId" , countryId) ;
			mv.addObject("governorateId" , governorateId) ;
			mv.addObject("countryList" , countryList) ;
			mv.addObject("governorateList" , governorateList) ;
			mv.addObject("cityDistrictList" , cityDistrictList) ;
			mv.setViewName(customerPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}

	
	
	
	@RequestMapping(value = "aao/save" , method = RequestMethod.POST , params = "save")
	public ModelAndView save(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			String address = request.getParameter("address") ;
			Integer cityDistrictId = Integer.parseInt(request.getParameter("cityDistrictId")) ;
			Integer customerType = Integer.parseInt(request.getParameter("customerType")) ;
			String email = request.getParameter("email") ;
			String fullName = request.getParameter("fullName") ;
			Integer isTax = Integer.parseInt(request.getParameter("isTax") == null ? "0" : "1") ;
			String mobile = request.getParameter("mobile") ;
			String password = HelperUtils.encrypt(request.getParameter("password")) ;
			String phone = request.getParameter("phone") ;
			
			CustomersDTO dto = new CustomersDTO() ;
			dto.setAddress(address) ;
			dto.setCityDistrictId(cityDistrictId) ;
			dto.setCustomerType(customerType) ;
			dto.setEmail(email) ;
			dto.setFullName(fullName) ;
			dto.setIsTax(isTax) ;
			dto.setMobile(mobile) ;
			dto.setPassword(password) ;
			dto.setPhone(phone) ;
			
			Long count = service.countByEmail(email) ;
			
			if(count > 0L) {
				mv.addObject("count" , 1) ;
				mv.addObject("saved" , null) ;
			}else {
				dto.setUpdatedBy(0) ;
				dto.setUpdatedDate(new Date());
				dto.setCreatedBy(0);
				dto.setCreatedDate(new Date());		
				service.save(dto) ;
				mv.addObject("saved" , 1) ;
			}
			mv.addObject("dto" , dto) ;
			mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER)) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		
		return mv ;
	}

	
	
	
	@RequestMapping("aao/searchCustomers")
	public ModelAndView search(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		session.setAttribute("countryId" , null) ;
		
		if(session.getAttribute("user") != null) {
			countryList = generalService.countryList() ;
			governorateList = new ArrayList<GeneralDTO>() ;
			cityDistrictList = new ArrayList<GeneralDTO>() ;
			list = service.findAll() ;
			
			Map<String , Object> metaData = service.metaData() ;
						
			mv.addObject("list" , service.findAll()) ;
			mv.addObject("countryList" , countryList) ;
			mv.addObject("governorateList" , governorateList) ;
			mv.addObject("cityDistrictList" , cityDistrictList) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	
	
	@RequestMapping("aao/govsSearch")
	public ModelAndView govsSearch(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("user") != null) {
			Integer countryId = Integer.parseInt(request.getParameter("countryId")) ;
			session.setAttribute("countryId" , countryId) ;

			governorateList = generalService.governorateList(countryId) ;
			cityDistrictList = new ArrayList<GeneralDTO>() ;
			list = service.findAll() ;
			
			Map<String , Object> metaData = service.metaData() ;
			
			mv.addObject("list" , list) ;
			mv.addObject("countryId" , countryId) ;
			mv.addObject("countryList" , countryList) ;
			mv.addObject("governorateList" , governorateList) ;
			mv.addObject("cityDistrictList" , cityDistrictList) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	
	
	@RequestMapping("aao/cityDistSearch")
	public ModelAndView cityDistSearch(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("user") != null) {
			Integer countryId = Integer.parseInt(session.getAttribute("countryId") == null ? "60" : session.getAttribute("countryId").toString()) ;
			Integer governorateId = Integer.parseInt(request.getParameter("governorateId")) ;
			
			cityDistrictList = generalService.cityDistrictList(governorateId) ;
			list = service.findAll() ;
			
			Map<String , Object> metaData = service.metaData() ;
			
			mv.addObject("list" , list) ;
			mv.addObject("countryId" , countryId) ;
			mv.addObject("governorateId" , governorateId) ;
			mv.addObject("countryList" , countryList) ;
			mv.addObject("governorateList" , governorateList) ;
			mv.addObject("cityDistrictList" , cityDistrictList) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	
	
	@RequestMapping(value = "aao/doSearchCustomers" , method = RequestMethod.POST , params = "search")
	public ModelAndView doSearchCustomers(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			countryList = generalService.countryList() ;
			governorateList = new ArrayList<GeneralDTO>() ;
			cityDistrictList = new ArrayList<GeneralDTO>() ;
			
			String email = null ;
			String fullName = null ;
			Integer customerType = null ;
			Integer cityDistrictId = null ;
			Integer governorateId = null ;
			Integer countryId = null ;
			int language = 1 ;

			if(session.getAttribute("language") != null) {
				if(session.getAttribute("language").equals("En")) {
					language = 2 ;
				}else {
					language = 1 ;
				}
			}
			System.err.println(language + " =================== language");
			if(request.getParameter("fullName") != null && !request.getParameter("fullName").equals("")) {
				fullName = request.getParameter("fullName") ;
			}
			
			if(request.getParameter("email") != null && !request.getParameter("email").equals("")) {
				email = request.getParameter("email") ;
			}
			
			if(request.getParameter("customerType") != null && !request.getParameter("customerType").equals("0")) {
				customerType = Integer.parseInt(request.getParameter("customerType")) ;
			}else {
				customerType = null ;
			}
			
			if(request.getParameter("cityDistrictId") != null && !request.getParameter("cityDistrictId").equals("0")) {
				cityDistrictId = Integer.parseInt(request.getParameter("cityDistrictId")) ;
			}else {
				cityDistrictId = null ;
			}
			
			if(request.getParameter("governorateId") != null && !request.getParameter("governorateId").equals("0")) {
				governorateId = Integer.parseInt(request.getParameter("governorateId")) ;
			}else {
				governorateId = null ;
			}
			
			if(request.getParameter("countryId") != null && !request.getParameter("countryId").equals("0")) {
				countryId = Integer.parseInt(request.getParameter("countryId")) ;
			}else {
				countryId = null ;
			}
			
			CustomersDTO dto = new CustomersDTO() ;
			dto.setEmail(email) ;
			dto.setFullName(fullName) ;
			dto.setCustomerType(customerType) ;
			dto.setCityDistrictId(cityDistrictId) ;
			dto.setGovernorateId(governorateId) ;
			dto.setCountryId(countryId) ;
						
			list = service.search(dto , language) ;
			
			Map<String , Object> metaData = service.metaData() ;
			
			mv.addObject("dto" , dto) ;
			mv.addObject("list" , list) ;
			mv.addObject("countryList" , countryList) ;
			mv.addObject("governorateList" , governorateList) ;
			mv.addObject("cityDistrictList" , cityDistrictList) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping(value = "aao/doSearchCustomers" , method = RequestMethod.POST , params = "reset")
	public ModelAndView doResetSearchCustomers(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		session.setAttribute("countryId" , null) ;
		
		if(session.getAttribute("user") != null) {
			countryList = generalService.countryList() ;
			governorateList = new ArrayList<GeneralDTO>() ;
			cityDistrictList = new ArrayList<GeneralDTO>() ;
			list = service.findAll() ;
			
			Map<String , Object> metaData = service.metaData() ;
			
			mv.addObject("list" , list) ;
			mv.addObject("countryList" , countryList) ;
			mv.addObject("governorateList" , governorateList) ;
			mv.addObject("cityDistrictList" , cityDistrictList) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	

	public List<GeneralDTO> getCountryList() {
		return countryList;
	}



	public void setCountryList(List<GeneralDTO> countryList) {
		this.countryList = countryList;
	}



	public List<GeneralDTO> getGovernorateList() {
		return governorateList;
	}



	public void setGovernorateList(List<GeneralDTO> governorateList) {
		this.governorateList = governorateList;
	}



	public List<GeneralDTO> getCityDistrictList() {
		return cityDistrictList;
	}



	public void setCityDistrictList(List<GeneralDTO> cityDistrictList) {
		this.cityDistrictList = cityDistrictList;
	}



	public List<CustomersDTO> getList() {
		return list;
	}



	public void setList(List<CustomersDTO> list) {
		this.list = list;
	}
	
}
