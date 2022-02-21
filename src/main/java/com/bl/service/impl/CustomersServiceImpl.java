package com.bl.service.impl; 

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bl.DomainValues;
import com.bl.HelperUtils;
import com.bl.dto.AddressDTO;
import com.bl.dto.CountryGovernorateCityDistrictDTO;
import com.bl.dto.CustomersDTO;
import com.bl.dto.OrderDTO;
import com.bl.dto.OrderItemDTO;
import com.bl.dto.OrderStatusDTO;
import com.bl.entity.CustomersEntity;
import com.bl.repository.CustomersRepository;
import com.bl.service.AddressService;
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
	
	@Autowired
	private AddressService addressService ;
	
	private Page<CustomersEntity> page ;
	private List<CustomersEntity> entityList ;
	
	
	
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

		Long orderNumber = 0L ;
		if(newOrder != null) {
			orderNumber = newOrder.getOrderNumber() ;
		}
		return orderNumber ;
	}

	@Override
	public List<CustomersDTO> findAll(int language) {
		List<CustomersDTO> list = null ;
		page = repo.findAll(PageRequest.of(0 , 5)) ;
		entityList = page.getContent() ;
		
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<CustomersDTO>() ;
			for(CustomersEntity entity : entityList) {
				CustomersDTO dto = HelperUtils.convertEntityToDto(entity , CustomersDTO.class) ;
				
				if(dto.getCustomerType() == 1) {
					dto.setCustomerTypeValue(HelperUtils.getValueFromBundle("PERSON" , language));
				}else {
					dto.setCustomerTypeValue(HelperUtils.getValueFromBundle("COMPANY" , language));
				}
				AddressDTO cityDistrict = addressService.findCitiesDistrictById(dto.getCityDistrictId()) ;
				dto.setCityDistrictNameAr(cityDistrict.getCitiesDistrictNameAr()) ;
				dto.setCityDistrictNameEn(cityDistrict.getCitiesDistrictName()) ;
				
				AddressDTO governorate = addressService.findGovernorateById(cityDistrict.getGovernorateId()) ;
				dto.setGovernorateNameAr(governorate.getGovernorateNameAr()) ;
				dto.setGovernorateNameEn(governorate.getGovernorateNameEn()) ;
				
				AddressDTO country = addressService.findCountryById(governorate.getCountryId()) ;
				dto.setCountryNameAr(country.getCountryNameAr()) ;
				dto.setCountryNameEn(country.getCountryName()) ;
				
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	

	@Override
	public List<CustomersDTO> next() {
		List<CustomersDTO> list = null ;
		
		if(page.hasNext()) {
			list = new ArrayList<CustomersDTO>() ;
			page = repo.findAll(page.nextPageable()) ;
			entityList = page.getContent() ;
			
			for(CustomersEntity entity : entityList) {
				CustomersDTO dto = HelperUtils.convertEntityToDto(entity , CustomersDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public List<CustomersDTO> previous() {
		List<CustomersDTO> list = null ;
		
		if(page.hasNext()) {
			list = new ArrayList<CustomersDTO>() ;
			page = repo.findAll(page.previousPageable()) ;
			entityList = page.getContent() ;
			
			for(CustomersEntity entity : entityList) {
				CustomersDTO dto = HelperUtils.convertEntityToDto(entity , CustomersDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public Map<String, Object> metaData() {
		Map<String, Object> metaData = new HashMap<String, Object>() ;
		if(page != null) {
		    metaData.put("currentPage", page.getNumber() + 1);
		    metaData.put("total", page.getTotalElements());
		    metaData.put("totalPages", page.getTotalPages());
		    metaData.put("isFirst", page.isFirst());
		     metaData.put("isLast", page.isLast());
		}		
		return metaData;
	}



	@Override
	public List<CustomersDTO> search(CustomersDTO dto , int language) {
		List<CustomersDTO> list = null ;
		page = repo.search(dto.getEmail() , dto.getFullName() , dto.getCustomerType() , dto.getCityDistrictId() , dto.getGovernorateId() , dto.getCountryId() , 
				PageRequest.of(0 , 5)) ;
		
		List<CustomersEntity> entityList = page.getContent() ;
		
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<CustomersDTO>() ;
			
			for(CustomersEntity entity : entityList) {				
				CustomersDTO customer = HelperUtils.convertEntityToDto(entity , CustomersDTO.class) ;
				if(customer.getCustomerType() == 1) {
					customer.setCustomerTypeValue(HelperUtils.getValueFromBundle("PERSON" , language));
				}else {
					customer.setCustomerTypeValue(HelperUtils.getValueFromBundle("COMPANY" , language));
				}
				AddressDTO cityDistrict = addressService.findCitiesDistrictById(customer.getCityDistrictId()) ;
				customer.setCityDistrictNameAr(cityDistrict.getCitiesDistrictNameAr()) ;
				customer.setCityDistrictNameEn(cityDistrict.getCitiesDistrictName()) ;
				
				AddressDTO governorate = addressService.findGovernorateById(cityDistrict.getGovernorateId()) ;
				customer.setGovernorateNameAr(governorate.getGovernorateNameAr()) ;
				customer.setGovernorateNameEn(governorate.getGovernorateNameEn()) ;
				
				AddressDTO country = addressService.findCountryById(governorate.getCountryId()) ;
				customer.setCountryNameAr(country.getCountryNameAr()) ;
				customer.setCountryNameEn(country.getCountryName()) ;
				
				list.add(customer) ;
			}
		}			
		return list ;
	}
	
	
	

	public Page<CustomersEntity> getPage() {
		return page;
	}

	public void setPage(Page<CustomersEntity> page) {
		this.page = page;
	}

	public List<CustomersEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<CustomersEntity> entityList) {
		this.entityList = entityList;
	}
	
	
	
	

}
