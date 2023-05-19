package com.bl.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bl.CompanyProfile;
import com.bl.DomainValues;
import com.bl.HelperUtils;
import com.bl.dto.CustomersDTO;
import com.bl.dto.GeneralDTO;
import com.bl.dto.OrderDTO;
import com.bl.dto.OrderItemDTO;
import com.bl.dto.OrderStatusDTO;
import com.bl.dto.cms.ContactFormDTO;
import com.bl.service.CustomersService;
import com.bl.service.GeneralService;
import com.bl.service.OrderService;
import com.bl.service.cms.ContactFormService;



@Controller
public class CustomersController extends BaseController{

	private String loginPage = "pages/customer/login" ;
	private String accountPage = "pages/customer/account" ;
	private String contactFormPage = "contact_form" ;
	
	@Autowired
	private CustomersService service ;	 
	@Autowired
	private GeneralService generalService ;	
	@Autowired
	private OrderService orderService ;
	@Autowired
	private ContactFormService contactFormService ;

	
	@RequestMapping(value = "customerLogin" , method = RequestMethod.POST)
	public ModelAndView customerLogin(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		
		HttpSession session = request.getSession() ;
		
		String email = request.getParameter("email") ;
		String password = request.getParameter("password") ;
		CustomersDTO dto = service.findByEmailAndPassword(email , password) ;

		if(dto != null) {
			session.setAttribute("customerLoginForm" , 1);
			session.setAttribute("customerLoginFaildMsg" , null);
			session.setAttribute("customer" , dto) ;
			mv.setViewName("redirect: home") ;
		}else {
			session.setAttribute("customerLoginForm" , null);
			session.setAttribute("customerLoginFaildMsg" , 1);
			mv.setViewName(loginPage);
		}
		
		return mv ;
	}
	
