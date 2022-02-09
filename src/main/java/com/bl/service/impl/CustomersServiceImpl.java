package com.bl.service.impl; 

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bl.DomainValues;
import com.bl.HelperUtils;
import com.bl.dto.CountryGovernorateCityDistrictDTO;
import com.bl.dto.CustomersDTO;
import com.bl.dto.OrderDTO;
import com.bl.dto.OrderItemDTO;
import com.bl.dto.OrderStatusDTO;
import com.bl.entity.CustomersEntity;
import com.bl.repository.CustomersRepository;
import com.bl.service.CustomersService;
import com.bl.service.GeneralService;
import com.bl.service.OrderRequestService;
import com.bl.service.OrderService;

@Service
public class CustomersServiceImpl implements CustomersService {

	@Autowired
	private CustomersRepository repo ;
	
	@Autowired
	private GeneralService generalService ;
	
	@Autowired
	private OrderRequestService orderRequestService ;
	
	@Autowired
	private OrderService orderService ;
	
	@Override
	public CustomersDTO findByEmailAndPassword(String email, String password) {
		password = HelperUtils.encrypt(password) ;
		
		CustomersEntity entity = repo.findByEmailAndPassword(email , password) ;
		CustomersDTO dto = null ;
		
		if(entity != null) { 
			dto = HelperUtils.convertEntityToDto(entity , CustomersDTO.class) ;
			List<CountryGovernorateCityDistrictDTO> governorateIdAndCountryIdList = generalService.findGovernorateIdAndCountryIdByCityDistrictId(dto.getCityDistrictId()) ;
			for(CountryGovernorateCityDistrictDTO cgcd : governorateIdAndCountryIdList) {
				dto.setCountryId(cgcd.getCountryId()) ;
				dto.setGovernorateId(cgcd.getGovernorateId()) ;
				dto.setCityDistrictId(cgcd.getCityDistrictId()) ;
				dto.setCityDistrictNameEn(cgcd.getCityDistrictNameEn()) ;
				dto.setCityDistrictNameAr(cgcd.getCityDistrictNameAr()) ;
				dto.setGovernorateNameEn(cgcd.getGovernorateNameEn()) ;
				dto.setGovernorateNameAr(cgcd.getGovernorateNameAr()) ;
				dto.setCountryNameEn(cgcd.getCountryNameEn()) ;
				dto.setCountryNameAr(cgcd.getCountryNameAr()) ;
			}
		}
		return dto ;
	}

	@Override
	public CustomersDTO findById(Long id) {
		CustomersDTO dto = null ;
		CustomersEntity entity = repo.findById(id).get() ;
		if(entity != null) {
			dto = HelperUtils.convertEntityToDto(entity , CustomersDTO.class) ;			
		}
		return dto ;
	}

	@Override
	public Long save(CustomersDTO dto) {		
		CustomersEntity entity = HelperUtils.convertDtoToEntity(dto, CustomersEntity.class) ;
		repo.save(entity) ;
		Long id = entity.getId() ;
		return id ;
	}

	@Override
	public Long countByEmail(String email) {
		return repo.countByEmail(email) ;
	}

	@Override
	public Long saveOrder(OrderDTO order, OrderItemDTO orderItem, OrderStatusDTO orderStatus) {
		OrderDTO orderDto = orderService.findByCustomerIdAndOrderStatusId(order.getCustomerId() , DomainValues.OrderStatus.ADD_TO_CART) ;	//validate order cart
		Long orderId = 0L ;
		if(orderDto != null) {									//save order
			orderId = orderDto.getId() ;
			order.setId(orderId) ;
			order.setTotalPrice(orderDto.getTotalPrice() + order.getTotalPrice()) ;
			order.setUpdatedDate(new Date()) ;
			orderRequestService.saveOrder(order) ;
		}else {
			orderId = orderRequestService.saveOrder(order) ;
		}
 
		orderItem.setOrderId(orderId) ;
		orderRequestService.saveOrderItem(orderItem) ; // save order item		
		orderStatus.setOrderId(orderId) ;
		orderRequestService.saveOrderStatus(orderStatus) ;// save order status 
		
		OrderDTO newOrder = orderService.findById(orderId) ;
		System.err.println(orderId + " nnnnnnnnnneeeeeeeeeeeeeewwwwwwwwwwwwww");
		Long orderNumber = 0L ;
		if(newOrder != null) {
			orderNumber = newOrder.getOrderNumber() ;
		}
		return orderNumber ;
	}

}
