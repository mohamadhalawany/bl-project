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

import com.bl.HelperUtils;
import com.bl.dao.OrderRequestDAO;
import com.bl.dto.CustomersDTO;
import com.bl.dto.GeneralDTO;
import com.bl.dto.OrderDTO;
import com.bl.dto.OrderItemDTO;
import com.bl.dto.OrderRequestDTO;
import com.bl.dto.OrderStatusDTO;
import com.bl.entity.OrderEntity;
import com.bl.entity.OrderItemEntity;
import com.bl.entity.OrderStatusEntity;
import com.bl.repository.OrderItemRepository;
import com.bl.repository.OrderRepository;
import com.bl.repository.OrderStatusRepository;
import com.bl.service.CustomersService;
import com.bl.service.GeneralService;
import com.bl.service.OrderRequestService;

@Service
public class OrderRequestServiceImpl implements OrderRequestService {

	@Autowired
	private OrderRequestDAO dao ;
	
	@Autowired
	private OrderRepository orderRepository ;
	
	@Autowired
	private OrderItemRepository orderItemRepository ;
	
	@Autowired
	private OrderStatusRepository orderStatusRepository ;
	
	@Autowired
	private CustomersService customersService ;
	
	@Autowired
	private GeneralService generalService ;
	
	private Page< OrderEntity> orderPage ;
	private List<OrderEntity> orderEntityList ;
	
	@Override
	public List<OrderRequestDTO> customerOrdersCart(Long customerId , Integer orderStatus) {
		Long maxOrderId = dao.maxOrderIdByCustomerIdAndOrderStatus(customerId , orderStatus) ;		
		List<OrderRequestDTO> list = dao.customerOrdersList(customerId , orderStatus , maxOrderId) ;		
		return list ;
	}


	@Override
	public Long saveOrder(OrderDTO dto) {
		OrderEntity entity = HelperUtils.convertDtoToEntity(dto , OrderEntity.class) ;

		if(entity.getId() == null) {
			entity.setOrderNumber(generateOrderNumber()) ;
		}		
		entity.setPaymentMethod(1) ;
		entity = orderRepository.save(entity) ;
		Long id = entity.getId() ;
		return id ;
	}


	@Override
	public Long saveOrderItem(OrderItemDTO dto) {
		OrderItemEntity entity = HelperUtils.convertDtoToEntity(dto , OrderItemEntity.class) ;
		Long id = 0L ;
		OrderItemEntity oldEntity = orderItemRepository.findByOrderIdAndItemId(entity.getOrderId() , entity.getItemId()) ;
		
		if(oldEntity != null) {
			Long itemId = entity.getItemId() ;
			Long oldItemId = oldEntity.getItemId() ;
			Long orderId = entity.getOrderId() ;
			Long oldOrderId = oldEntity.getOrderId() ;
			
			if(itemId == oldItemId && orderId == oldOrderId) {
				entity.setId(oldEntity.getId()) ;
				entity.setQuantity(oldEntity.getQuantity() + entity.getQuantity()) ;
			}
			id = orderItemRepository.save(entity).getId() ;
		}else {
			id = orderItemRepository.save(entity).getId() ;
		}
		return id ;
	}


	@Override
	public Long saveOrderStatus(OrderStatusDTO dto) {
		OrderStatusEntity entity = HelperUtils.convertDtoToEntity(dto, OrderStatusEntity.class) ;
		entity = orderStatusRepository.save(entity) ;
		Long id = entity.getId() ;
		return id ;
	}


	@Override
	public void delete(Long id) {
		OrderItemEntity entity = orderItemRepository.findById(id).get() ;
		if(entity != null) {
			OrderEntity orderEntity = orderRepository.findById(entity.getOrderId()).get() ;
			Double itemPrice = entity.getItemPrice() * entity.getQuantity() ;			
			Double totalPrice = orderEntity.getTotalPrice() ;
			totalPrice = totalPrice - itemPrice ;
			orderEntity.setTotalPrice(totalPrice) ;
			
			Long count = orderItemRepository.countByOrderId(orderEntity.getId()) ;
			if(count > 1) {
				orderRepository.save(orderEntity) ;
			}else {
				orderRepository.delete(orderEntity) ;
			}
			orderItemRepository.delete(entity) ;
		}
		
	}


	@Override
	public void increaseQuantity(Integer quantity, Long orderItemId , Long orderId , int type) {
		OrderItemEntity entity = orderItemRepository.findById(orderItemId).get() ;
		if(entity != null) {
			Integer oldQuantity = entity.getQuantity() ;
			entity.setQuantity(quantity + oldQuantity) ;
			orderItemRepository.save(entity) ;
				
			Double price = entity.getItemPrice() ;
				
			OrderEntity orderEntity = orderRepository.findById(orderId).get() ;
			if(orderEntity != null) {
				Double totalPrice = orderEntity.getTotalPrice() ;
				Double total = totalPrice + price ;
				orderEntity.setUpdatedDate(new Date()) ;
				orderEntity.setTotalPrice(total) ;
				orderRepository.save(orderEntity) ;
			}			
		}
	}
	
	
	
	
	
	@Override
	public void decreaseQuantity(Integer quantity, Long orderItemId, Long orderId, int type) {
		OrderItemEntity entity = orderItemRepository.findById(orderItemId).get() ;
		if(entity != null) {
			Integer oldQuantity = entity.getQuantity() ;
			if(oldQuantity == 1) {
				orderItemRepository.delete(entity) ;
			}else {
				entity.setQuantity(oldQuantity - quantity) ;
				orderItemRepository.save(entity) ;
			}
			Double price = entity.getItemPrice() ;
			
			OrderEntity orderEntity = orderRepository.findById(orderId).get() ;
			if(orderEntity != null) {
				Double totalPrice = orderEntity.getTotalPrice() ;
				Double total = totalPrice - price ;
				orderEntity.setUpdatedDate(new Date()) ;
				orderEntity.setTotalPrice(total) ;
				orderRepository.save(orderEntity) ;
			}
		}
	}


