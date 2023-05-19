package com.bl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dto.CategoryDTO;
import com.bl.dto.ItemsDTO;
import com.bl.dto.OrderItemDTO;
import com.bl.entity.OrderItemEntity;
import com.bl.repository.OrderItemRepository;
import com.bl.service.ItemService;
import com.bl.service.OrderItemService;
import com.bl.service.cms.CategoryService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemRepository repo ;
	
	@Autowired
	private ItemService itemService ;
	
	@Autowired
	private CategoryService categoryService ;
		
	private Page<OrderItemEntity> page ;
	private List<OrderItemEntity> entityList ;
	
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


	@Override
	public List<OrderItemDTO> findByOrderIdForDetails(Long orderId) {
		List<OrderItemDTO> list = null ;
		page = repo.findByOrderId(orderId , PageRequest.of(0 , 5)) ;
		
		if(page != null && !page.isEmpty()) {
			entityList = page.getContent() ;
			list = new ArrayList<OrderItemDTO>() ;
			for(OrderItemEntity entity : entityList) {
				OrderItemDTO dto = HelperUtils.convertEntityToDto(entity , OrderItemDTO.class) ;
				ItemsDTO item = itemService.findById(dto.getItemId()) ;
				if(item != null) {
					dto.setItemName(item.getItemName()) ;
					dto.setItemCode(item.getItemCode()) ;
					
					CategoryDTO category = categoryService.findById(item.getCategoryId()) ;
					if(category != null) {
						dto.setCategoryName(category.getCategoryName()) ;
					}
				}
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	
	
	@Override
	public List<OrderItemDTO> findByOrderIdForDetailsNext(Long orderId) {
		List<OrderItemDTO> list = null ;
		if(page.hasNext()) {
			page = repo.findByOrderId(orderId , page.nextPageable()) ;
			entityList = page.getContent() ;
			list = new ArrayList<OrderItemDTO>() ;
			for(OrderItemEntity entity : entityList) {
				OrderItemDTO dto = HelperUtils.convertEntityToDto(entity , OrderItemDTO.class) ;
				ItemsDTO item = itemService.findById(dto.getItemId()) ;
				if(item != null) {
					dto.setItemName(item.getItemName()) ;
					dto.setItemCode(item.getItemCode()) ;
					
					CategoryDTO category = categoryService.findById(item.getCategoryId()) ;
					if(category != null) {
						dto.setCategoryName(category.getCategoryName()) ;
					}
				}
				list.add(dto) ;
			}
		}
		return list ;
	}


	@Override
	public List<OrderItemDTO> findByOrderIdForDetailsPrevious(Long orderId) {
		List<OrderItemDTO> list = null ;
		if(page.hasPrevious()) {
			page = repo.findByOrderId(orderId , page.previousPageable()) ;
			entityList = page.getContent() ;
			list = new ArrayList<OrderItemDTO>() ;
			for(OrderItemEntity entity : entityList) {
				OrderItemDTO dto = HelperUtils.convertEntityToDto(entity , OrderItemDTO.class) ;
				ItemsDTO item = itemService.findById(dto.getItemId()) ;
				if(item != null) {
					dto.setItemName(item.getItemName()) ;
					dto.setItemCode(item.getItemCode()) ;
					
					CategoryDTO category = categoryService.findById(item.getCategoryId()) ;
					if(category != null) {
						dto.setCategoryName(category.getCategoryName()) ;
					}
				}
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

	
	
	public Page<OrderItemEntity> getPage() {
		return page;
	}


	public void setPage(Page<OrderItemEntity> page) {
		this.page = page;
	}


	public List<OrderItemEntity> getEntityList() {
		return entityList;
	}


	public void setEntityList(List<OrderItemEntity> entityList) {
		this.entityList = entityList;
	}


	

}
