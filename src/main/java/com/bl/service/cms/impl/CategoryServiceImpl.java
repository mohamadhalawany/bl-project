package com.bl.service.cms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bl.HelperUtils;
import com.bl.dto.CategoryDTO;
import com.bl.entity.CategoryEntity;
import com.bl.repository.ItemRepository;
import com.bl.repository.cms.CMSCategoryRepository;
import com.bl.service.cms.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CMSCategoryRepository repo ;
	
	@Autowired
	private ItemRepository itemRepository ;
	
	private Page<CategoryEntity> page ;
	private List<CategoryEntity> entityList ;
	
	@Override
	public List<CategoryDTO> findAll() {
		List<CategoryDTO> list = null ;
		page = repo.findAll(PageRequest.of(0 , 5 , Sort.by("parentCategoryId").and(Sort.by("id")))) ;
		if(page != null) {
			list = new ArrayList<CategoryDTO>() ;
			entityList = page.getContent() ;
			for(CategoryEntity entity : entityList) {
				CategoryDTO dto = HelperUtils.convertEntityToDto(entity , CategoryDTO.class) ;
				
				if(dto.getParentCategoryId() != null) {
					CategoryDTO main = findById(dto.getParentCategoryId()) ;
					dto.setParentCategoryName(main.getCategoryName());
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

	@Override
	public List<CategoryDTO> next() {
		List<CategoryDTO> list = null ;
		if(page.hasNext()) {
			list = new ArrayList<CategoryDTO>() ;
			page = repo.findAll(page.nextPageable()) ;
			entityList = page.getContent() ;
			
			for(CategoryEntity entity : entityList) {
				CategoryDTO dto = HelperUtils.convertEntityToDto(entity , CategoryDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public List<CategoryDTO> previous() {
		List<CategoryDTO> list = null ;
		if(page.hasPrevious()) {
			list = new ArrayList<CategoryDTO>() ;
			page = repo.findAll(page.previousPageable()) ;
			entityList = page.getContent() ;
			
			for(CategoryEntity entity : entityList) {
				CategoryDTO dto = HelperUtils.convertEntityToDto(entity , CategoryDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public Long save(CategoryDTO dto) {
		CategoryEntity entity = HelperUtils.convertDtoToEntity(dto , CategoryEntity.class) ;
		entity = repo.save(entity) ;
		return entity.getId() ;
	}

	
	
	
	@Override
	public CategoryDTO findById(Long id) {
		CategoryDTO dto = null ;
		Optional<CategoryEntity> opt = repo.findById(id) ;
		if(!opt.isEmpty()) {
			CategoryEntity entity = opt.get() ;
			dto = HelperUtils.convertEntityToDto(entity , CategoryDTO.class) ;
//			if(dto.getParentCategoryId() != null) {
//				CategoryDTO main = findById(dto.getParentCategoryId()) ;
//				dto.setParentCategoryName(main.getCategoryName());
//			}
		}
		return dto ;
	}
	
	
	
	
	@Override
	public int checkCategoryInItems(Long id) {
		Long count = itemRepository.countByCategoryId(id) ;
		if(count == 0L) {
			return 0;
		}else {
			return 1 ;
		}
	}

	@Override
	public void delete(Long id) {		
		Optional<CategoryEntity> opt = repo.findById(id) ;
		if(!opt.isEmpty()) {
			CategoryEntity entity = opt.get() ;
			repo.delete(entity) ;
		}
	}
	
	
	
	@Override
	public List<CategoryDTO> findAllNot(Long id) {
		List<CategoryDTO> list = null ;
		entityList = repo.findByIdNot(id) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<CategoryDTO>() ;
			for(CategoryEntity entity : entityList) {
				CategoryDTO dto = HelperUtils.convertEntityToDto(entity , CategoryDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	
	
	@Override
	public List<CategoryDTO> findAllCategories() {
		List<CategoryDTO> list = null ;
		List<CategoryEntity> entityList = repo.findAll() ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<CategoryDTO>() ;
			for(CategoryEntity entity : entityList) {
				CategoryDTO dto = HelperUtils.convertEntityToDto(entity , CategoryDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	
	@Override
	public List<CategoryDTO> findByParentCategoryId(Long parentCategoryId) {
		List<CategoryDTO> list = null ;
		List<CategoryEntity> entityList = repo.findByParentCategoryId(parentCategoryId) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<CategoryDTO>() ;
			for(CategoryEntity entity : entityList) {
				CategoryDTO dto = HelperUtils.convertEntityToDto(entity, CategoryDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	@Override
	public List<CategoryDTO> findByMenuIdNot(Integer menuId) {
		List<CategoryDTO> list = null ;
		List<CategoryEntity> entityList = repo.findByMenuIdNot(menuId) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<CategoryDTO>() ;
			for(CategoryEntity entity : entityList) {
				CategoryDTO dto = HelperUtils.convertEntityToDto(entity, CategoryDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	@Override
	public List<CategoryDTO> findByParentCategoryIdIsNull(Integer menuId) {
		List<CategoryDTO> list = null ;
		List<CategoryEntity> entityList = repo.findByParentCategoryIdIsNull(menuId) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<CategoryDTO>() ;
			for(CategoryEntity entity : entityList) {
				CategoryDTO dto = HelperUtils.convertEntityToDto(entity, CategoryDTO.class) ;				
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	

	@Override
	public List<CategoryDTO> findByParentCategoryIdIsNotNull() {
		List<CategoryDTO> list = null ;
		List<CategoryEntity> entityList = repo.findByParentCategoryIdIsNotNull() ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<CategoryDTO>() ;
			for(CategoryEntity entity : entityList) {
				CategoryDTO dto = HelperUtils.convertEntityToDto(entity, CategoryDTO.class) ;
				if(dto != null && dto.getParentCategoryId() != null) {
					CategoryDTO main = findById(dto.getParentCategoryId()) ;
					if(main != null) {
						dto.setParentCategoryName(main.getCategoryName()) ;
					}
				}
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	
	@Override
	public List<CategoryDTO> findByParentCategoryIdAndMenuId(Long parentCategoryId, Integer menuId) {
		List<CategoryDTO> list = null ;
		List<CategoryEntity> entityList = repo.findByParentCategoryIdAndMenuIdNotEqual(parentCategoryId , menuId) ;
		if(entityList != null && !entityList.isEmpty()) {
			list = new ArrayList<CategoryDTO>() ;
			for(CategoryEntity entity : entityList) {
				CategoryDTO dto = HelperUtils.convertEntityToDto(entity, CategoryDTO.class) ;
				list.add(dto) ;
			}
		}
		return list ;
	}
	
	

	public Page<CategoryEntity> getPage() {
		return page;
	}

	public void setPage(Page<CategoryEntity> page) {
		this.page = page;
	}

	public List<CategoryEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<CategoryEntity> entityList) {
		this.entityList = entityList;
	}

}
