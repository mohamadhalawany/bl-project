package com.bl.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bl.CompanyProfile;
import com.bl.DomainValues;
import com.bl.dto.CustomersDTO;
import com.bl.dto.OrderDTO;
import com.bl.dto.OrderRequestDTO;
import com.bl.dto.OrderStatusDTO;
import com.bl.service.OrderRequestService;
import com.bl.service.OrderService;

@Controller
public class OrdersController extends BaseController{

	private String order = "pages/order/order" ;
	private String checkout = "pages/order/checkout" ;
	private String loginPage = "pages/customer/login" ;
	private String myOrders = "pages/order/MyOrders" ;
	private String track = "pages/order/track" ;
	
	@Autowired
	private OrderRequestService service ;
	
	@Autowired 
	private OrderService orderService ;


	@RequestMapping(value = "order" , method = RequestMethod.GET , params = "cart")
	public ModelAndView cart(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("customer") != null) {
			CustomersDTO dto = (CustomersDTO) session.getAttribute("customer") ;
			List<OrderRequestDTO> list = customerOrders(dto.getId() , DomainValues.OrderStatus.ADD_TO_CART , session) ;
			mv.addObject("list" , list) ;
			session.setAttribute("orderRequestListSize" , list.size());
			mv.addObject("orderNumber" , null) ;
			mv.setViewName(order) ;
		}else {
			mv.setViewName(loginPage) ;
			session.setAttribute("orderRequestListSize" , 0);
			session.setAttribute("orderRequestList" , null);
			session.setAttribute("totalPrice" , 0);
			session.setAttribute("currencyName" , null);
			session.setAttribute("company" , new CompanyProfile());
		}
		
