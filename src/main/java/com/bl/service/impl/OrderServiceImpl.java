package com.bl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bl.DomainValues;
import com.bl.HelperUtils;
import com.bl.dto.GeneralDTO;
import com.bl.dto.OrderDTO;
import com.bl.dto.OrderStatusDTO;
import com.bl.entity.OrderEntity;
import com.bl.entity.OrderStatusEntity;
import com.bl.repository.*;
import com.bl.service.GeneralService;
import com.bl.service.OrderItemService;
import com.bl.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository repo ; 	
	@Autowired
	private OrderItemService orderItemService ;	
	@Autowired
	private OrderStatusRepository orderStatusRepository ;
	@Autowired
	private GeneralService generalService ;
	
	private Page<OrderStatusEntity> orderStatusPage ;
	private List<OrderStatusEntity> orderStatusEntityList ;
	
	
	@Override
	public OrderDTO findByCustomerIdAndOrderStatusId(Long customerId, Integer orderStatusId) {
		OrderDTO dto = null ;
		OrderEntity entity = repo.findByCustomerIdAndOrderStatusId(customerId , orderStatusId) ;
		
		if(entity != null) {
			dto = HelperUtils.convertEntityToDto(entity , OrderDTO.class) ;
			Long orderExpire = HelperUtils.dateDifferencesInDays(entity.getOrderDate()) ;
			Integer days = generalService.orderExpireDays() ;
			if(orderExpire >= days) {
				entity.setOrderStatusId(11) ;
				entity.setUpdatedDate(new Date()) ;
				repo.save(entity) ;
				dto = null ;
			}else {
				dto = HelperUtils.convertEntityToDto(entity , OrderDTO.class) ;
			}
		}
		return dto ;
	}


	@Override
	public OrderDTO findById(Long id) {
		OrderDTO dto = null ;
		OrderEntity entity = repo.findById(id).get() ;
		if(entity != null) {
			dto = HelperUtils.convertEntityToDto(entity , OrderDTO.class) ;
			if(dto != null) {
				Integer currencyId = dto.getCurrencyId() ;
				if(currencyId != null) {
					GeneralDTO currency = generalService.currencyById(currencyId) ;
					if(currency != null) {
						dto.setCurrencyName(currency.getValueEn()) ;
						dto.setCurrencyNameAr(currency.getValueAr()) ;
						dto.setInternationalCode(currency.getIsoName()) ;
					}
				}
			}
		}
		return dto ;
	}
	
	
	
	
	
	@Override
	public List<OrderDTO> myOrders(Long customerId) {
		List<OrderDTO> list = null ;		
		List<OrderEntity> entityList = repo.findByCustomerIdOrderByIdDesc(customerId) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<OrderDTO>() ;
			for(OrderEntity entity : entityList) {
				OrderDTO dto = HelperUtils.convertEntityToDto(entity , OrderDTO.class) ;
				dto.setOrderNumberLong(dto.getOrderNumber() + "/" + (dto.getOrderDate().getYear() + 1900));
				dto.setItems(orderItemService.findAllByOrderId(dto.getId())) ;
				list.add(dto) ;				
			}
		}
		return list ;
	}


	@Override
	public List<OrderStatusDTO> orderStatusList(Long orderId , int language) {
		List<OrderStatusDTO> list = null ;
		List<OrderStatusEntity> entityList = orderStatusRepository.findAllByOrderIdOrderByIdDesc(orderId) ;
		
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<OrderStatusDTO>() ;
			for(OrderStatusEntity entity : entityList) {
				OrderStatusDTO dto = HelperUtils.convertEntityToDto(entity , OrderStatusDTO.class) ;
				dto.setStatusName(statusNameLanguage(language, dto.getStatusId())) ;
				list.add(dto) ;
			}
		}
		return list ;
	}
	

	@Override
	public Long save(OrderDTO dto) {
		OrderEntity entity = HelperUtils.convertDtoToEntity(dto , OrderEntity.class) ;
		entity = repo.save(entity) ;
		return entity.getId() ;
	}
	
	
	@Override
	public List<OrderStatusDTO> orderStatusListHistory(Long orderId, int language) {
		List<OrderStatusDTO> list = null ;
		orderStatusPage = orderStatusRepository.findAllByOrderId(orderId , PageRequest.of(0 , 5 , Sort.by("id").descending())) ;
		if(orderStatusPage != null && !orderStatusPage.isEmpty()) {
			list = new ArrayList<OrderStatusDTO>() ;
			orderStatusEntityList = orderStatusPage.getContent() ;
			for(OrderStatusEntity entity : orderStatusEntityList) {
				OrderStatusDTO dto = HelperUtils.convertEntityToDto(entity , OrderStatusDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}


	@Override
	public List<OrderStatusDTO> next(Long orderId , int language) {
		List<OrderStatusDTO> list = null ;
		if(orderStatusPage.hasNext()) {
			orderStatusPage = orderStatusRepository.findAllByOrderId(orderId , orderStatusPage.nextPageable()) ;
			if(orderStatusPage != null && !orderStatusPage.isEmpty()) {
				list = new ArrayList<OrderStatusDTO>() ;
				orderStatusEntityList = orderStatusPage.getContent() ;
				for(OrderStatusEntity entity : orderStatusEntityList) {
					OrderStatusDTO dto = HelperUtils.convertEntityToDto(entity , OrderStatusDTO.class) ;
					list.add(dto) ;
				}
			}
		}
		return list ;
	}


	@Override
	public List<OrderStatusDTO> previous(Long orderId ,int language) {
		List<OrderStatusDTO> list = null ;
		if(orderStatusPage.hasPrevious()) {
			orderStatusPage = orderStatusRepository.findAllByOrderId(orderId , orderStatusPage.previousPageable()) ;
			if(orderStatusPage != null && !orderStatusPage.isEmpty()) {
				list = new ArrayList<OrderStatusDTO>() ;
				orderStatusEntityList = orderStatusPage.getContent() ;
				for(OrderStatusEntity entity : orderStatusEntityList) {
					OrderStatusDTO dto = HelperUtils.convertEntityToDto(entity , OrderStatusDTO.class) ;
					list.add(dto) ;
				}
			}
		}
		return list ;
	}


	@Override
	public Map<String, Object> metaData() {
		Map<String, Object> metaData = new HashMap<String, Object>() ;
		if(orderStatusPage != null) {
		    metaData.put("currentPage", orderStatusPage.getNumber() +  1);
		    metaData.put("total", orderStatusPage.getTotalElements());
		    metaData.put("totalPages", orderStatusPage.getTotalPages());
		    metaData.put("isFirst", orderStatusPage.isFirst());
		     metaData.put("isLast", orderStatusPage.isLast());
		}		
		return metaData;
	}


	private String statusNameLanguage(int language , int statusId) {
		String name = null ;
		if(statusId == DomainValues.OrderStatus.ACCEPTED) {
			name = HelperUtils.getValueFromBundle("ACCEPTED" , language) ;
		}else if(statusId == DomainValues.OrderStatus.ADD_TO_CART) {
			name = HelperUtils.getValueFromBundle("ADD_TO_CART" , language) ;
		}else if(statusId == DomainValues.OrderStatus.CANCELLED) {
			name = HelperUtils.getValueFromBundle("CANCELLED" , language) ;
		}else if(statusId == DomainValues.OrderStatus.DELIVERED) {
			name = HelperUtils.getValueFromBundle("DELIVERED" , language) ;
		}else if(statusId == DomainValues.OrderStatus.IN_THE_WAY) {
			name = HelperUtils.getValueFromBundle("IN_THE_WAY" , language) ;
		}else if(statusId == DomainValues.OrderStatus.ORDERED) {
			name = HelperUtils.getValueFromBundle("ORDERED" , language) ;
		}else if(statusId == DomainValues.OrderStatus.REFUESED_BY_CUSTOMER) {
			name = HelperUtils.getValueFromBundle("REFUESED_BY_CUSTOMER" , language) ;
		}else if(statusId == DomainValues.OrderStatus.REJECTED_ADMIN) {
			name = HelperUtils.getValueFromBundle("REJECTED_ADMIN" , language) ;
		}
		return name ;
	}


//	@Override
//	public OrderDTO maxOrderNumber() {
//		OrderDTO dto = null ;
//		OrderEntity entity = repo.maxOrderNumber() ;
//		if(entity != null) {
//			dto = HelperUtils.convertEntityToDto(entity , OrderDTO.class) ;
//		}
//		return dto;
//	}


	public Page<OrderStatusEntity> getOrderStatusPage() {
		return orderStatusPage;
	}


	public void setOrderStatusPage(Page<OrderStatusEntity> orderStatusPage) {
		this.orderStatusPage = orderStatusPage;
	}


	public List<OrderStatusEntity> getOrderStatusEntityList() {
		return orderStatusEntityList;
	}


	public void setOrderStatusEntityList(List<OrderStatusEntity> orderStatusEntityList) {
		this.orderStatusEntityList = orderStatusEntityList;
	}

}
