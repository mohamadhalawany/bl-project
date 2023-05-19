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

import com.bl.DomainValues;
import com.bl.HelperUtils;
import com.bl.dto.GeneralDTO;
import com.bl.dto.OrderDTO;
import com.bl.dto.OrderItemDTO;
import com.bl.dto.OrderRequestDTO;
import com.bl.dto.OrderStatusDTO;
import com.bl.service.OrderItemService;
import com.bl.service.OrderRequestService;
import com.bl.service.OrderService;

@Controller
public class CmsOrdersController {

	private String orderPage = "orders/orders" ;
	private String newOrdersPage = "orders/new_orders" ;
	private String orderDetailsPage = "orders/details" ;
	private String orderTrackingPage = "orders/tracking" ;
	private String loginPage = "login" ;
	
	@Autowired
	private OrderRequestService service ;	
	@Autowired
	private OrderItemService orderItemService ;	
	@Autowired
	private OrderService orderService ;
	
	private OrderRequestDTO dto ;
	
	private List<GeneralDTO> orderStatus ;
	
	@RequestMapping("aao/orders")
	public ModelAndView orders(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute(DomainValues.SessionKeys.USER)  != null) {
			List<OrderDTO> list = service.findAllOrdersOrdered() ;
			fillOrderStatus() ;
			
			Map<String , Object> metaData = service.metaData() ;
			
			mv.addObject("list" , list) ;
			mv.addObject("orderStatus" , orderStatus) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(newOrdersPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
	    return mv ;
	}

	
	
	@RequestMapping("aao/nextorders")
	public ModelAndView nextOrders(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute(DomainValues.SessionKeys.USER)  != null) {
			List<OrderDTO> list = service.findAllOrdersOrderedNext() ;
			fillOrderStatus() ;
			
			Map<String , Object> metaData = service.metaData() ;
			
			mv.addObject("list" , list) ;
			mv.addObject("orderStatus" , orderStatus) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(newOrdersPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/previousorders")
	public ModelAndView previousOrders(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute(DomainValues.SessionKeys.USER)  != null) {
			List<OrderDTO> list = service.findAllOrdersOrderedPrevious() ;
			fillOrderStatus() ;
			
			Map<String , Object> metaData = service.metaData() ;
			
			mv.addObject("list" , list) ;
			mv.addObject("orderStatus" , orderStatus) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(newOrdersPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/searchorders")
	public ModelAndView goSearchOrders(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute(DomainValues.SessionKeys.USER)  != null) {
			List<OrderRequestDTO> list = new ArrayList<OrderRequestDTO>() ;
			OrderRequestDTO dto = new OrderRequestDTO() ;
			dto.setOrderYear(new Date().getYear() + 1900) ;
			fillOrderStatus() ;
			
			mv.addObject("list" , list) ;
			mv.addObject("dto" , dto) ;
			mv.addObject("orderStatus" , orderStatus) ;
			mv.addObject("currentPage" , 0) ;
			mv.addObject("total" ,  0) ;
			mv.addObject("totalPages" , 0) ;
			mv.addObject("isFirst" , false) ;
			mv.addObject("isLast" , false) ;
			mv.setViewName(orderPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
	    return mv ;
	}
	
	
	
	@RequestMapping(value = "aao/searchOrders" , method = RequestMethod.POST , params = "search")
	public ModelAndView searchOrders(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			OrderRequestDTO order = new OrderRequestDTO() ;
			
			String customerFullName = null ;
			String categoryName = null ;
			String itemNameCode = null ;
			String orderDate = null ;
			String orderDateTo = null ;
			Integer orderYear = null ;
			Integer customerType = null ;		
			Integer orderStatusId = null ;			
			Long orderNumber = null ;
			
			if(request.getParameter("customerFullName") != null && !request.getParameter("customerFullName").equals("")) {
				customerFullName = request.getParameter("customerFullName") ;
				order.setCustomerFullName(customerFullName) ;
			}
			if(request.getParameter("categoryName") != null && !request.getParameter("categoryName").equals("")) {
				categoryName = request.getParameter("categoryName") ;
				order.setCategoryName(categoryName) ;				
			}
			if(request.getParameter("itemNameCode") != null && !request.getParameter("itemNameCode").equals("")) {
				itemNameCode = request.getParameter("itemNameCode") ;
				order.setItemNameCode(itemNameCode) ;
			}
			if(request.getParameter("customerType") != null && !request.getParameter("customerType").equals("") && !request.getParameter("customerType").equals("0")) {
				customerType = Integer.parseInt(request.getParameter("customerType")) ;
				order.setCustomerType(customerType) ;					
			}
			if(request.getParameter("orderNumber") != null && !request.getParameter("orderNumber").equals("")) {
				orderNumber = Long.parseLong(request.getParameter("orderNumber")) ;
				
				if(request.getParameter("orderYear") != null && !request.getParameter("orderYear").equals("")) {
					orderYear = Integer.parseInt(request.getParameter("orderYear")) ;
				}else {
					orderYear = new Date().getYear() + 1900 ;
				}
				order.setOrderNumber(orderNumber) ;
				order.setOrderYear(orderYear) ;
			}
			if(request.getParameter("orderStatusId") != null && !request.getParameter("orderStatusId").equals("") && !request.getParameter("orderStatusId").equals("0")) {
				orderStatusId = Integer.parseInt(request.getParameter("orderStatusId")) ;
				order.setOrderStatusId(orderStatusId) ;
			}
			if(request.getParameter("orderDate") != null && !request.getParameter("orderDate").equals("") 
					&& request.getParameter("orderDateTo") != null && !request.getParameter("orderDateTo").equals("")) {
				
				orderDate = request.getParameter("orderDate") ;
				orderDateTo = request.getParameter("orderDateTo") ;
				
				order.setOrderDate(orderDate) ;
				order.setOrderDateTo(orderDateTo) ;
			}
			
			List<OrderRequestDTO> list = service.search(order) ;
			int total = list != null ? list.size() : 0 ;
			fillOrderStatus() ;
			
			mv.addObject("dto" , order) ;
			mv.addObject("list" , list) ;
			mv.addObject("orderStatus" , orderStatus) ;
			mv.addObject("total" ,  total) ;
			mv.setViewName(orderPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/orderdetails")
	public ModelAndView goOrderDetails(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute(DomainValues.SessionKeys.USER)  != null) {
			Long id = Long.parseLong(request.getParameter("id")) ;
			session.setAttribute("orderIdForDetails" , id) ;
			fillOrderStatus() ;
			
			dto = service.findOrderById(id) ;
			List<OrderItemDTO> orderItemList = orderItemService.findByOrderIdForDetails(id) ;
			Map<String, Object> metaData = orderItemService.metaData() ;
			
			mv.addObject("dto" , dto) ;			
			mv.addObject("list" , orderItemList) ;
			mv.addObject("orderStatus" , orderStatus) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(orderDetailsPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	
	@RequestMapping("aao/orderItemsNext")
	public ModelAndView orderItemsNext(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute(DomainValues.SessionKeys.USER)  != null) {
			Long id = null ;
			if(session.getAttribute("orderIdForDetails") != null) {
				id = Long.parseLong(session.getAttribute("orderIdForDetails").toString()) ;
			}
			
			fillOrderStatus() ;
			
			List<OrderItemDTO> orderItemList = orderItemService.findByOrderIdForDetailsNext(id) ;
			Map<String, Object> metaData = orderItemService.metaData() ;
			
			mv.addObject("dto" , dto) ;			
			mv.addObject("list" , orderItemList) ;
			mv.addObject("orderStatus" , orderStatus) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(orderDetailsPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/orderItemsPrevious")
	public ModelAndView orderItemsPrevious(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute(DomainValues.SessionKeys.USER)  != null) {
			Long id = null ;
			if(session.getAttribute("orderIdForDetails") != null) {
				id = Long.parseLong( session.getAttribute("orderIdForDetails").toString()) ;
			}
			
			fillOrderStatus() ;

			List<OrderItemDTO> orderItemList = orderItemService.findByOrderIdForDetailsPrevious(id) ;
			Map<String, Object> metaData = orderItemService.metaData() ;
			
			mv.addObject("dto" , dto) ;			
			mv.addObject("list" , orderItemList) ;
			mv.addObject("orderStatus" , orderStatus) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(orderDetailsPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping(value = "aao/saveOrderStatus" , method = RequestMethod.POST , params = "save")
	public ModelAndView saveOrderStatus(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {			
			Long id = null ;
			if(session.getAttribute("orderIdForDetails") != null) {
				id = Long.parseLong( session.getAttribute("orderIdForDetails").toString()) ;
			}
			
			Integer orderStatusId = 0 ;
			if(request.getParameter("orderStatusId") != null && !request.getParameter("orderStatusId").equals("0")) {
				orderStatusId = Integer.parseInt(request.getParameter("orderStatusId")) ;
			}
			
			OrderDTO order = orderService.findById(id) ;
			order.setId(id) ;
			order.setOrderStatusId(orderStatusId) ;
			order.setUpdatedDate(new Date()) ;
			orderService.save(order) ;
			mv.addObject("saved" , 1) ;
			
			OrderStatusDTO status = new OrderStatusDTO() ;
			status.setOrderId(id) ;
			status.setStatusDate(new Date()) ;
			status.setStatusId(orderStatusId) ;
			service.saveOrderStatus(status) ;
			
			mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER)) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping(value = "aao/saveOrderStatus" , method = RequestMethod.POST , params = "cancel")
	public ModelAndView saveOrderStatusReset(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			int target = Integer.parseInt(request.getParameter("target")) ;
			if(target == 1) {
				List<OrderRequestDTO> list = new ArrayList<OrderRequestDTO>() ;
				OrderRequestDTO dto = new OrderRequestDTO() ;
				dto.setOrderYear(new Date().getYear() + 1900) ;
				fillOrderStatus() ;
				
				mv.addObject("list" , list) ;
				mv.addObject("dto" , dto) ;
				mv.addObject("orderStatus" , orderStatus) ;
				mv.addObject("currentPage" , 0) ;
				mv.addObject("total" ,  0) ;
				mv.addObject("totalPages" , 0) ;
				mv.addObject("isFirst" , false) ;
				mv.addObject("isLast" , false) ;
				mv.setViewName(orderPage) ;
			}else {
				List<OrderDTO> list = service.findAllOrdersOrdered() ;
				fillOrderStatus() ;
				
				Map<String , Object> metaData = service.metaData() ;
				
				mv.addObject("list" , list) ;
				mv.addObject("orderStatus" , orderStatus) ;
				mv.addObject("currentPage" , metaData.get("currentPage")) ;
				mv.addObject("total" ,  metaData.get("total")) ;
				mv.addObject("totalPages" , metaData.get("totalPages")) ;
				mv.addObject("isFirst" , metaData.get("isFirst")) ;
				mv.addObject("isLast" , metaData.get("isLast")) ;
				mv.setViewName(newOrdersPage) ;
			}
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping(value = "aao/searchOrders" , method = RequestMethod.POST , params = "reset")
	public ModelAndView searchOrdersReset(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			List<OrderRequestDTO> list = new ArrayList<OrderRequestDTO>() ;
			
			fillOrderStatus() ;
			
			mv.addObject("dto" , new OrderRequestDTO()) ;
			mv.addObject("list" , list) ;
			mv.addObject("orderStatus" , orderStatus) ;
			mv.addObject("total" ,  0) ;
			mv.setViewName(orderPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/tracking")
	public ModelAndView tracking(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			Long orderId = Long.parseLong(request.getParameter("order")) ;
			List<OrderStatusDTO> orderStatusList = orderService.orderStatusListHistory(orderId , 1) ;
			Map<String, Object> metaData = orderService.metaData() ;
			session.setAttribute("target" , request.getParameter("target")) ;
			
			fillOrderStatus() ;
			
			mv.addObject("orderStatus" , orderStatus) ;
			mv.addObject("orderStatusList" , orderStatusList) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(orderTrackingPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	@RequestMapping("aao/nextTracking")
	public ModelAndView nextTracking(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			Long orderId = Long.parseLong(request.getParameter("order")) ;
			List<OrderStatusDTO> orderStatusList = orderService.next(orderId , 1) ;
			Map<String, Object> metaData = orderService.metaData() ;
			int target = Integer.parseInt(session.getAttribute("target") == null ? "1" : session.getAttribute("target").toString()) ;
			fillOrderStatus() ;
			
			mv.addObject("orderStatus" , orderStatus) ;
			mv.addObject("orderStatusList" , orderStatusList) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(orderTrackingPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/previousTracking")
	public ModelAndView previousTracking(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			Long orderId = Long.parseLong(request.getParameter("order")) ;
			List<OrderStatusDTO> orderStatusList = orderService.previous(orderId , 1) ;
			Map<String, Object> metaData = orderService.metaData() ;
			int target = Integer.parseInt(session.getAttribute("target") == null ? "1" : session.getAttribute("target").toString()) ;
			
			fillOrderStatus() ;
			
			mv.addObject("orderStatus" , orderStatus) ;
			mv.addObject("orderStatusList" , orderStatusList) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(orderTrackingPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	@RequestMapping("aao/backOrders")
	public ModelAndView backOrders(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			List<OrderRequestDTO> list = new ArrayList<OrderRequestDTO>() ;
			int target = Integer.parseInt(request.getParameter("taget")) ;
			
			fillOrderStatus() ;
			
			mv.addObject("dto" , new OrderRequestDTO()) ;
			mv.addObject("list" , list) ;
			mv.addObject("orderStatus" , orderStatus) ;
			mv.addObject("total" ,  0) ;
			if(target == 1) {
				mv.setViewName(orderPage) ;
			}else {
				List<OrderDTO> ordersList = service.findAllOrdersOrdered() ;
				fillOrderStatus() ;				
				Map<String , Object> metaData = service.metaData() ;
				
				mv.addObject("list" , ordersList) ;
				mv.addObject("orderStatus" , orderStatus) ;
				mv.addObject("currentPage" , metaData.get("currentPage")) ;
				mv.addObject("total" ,  metaData.get("total")) ;
				mv.addObject("totalPages" , metaData.get("totalPages")) ;
				mv.addObject("isFirst" , metaData.get("isFirst")) ;
				mv.addObject("isLast" , metaData.get("isLast")) ;
				mv.setViewName(newOrdersPage) ;
			}
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	private void fillOrderStatus() {
		orderStatus = new ArrayList<GeneralDTO>() ;
		
		GeneralDTO ordered = new GeneralDTO() ;
		ordered.setKey(2L) ;
		ordered.setValueEn(HelperUtils.getValueFromBundle("ORDERED" , 2));
		orderStatus.add(ordered) ;
		
		GeneralDTO accepted = new GeneralDTO() ;
		accepted.setKey(3L) ;
		accepted.setValueEn(HelperUtils.getValueFromBundle("ACCEPTED" , 2));
		orderStatus.add(accepted) ;
		
		GeneralDTO inTheWay = new GeneralDTO() ;
		inTheWay.setKey(4L) ;
		inTheWay.setValueEn(HelperUtils.getValueFromBundle("IN_THE_WAY" , 2));
		orderStatus.add(inTheWay) ;
		
		GeneralDTO delivered = new GeneralDTO() ;
		delivered.setKey(5L) ;
		delivered.setValueEn(HelperUtils.getValueFromBundle("DELIVERED" , 2));
		orderStatus.add(delivered) ;
		
		GeneralDTO rejectedAdmin = new GeneralDTO() ;
		rejectedAdmin.setKey(6L) ;
		rejectedAdmin.setValueEn(HelperUtils.getValueFromBundle("REJECTED_ADMIN" , 2));
		orderStatus.add(rejectedAdmin) ;
		
		GeneralDTO cancelled = new GeneralDTO() ;
		cancelled.setKey(8L) ;
		cancelled.setValueEn(HelperUtils.getValueFromBundle("CANCELLED" , 2));
		orderStatus.add(cancelled) ;
	}
	
	
	
	
	
	public List<GeneralDTO> getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(List<GeneralDTO> orderStatus) {
		this.orderStatus = orderStatus;
	}
	public OrderRequestDTO getDto() {
		return dto;
	}
	public void setDto(OrderRequestDTO dto) {
		this.dto = dto;
	}
	
	
	
}