		return mv ;
	}
	
	
	
	
	@RequestMapping("deleteOrder")
	public ModelAndView deleteOrder(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		
		if(request.getParameter("id") != null) {
			Long id = Long.parseLong(request.getParameter("id").equals("") ? "0" : request.getParameter("id")) ;
			if(id != 0L) {
				service.delete(id);
			}
		}
		mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER)) ;
		return mv ;
	}
	
	
	
	
	@RequestMapping("incdec")
	public ModelAndView incdec(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		Long orderId = 0L ;
		Long orderItemId = 0L ;
		int type = 0 ;
		
		if(request.getParameter("oiid") != null) {
			orderItemId = Long.parseLong(request.getParameter("oiid").equals("") ? "0" : request.getParameter("oiid")) ;
		}
		
		if(request.getParameter("oid") != null) {
			orderId = Long.parseLong(request.getParameter("oid").equals("") ? "0" : request.getParameter("oid")) ;
		}
		
		if(request.getParameter("type") != null) {
			type = Integer.parseInt(request.getParameter("type")) ;
			if(type == 2) {
				service.increaseQuantity(1 , orderItemId , orderId , type) ;
			}else { 
				service.decreaseQuantity(1 , orderItemId , orderId , type) ;
			}
		}		
		mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER)) ;
		return mv ;
	}
	
	
	
	
	
	@RequestMapping(value = "order" , method = RequestMethod.GET , params = "checkout")
	public ModelAndView checkout(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.CUSTOMER) != null) {			
			Long orderId = null ;
			if(request.getParameter("orderId") != null && !request.getParameter("orderId").equals("")) {
				orderId = Long.parseLong(request.getParameter("orderId")) ;
			}else {
				orderId = 48L ;
			}
			
			CustomersDTO dto = (CustomersDTO) session.getAttribute(DomainValues.SessionKeys.CUSTOMER) ;
			List<OrderRequestDTO> list = service.customerOrdersCart(dto.getId() , DomainValues.OrderStatus.ADD_TO_CART) ;
			OrderDTO orderDto = orderService.findById(orderId) ;
			
			mv.addObject("order" , orderDto) ;
			mv.addObject("list" , list) ;
			mv.addObject("orderNumber" , null) ;
			mv.setViewName(checkout) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	@RequestMapping(value = "confirm" , method = RequestMethod.POST , params = "confirm")
	public ModelAndView continueOdern(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		Integer paymentMethod = 0 ;
		Long customerId = 0L ;
		Long orderId = 0L ;
		
		if(request.getParameter("paymentMethod") != null) {
			paymentMethod = Integer.parseInt(request.getParameter("paymentMethod")) ;
		}
		
		if(request.getParameter("customerId") != null) {
			customerId = Long.parseLong(request.getParameter("customerId")) ;
		}
		
		if(request.getParameter("orderId") != null) {
			orderId = Long.parseLong(request.getParameter("orderId")) ;
		}

		OrderDTO dto = service.findById(orderId) ;
		if(dto != null) {
			dto.setId(orderId) ;
			dto.setCustomerId(customerId) ;
			dto.setOrderStatusId(DomainValues.OrderStatus.ORDERED) ;
			dto.setPaymentMethod(paymentMethod) ;
			dto.setUpdatedDate(new Date()) ;
			service.saveOrder(dto) ;
			
			OrderStatusDTO statusDto = new OrderStatusDTO() ;
			statusDto.setOrderId(orderId) ;
			statusDto.setStatusDate(new Date()) ;
			statusDto.setStatusId(DomainValues.OrderStatus.ORDERED) ;
			service.saveOrderStatus(statusDto) ;
			
			mv.addObject("saved" , 1) ;
			mv.addObject("orderNumber" , dto.getOrderNumber()) ;
		}
		
		mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER));
		return mv ;
	}
	
	
	
	
	@RequestMapping(value = "confirm" , method = RequestMethod.POST , params = "cancel")
	public ModelAndView cancel(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		
		Integer paymentMethod = 0 ;
		Long customerId = 0L ;
		Long orderId = 0L ;
		
		if(request.getParameter("paymentMethod") != null) {
			paymentMethod = Integer.parseInt(request.getParameter("paymentMethod")) ;
		}
		
		if(request.getParameter("customerId") != null) {
			customerId = Long.parseLong(request.getParameter("customerId")) ;
		}
		
		if(request.getParameter("orderId") != null) {
			orderId = Long.parseLong(request.getParameter("orderId")) ;
		}

		OrderDTO dto = service.findById(orderId) ;
		if(dto != null) {
			dto.setId(orderId) ;
			dto.setCustomerId(customerId) ;
			dto.setOrderStatusId(DomainValues.OrderStatus.CANCELLED) ;
			dto.setPaymentMethod(paymentMethod) ;
			dto.setUpdatedDate(new Date()) ;
			service.saveOrder(dto) ;
			
			OrderStatusDTO statusDto = new OrderStatusDTO() ;
			statusDto.setOrderId(orderId) ;
			statusDto.setStatusDate(new Date()) ;
			statusDto.setStatusId(DomainValues.OrderStatus.CANCELLED) ;
			service.saveOrderStatus(statusDto) ;
			
			mv.addObject("saved" , 1) ;
			mv.addObject("orderNumber" , dto.getOrderNumber()) ;
		}
		
		mv.setViewName("redirect: home");
		return mv ;
	}
	
	
	
	
	
	@RequestMapping(value = "order" , method = RequestMethod.GET , params = "myOrders")
	public ModelAndView myOrders(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("customer") != null) {
			CustomersDTO dto = (CustomersDTO) session.getAttribute("customer") ;
			List<OrderDTO> list = orderService.myOrders(dto.getId()) ;
			if(list != null && !list.isEmpty()) {
				mv.addObject("list" , list) ;
			}
			mv.addObject("orderNumber" , null) ;
			mv.setViewName(myOrders) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		
		return mv ;
	}
	
	
	
	
	
	@RequestMapping(value = "myOrders")
	public ModelAndView myOrdersHeader(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("customer") != null) {
			CustomersDTO dto = (CustomersDTO) session.getAttribute("customer") ;
			List<OrderDTO> list = orderService.myOrders(dto.getId()) ;
			if(list != null) {
				mv.addObject("list" , list) ;
			}
			mv.addObject("orderNumber" , null) ;
			mv.setViewName(myOrders) ;
		}else {
			mv.setViewName(loginPage) ;
			session.setAttribute("orderRequestListSize" , 0);
			session.setAttribute("orderRequestList" , null);
			session.setAttribute("totalPrice" , 0);
			session.setAttribute("currencyName" , null);
			session.setAttribute("company" , new CompanyProfile());
		}
		
		return mv ;
	}
	
	
	
	
	@RequestMapping("tracking")
	public ModelAndView tracking(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.CUSTOMER) != null) {
			if(request.getParameter("id") != null) {
				Long orderId = Long.parseLong(request.getParameter("id")) ;
				int language = 2 ;
				if(session.getAttribute("language") != null) {
					if(session.getAttribute("language") == "Ar") {
						language = 1 ;
					}else {
						language = 2 ;
					}
				}
				mv.addObject("list" , orderService.orderStatusList(orderId , language)) ;
				mv.setViewName(track) ;
			}
		}else {
			mv.setViewName(loginPage) ;
		}
		
		return mv ;
	}
		
}