	@RequestMapping("customerAccount")
	public ModelAndView loginCustomer(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("customer") == null) {			
			mv.setViewName(loginPage) ;
		}else {
			CustomersDTO dto = (CustomersDTO) session.getAttribute("customer") ;			
			if(dto != null) {				
				init(mv) ;
				List<GeneralDTO> governorateList = generalService.governorateList(dto.getCountryId()) ;
				List<GeneralDTO> cityDistrictList = generalService.cityDistrictList(dto.getGovernorateId()) ;
				
				session.setAttribute("customerLoginForm" , 1) ;
				session.setAttribute("customerLoginFaildMsg" , null) ;
				
				mv.addObject("dto" , dto) ;
				mv.addObject("governorateList" , governorateList) ;
				mv.addObject("cityDistrictList" , cityDistrictList) ;
			}
			mv.setViewName(accountPage) ;
		}		
		return mv ;
	}
		
	@RequestMapping("register")
	public ModelAndView register(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		session.setAttribute("company" , new CompanyProfile());
		mv.addObject("customer" , new CustomersDTO()) ;
		init(mv) ;
		mv.setViewName(accountPage) ;	
		return mv ;
	}
	
	@RequestMapping("govs")
	public ModelAndView govs(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		init(mv) ;
		
		if(request.getParameter("countryId") != null) {
			Integer countryId = Integer.parseInt(request.getParameter("countryId")) ;
			session.setAttribute("countryId" , countryId) ;
			
			mv.addObject("governorateList" , generalService.governorateList(countryId)) ;
			
			if(session.getAttribute("customer") == null) {
				CustomersDTO dto = new CustomersDTO() ;
				dto.setCountryId(countryId);
				mv.addObject("customer" , dto) ;				
			}else {
				CustomersDTO dto = (CustomersDTO) session.getAttribute("customer") ;
				dto.setCountryId(countryId);
				mv.addObject("customer" , dto) ;	
			}
		}
		mv.setViewName(accountPage) ;
		return mv ;
	}
	
	@RequestMapping("cityDist")
	public ModelAndView cityDist(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		init(mv) ;
		
		if(request.getParameter("governorateId") != null) {
			Integer countryId = Integer.parseInt(session.getAttribute("countryId") == null ? "60" : session.getAttribute("countryId").toString()) ;
			Integer governorateId = Integer.parseInt(request.getParameter("governorateId")) ;
			
			mv.addObject("governorateList" , generalService.governorateList(countryId)) ;
			mv.addObject("cityDistrictList" , generalService.cityDistrictList(governorateId)) ;
			
			if(session.getAttribute("customer") == null) {
				CustomersDTO dto = new CustomersDTO() ;
				dto.setCountryId(countryId);
				dto.setGovernorateId(governorateId) ;
				mv.addObject("customer" , dto) ;				
			}else {
				CustomersDTO dto = (CustomersDTO) session.getAttribute("customer") ;
				dto.setCountryId(countryId);
				dto.setGovernorateId(governorateId) ;
				mv.addObject("customer" , dto) ;	
			}
		}
		mv.setViewName(accountPage) ;
		return mv ;
	}
	
	@RequestMapping("logoutCustomer")
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		session.setAttribute("company" , new CompanyProfile());
		session.setAttribute("customer" , null) ;
		session.setAttribute("orderRequestList" , null) ;
		session.setAttribute("orderRequestListSize" , null) ;
		session.setAttribute("totalPrice" , null) ;
		session.setAttribute("currencyName" , null) ;
		session.setAttribute("currencyNameAr" , null) ;
		session.setAttribute("currencyCode" , null) ;
		session.invalidate() ;
		mv.setViewName("redirect: home") ;
		return mv ;
	}
	
	@RequestMapping(value =  "reg" , method = RequestMethod.POST)
	public ModelAndView reg(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		session.setAttribute("company" , new CompanyProfile());
		
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
		}else {
			if(session.getAttribute("customer") == null) {			
				dto.setCreatedBy(0);
				dto.setCreatedDate(new Date());	
				service.save(dto) ;
				mv.addObject("saved" , 1) ;
			}else {
				CustomersDTO loggedId = (CustomersDTO) session.getAttribute("customer") ;						
				dto.setUpdatedBy(0) ;
				dto.setUpdatedDate(new Date());
				dto.setId(loggedId.getId()) ;			
				service.save(dto) ;
				mv.addObject("saved" , 1) ;
			}
		}
		mv.addObject("customer" , dto) ;
		mv.setViewName(accountPage) ;
		return mv ;
	}
	
	@RequestMapping("addToCart")
	public ModelAndView addToCart(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("customer") == null) {
			mv.setViewName(loginPage) ;
		}else {
			CustomersDTO customersDTO = (CustomersDTO) session.getAttribute("customer") ;
			Long customerId = customersDTO.getId() ; 			
			Double itemPrice = null ;
			
			if(request.getParameter("itemPrice") != null && !request.getParameter("itemPrice").equals("")) {
				itemPrice = Double.parseDouble(request.getParameter("itemPrice").equals("") ? "0.0" : request.getParameter("itemPrice")) ;
			}else {
				itemPrice = Double.parseDouble(request.getParameter("itemPriceOffer").equals("") ? "0.0" : request.getParameter("itemPriceOffer")) ;
			}
			
			Long itemId = Long.parseLong(request.getParameter("itemId").equals("") ? "0" : request.getParameter("itemId")) ;
			Integer quantity = Integer.parseInt(request.getParameter("quantity").equals("") ? "1" : request.getParameter("quantity")) ;
			Integer currencyId = Integer.parseInt(request.getParameter("currencyId").equals("") ? "48" : request.getParameter("currencyId")) ;
			
			OrderDTO orderDTO = orderService.findByCustomerIdAndOrderStatusId(customerId, DomainValues.OrderStatus.ADD_TO_CART) ;
			if(orderDTO == null) {
				orderDTO = new OrderDTO() ;
			}			
			orderDTO.setCustomerId(customerId) ;
			orderDTO.setOrderStatusId(DomainValues.OrderStatus.ADD_TO_CART) ;
			orderDTO.setOrderDate(new Date()) ;		
			orderDTO.setTotalPrice(itemPrice * quantity) ;
			orderDTO.setCurrencyId(currencyId) ;
			
			
			OrderItemDTO orderItemDTO = new OrderItemDTO() ;
			orderItemDTO.setItemId(itemId) ;
			orderItemDTO.setItemPrice(itemPrice) ;
			orderItemDTO.setQuantity(quantity) ;
			
			OrderStatusDTO orderStatusDTO = new OrderStatusDTO() ;			
			orderStatusDTO.setStatusDate(new Date()) ;
			orderStatusDTO.setStatusId(DomainValues.OrderStatus.ADD_TO_CART) ;
			
			Long orderNumber = service.saveOrder(orderDTO, orderItemDTO, orderStatusDTO) ;
			
			mv.addObject("orderNumber" , orderNumber) ;
			mv.addObject("quantity" , quantity) ;
			mv.addObject("saved" , 1) ;
			mv.setViewName("redirect: item?id=" + itemId) ;
		}
		return mv ;
	}
	
	@RequestMapping(value = "saveContactForm" , method = RequestMethod.POST)
	public ModelAndView saveContactForm(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		ContactFormDTO dto = new ContactFormDTO() ;
		
		String customerMail = "" ;
		String customerName = "" ;
		String message = "" ;
		
		if(request.getParameter("customerEmail") != null && !request.getParameter("customerEmail").equals("")) {			
			customerMail = request.getParameter("customerEmail") ;
			if(!HelperUtils.validatEmail(customerMail)) {
				mv.addObject("emailError" , 1) ;
				dto.setCustomerMail(customerMail); 
				dto.setCustomerName(request.getParameter("customerName"));
				dto.setMessage(request.getParameter("message"));
				mv.addObject("contactDto" , dto) ;
				mv.setViewName(contactFormPage) ;
				return mv ;
			}
			dto.setCustomerMail(customerMail) ;
		}
		if(request.getParameter("customerName") != null && !request.getParameter("customerName").equals("")) {
			customerName = request.getParameter("customerName") ;
			dto.setCustomerName(customerName) ;
		}
		if(request.getParameter("message") != null && !request.getParameter("message").equals("")) {
			message = request.getParameter("message") ;
			dto.setMessage(message) ;
		}
		
		contactFormService.save(dto) ;
		mv.addObject("contactDto" , dto) ;
		mv.addObject("save" , 1) ;
		mv.setViewName(contactFormPage) ;
		return mv ;
	}
	
	private void init(ModelAndView mv) {
		mv.addObject("countryList" , generalService.countryList()) ;
	}
}