	private Long generateOrderNumber() {
		Long orderNumber = orderRepository.maxOrderNumber() + 1 ;
		
		return orderNumber ;
	}


	@Override
	public OrderDTO findById(Long id) {
		OrderDTO dto = null ;
		OrderEntity entity = orderRepository.findById(id).get() ; 
		if(entity != null) {
			dto = HelperUtils.convertEntityToDto(entity , OrderDTO.class) ;
		}
		return dto ;
	}


	@Override
	public List<OrderDTO> findAllOrdersOrdered() {
		List<OrderDTO> list = null ;
		orderPage = orderRepository.newOrders(PageRequest.of(0 , 5)) ;
		if(!orderPage.isEmpty()) {
			orderEntityList = orderPage.getContent() ;
			list = new ArrayList<OrderDTO>() ;
			for(OrderEntity entity : orderEntityList) {
				OrderDTO dto = HelperUtils.convertEntityToDto(entity , OrderDTO.class) ;
				dto.setOrderNumberLong(dto.getOrderNumberLong() + "/" + (dto.getOrderDate().getYear() + 1900)) ;
				CustomersDTO customer = customersService.findById(dto.getCustomerId()) ;
				if(customer != null) {
					dto.setCustomerFullName(customer.getFullName()) ;
					dto.setCustomerType(customer.getCustomerType()) ;
				}
				
				GeneralDTO currency = generalService.currencyById(dto.getCurrencyId()) ;
				if(currency != null) {
					dto.setInternationalCode(currency.getIsoName()) ;
				}
				
				list.add(dto) ;
			}
		}
		return list ;
	}
	

	@Override
	public List<OrderDTO> findAllOrdersOrderedNext() {
		List<OrderDTO> list = null ;
		if(orderPage.hasNext()) {
			orderPage = orderRepository.newOrders(orderPage.nextPageable()) ;
			if(!orderPage.isEmpty()) {
				orderEntityList = orderPage.getContent() ;
				list = new ArrayList<OrderDTO>() ;
				for(OrderEntity entity : orderEntityList) {
					OrderDTO dto = HelperUtils.convertEntityToDto(entity , OrderDTO.class) ;
					dto.setOrderNumberLong(dto.getOrderNumberLong() + "/" + (dto.getOrderDate().getYear() + 1900)) ;
					
					CustomersDTO customer = customersService.findById(dto.getCustomerId()) ;
					if(customer != null) {
						dto.setCustomerFullName(customer.getFullName()) ;
						dto.setCustomerType(customer.getCustomerType()) ;
					}
					
					GeneralDTO currency = generalService.currencyById(dto.getCurrencyId()) ;
					if(currency != null) {
						dto.setInternationalCode(currency.getIsoName()) ;
					}
					list.add(dto) ;
				}
			}
		}
		return list ;
	}
	
	
	


	@Override
	public List<OrderDTO> findAllOrdersOrderedPrevious() {
		List<OrderDTO> list = null ;
		if(orderPage.hasPrevious()) {
			orderPage = orderRepository.newOrders(orderPage.previousPageable()) ;
			if(!orderPage.isEmpty()) {
				orderEntityList = orderPage.getContent() ;
				list = new ArrayList<OrderDTO>() ;
				for(OrderEntity entity : orderEntityList) {
					OrderDTO dto = HelperUtils.convertEntityToDto(entity , OrderDTO.class) ;
					dto.setOrderNumberLong(dto.getOrderNumberLong() + "/" + (dto.getOrderDate().getYear() + 1900)) ;
					
					CustomersDTO customer = customersService.findById(dto.getCustomerId()) ;
					if(customer != null) {
						dto.setCustomerFullName(customer.getFullName()) ;
						dto.setCustomerType(customer.getCustomerType()) ;
					}
					
					GeneralDTO currency = generalService.currencyById(dto.getCurrencyId()) ;
					if(currency != null) {
						dto.setInternationalCode(currency.getIsoName()) ;
					}
					list.add(dto) ;
				}
			}
		}
		return list ;
	}


	@Override
	public Map<String, Object> metaData() {
		Map<String, Object> metaData = new HashMap<String, Object>() ;
		if(orderPage != null) {
		    metaData.put("currentPage", orderPage.getNumber() + 1);
		    metaData.put("total", orderPage.getTotalElements());
		    metaData.put("totalPages", orderPage.getTotalPages());
		    metaData.put("isFirst", orderPage.isFirst());
		     metaData.put("isLast", orderPage.isLast());
		}		
		return metaData;
	}

	

	@Override
	public OrderRequestDTO findOrderById(Long id) {
		OrderRequestDTO dto = dao.findOrderRequestById(id) ;
		
		return dto ;
	}


	@Override
	public List<OrderRequestDTO> search(OrderRequestDTO dto) {
		List<OrderRequestDTO> list = dao.search(dto) ;
		return list ;
	}


	public Page<OrderEntity> getOrderPage() {
		return orderPage;
	}


	public void setOrderPage(Page<OrderEntity> orderPage) {
		this.orderPage = orderPage;
	}


	public List<OrderEntity> getOrderEntityList() {
		return orderEntityList;
	}


	public void setOrderEntityList(List<OrderEntity> orderEntityList) {
		this.orderEntityList = orderEntityList;
	}


	
	
}
