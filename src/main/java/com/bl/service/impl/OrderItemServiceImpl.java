package com.bl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dto.ItemsDTO;
import com.bl.dto.OrderItemDTO;
import com.bl.entity.OrderItemEntity;
import com.bl.repository.OrderItemRepository;
import com.bl.service.ItemService;
import com.bl.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemRepository repo ;
	
	@Autowired
	private ItemService itemService ;
	
	
	@Override
	public List<OrderItemDTO> findAllByOrderId(Long orderId) {
		List<OrderItemDTO> list = null ;
		List<OrderItemEntity> entityList = repo.findAllByOrderId(orderId) ;
		
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<OrderItemDTO>() ;
			for(OrderItemEntity entity : entityList) {
				OrderItemDTO dto = HelperUtils.convertEntityToDto(entity , OrderItemDTO.class) ;
				ItemsDTO itemDto = itemService.findById(dto.getItemId()) ;
				dto.setItemName(itemDto.getItemName()) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

}
