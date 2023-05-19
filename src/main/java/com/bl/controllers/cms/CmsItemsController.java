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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bl.DomainValues;
import com.bl.HelperUtils;
import com.bl.dto.CategoryDTO;
import com.bl.dto.GeneralDTO;
import com.bl.dto.ItemSpecificationsDTO;
import com.bl.dto.ItemsDTO;
import com.bl.dto.cms.BrandDTO;
import com.bl.dto.cms.ItemBrandDTO;
import com.bl.dto.cms.ItemOffersDTO;
import com.bl.dto.cms.ItemProductTypeDTO;
import com.bl.dto.cms.LoginUserDTO;
import com.bl.dto.cms.OffersDTO;
import com.bl.dto.cms.ProductTypeDTO;
import com.bl.service.GeneralService;
import com.bl.service.ItemService;
import com.bl.service.ItemSpecificationService;
import com.bl.service.cms.BrandService;
import com.bl.service.cms.CategoryService;
import com.bl.service.cms.ItemBrandService;
import com.bl.service.cms.ItemOffersService;
import com.bl.service.cms.ItemProductTypeService;
import com.bl.service.cms.ProductTypeService;

@Controller
public class CmsItemsController {
	
	private String categoryPage = "items/category" ;
	private String categoryEditPage = "items/category_edit" ;
	private String mainCategoryPage = "items/main_category" ;
	private String itemPage = "items/item" ;
	private String itemEditPage = "items/edit" ;
	private String itemOfferPage = "offers/item" ;
	private String loginPage = "login" ;
	
	private String sessionUserKey = DomainValues.SessionKeys.USER ;
	
	@Autowired
	private CategoryService categoryService ;	
	@Autowired
	private ItemService itemService ;	
	@Autowired
	private GeneralService generalService ;	
	@Autowired
	private ItemSpecificationService itemSpecificationService ;	
	@Autowired
	private ItemProductTypeService itemProductTypeService ;
	@Autowired
	private ProductTypeService productTypeService ;
	@Autowired
	private ItemBrandService itemBrandService ;
	@Autowired
	private BrandService brandService ;
	@Autowired
	private ItemOffersService itemOffersService ;
	
	
	
	private ItemsDTO dto ;
	
	private List<CategoryDTO> categories ;
	private List<ItemsDTO> items ;
	private List<ItemsDTO> itemsSearch ;
	private List<GeneralDTO> currencyList ;
	private List<GeneralDTO> colorList ;
	
	private Map<String, Object> metaData ;
	
	@RequestMapping("aao/category")
	public ModelAndView goCategory(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("user") != null) {			
			categories = categoryService.findByParentCategoryIdIsNotNull() ;
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;
			Map<String , Object> metaData = categoryService.metaData() ;
			
			mv.addObject("mainCategoryList" , mainCategoryList) ;
			mv.addObject("list" , categories) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			
			mv.setViewName(categoryPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		
		return mv ;
	}
	
	
	@RequestMapping("aao/category_edit")
	public ModelAndView editCategory(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("user") != null) {		
			if(request.getParameter("id") != null && !request.getParameter("id").equals("")) {
				Long categoryId = Long.parseLong(request.getParameter("id")) ;
				CategoryDTO dto = categoryService.findById(categoryId) ;
				List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;
				
				mv.addObject("dto" , dto) ;
				mv.addObject("mainCategoryList" , mainCategoryList) ;
				mv.setViewName(categoryEditPage) ;
			}else {
				mv.setViewName(loginPage) ;
			}			
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/goMainCategory")
	public ModelAndView goMainCategory(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("user") != null) {		
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;
			int total = 0 ;
			if(mainCategoryList != null) {
				total = mainCategoryList.size() ;
			}
			mv.addObject("list" , mainCategoryList) ;
			mv.addObject("total" , total) ;
			mv.setViewName(mainCategoryPage) ;				
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/nextCategory")
	public ModelAndView nextCategory(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("user") != null) {
			Map<String , Object> metaData = categoryService.metaData() ;
			categories = categoryService.next() ;
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;

			mv.addObject("mainCategoryList" , mainCategoryList) ;
			mv.addObject("list" , categories) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(categoryPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/previousCategory")
	public ModelAndView previousCategory(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("user") != null) {
			Map<String , Object> metaData = categoryService.metaData() ;
			categories = categoryService.previous() ;
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;

			mv.addObject("mainCategoryList" , mainCategoryList) ;
			mv.addObject("list" , categories) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(categoryPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/createCategory")
	public ModelAndView createCategory(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			CategoryDTO dto = new CategoryDTO() ;
			
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;

			mv.addObject("mainCategoryList" , mainCategoryList) ;
			mv.addObject("dto" , dto) ;
			mv.setViewName(categoryEditPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	@RequestMapping("aao/cancelCategory")
	public ModelAndView cancel(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {			
			categories = categoryService.findAll() ;
			Map<String , Object> metaData = categoryService.metaData() ;
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;

			mv.addObject("mainCategoryList" , mainCategoryList) ;
			mv.addObject("list" , categories) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;

			mv.setViewName(categoryPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}

	
	@RequestMapping("aao/saveCategory")
	public ModelAndView saveCategory(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
				
		if(session.getAttribute("user") != null) {		
			LoginUserDTO user = (LoginUserDTO) session.getAttribute("user") ;
			String categoryName = request.getParameter("categoryName") ;
			Long id = null ;
			Long parentCategoryId = Long.parseLong(request.getParameter("parentCategoryId")) ;
		
			CategoryDTO dto = null ;
			
			if(request.getParameter("id") == null || request.getParameter("id").equals("")) {
				dto = new CategoryDTO() ;
				dto.setCreatedBy(user.getId()) ;
				dto.setCreatedDate(new Date()) ;
				if(request.getParameter("categoryName") != null) {
					dto.setCategoryName(categoryName) ;
				}								
				if(request.getParameter("parentCategoryId") != null) {
					dto.setParentCategoryId(parentCategoryId) ;
				}
			}else if(request.getParameter("id") != null && request.getParameter("id") != "") {				
				id = Long.parseLong(request.getParameter("id")) ;
				dto = categoryService.findById(id) ;
				dto.setParentCategoryId(parentCategoryId) ;
				dto.setCategoryName(categoryName) ;
				dto.setUpdatedBy(user.getId()) ;
				dto.setUpdatedDate(new Date()) ;
			}
			id = categoryService.save(dto) ;
			dto = categoryService.findById(id) ;
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;

			mv.addObject("saved" , 1) ;
			mv.addObject("dto" , dto) ;
			mv.addObject("mainCategoryList" , mainCategoryList) ;
			mv.setViewName(categoryEditPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/activeCategory")
	public ModelAndView activeInactiveCategory(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
				
		if(session.getAttribute("user") != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute("user") ;
			int flag = 1 ;
			Long id = null ;
			
			if(request.getParameter("flag") == null) {
				flag = 1 ;
			}else {
				flag = Integer.parseInt(request.getParameter("flag")) ;
			}
			
			if(request.getParameter("id") != null) {
				id = Long.parseLong(request.getParameter("id")) ;
			}
			
			CategoryDTO dto = categoryService.findById(id) ;
			if(dto != null) {
				dto.setIsActive(flag) ;
				dto.setId(id) ;
				dto.setUpdatedBy(user.getId()) ;
				dto.setUpdatedDate(new Date()) ;
				categoryService.save(dto) ;
			}
			
			categories = categoryService.findAll() ;
			Map<String , Object> metaData = categoryService.metaData() ;
			
			mv.addObject("list" , categories) ;
			mv.addObject("listNot" , new ArrayList<CategoryDTO>()) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName("redirect: category") ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	@RequestMapping("aao/deleteCategory")
	public ModelAndView delete(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("user") != null) {
			Long id = null ;
			if(request.getParameter("id") != null) {
				id = Long.parseLong(request.getParameter("id")) ;
				int delete = categoryService.checkCategoryInItems(id) ;
				
				if(delete == 0) {
					categoryService.delete(id) ;
					mv.addObject("delete" , null) ;
				}else {
					mv.addObject("delete" , 1) ;
				}
			}
			
//			categories = categoryService.findAll() ;
//			Map<String , Object> metaData = categoryService.metaData() ;
//			
//			mv.addObject("list" , categories) ;
//			mv.addObject("currentPage" , metaData.get("currentPage")) ;
//			mv.addObject("total" ,  metaData.get("total")) ;
//			mv.addObject("totalPages" , metaData.get("totalPages")) ;
//			mv.addObject("isFirst" , metaData.get("isFirst")) ;
//			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER)) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}

	
//////////////// ITEMS START /////////////////////////////////////////////////////////////////////////////////////////////////////////	

	
	@RequestMapping("aao/item")
	public ModelAndView item(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {			
			session.setAttribute("categoryName" , null) ;
			session.setAttribute("searchNextPrevious" , null) ;
			
			items = itemService.findAll() ;			
			metaData = itemService.metaData() ;
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;

			mv.addObject("mainCategoryList" , mainCategoryList) ;
			mv.addObject("list" , items) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
 			mv.setViewName(itemPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/mainCategoryList")
	public ModelAndView mainCategoryList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {			
			session.setAttribute("categoryName" , null) ;
			session.setAttribute("searchNextPrevious" , null) ;
			
			Long id = null ;
			ItemsDTO dto = new ItemsDTO() ;
			
			items = itemService.findAll() ;			
			metaData = itemService.metaData() ;
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;
			
			if(request.getParameter("main") != null && !request.getParameter("main").equals("")) {
				id = Long.parseLong(request.getParameter("main")) ;
				dto.setParentCategoryId(id) ;
				mv.addObject("categoryList" , categoryService.findByParentCategoryId(id)) ;
			}
			
			mv.addObject("dto" , dto) ;
			mv.addObject("mainCategoryList" , mainCategoryList) ;
			mv.addObject("list" , items) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
 			mv.setViewName(itemPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/categoryByParent")
	public ModelAndView categoryByParent(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {			
			Long itemId = Long.parseLong(session.getAttribute("item") == null ? "0" : session.getAttribute("item").toString()) ;
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;
			Long parentCategoryId = null ;
			
			if(request.getParameter("main") != null && !request.getParameter("main").equals("")) {
				parentCategoryId = Long.parseLong(request.getParameter("main")) ;								
				mv.addObject("categories" , categoryService.findByParentCategoryId(parentCategoryId)) ;
			}
			
			ItemsDTO dto = itemService.findById(itemId) ;
			if(dto == null) {
				dto = new ItemsDTO() ;
			}
			dto.setParentCategoryId(parentCategoryId) ;
			
			currencyList = generalService.currencyList() ;
			colorList = generalService.colorsList() ;
			
			mv.addObject("currencyList" , currencyList) ;
			mv.addObject("colorList" , colorList) ;
			mv.addObject("dto" , dto) ;
			mv.addObject("mainCategoryList" , mainCategoryList) ;
			mv.addObject("tab" , 1) ;
			mv.setViewName(itemEditPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	@RequestMapping("aao/itemsNext")
	public ModelAndView next(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {			
			items = itemService.findAllItemsNextPage() ;
			
			metaData = itemService.metaData() ;
			
			mv.addObject("list" , items) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
 			mv.setViewName(itemPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/itemsPrevious")
	public ModelAndView previous(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {			
			items = itemService.findAllItemsPreviousPage() ;
			
			metaData = itemService.metaData() ;
			
			mv.addObject("list" , items) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
 			mv.setViewName(itemPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping(value =  "aao/searchItems" , method = RequestMethod.POST , params = "search")
	public ModelAndView search(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			session.setAttribute("searchNextPrevious" , 1) ;		
			
			ItemsDTO dto = new ItemsDTO() ;
			dto.setCategoryId(Long.parseLong(request.getParameter("categoryId"))) ;
			dto.setItemName(request.getParameter("itemName")) ;
			dto.setDescription(request.getParameter("description")) ;
			dto.setItemCode(request.getParameter("itemCode")) ;
			dto.setParentCategoryId(Long.parseLong(request.getParameter("mainCategoryId"))) ;
			
			if(request.getParameter("categoryName") != "") {
				session.setAttribute("categoryName" , request.getParameter("categoryName")) ;
			}else {
				session.setAttribute("categoryName" , null) ;
			}
			
			itemsSearch= itemService.findAll(dto) ;
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;
			
			mv.addObject("categoryList" , categoryService.findByParentCategoryId(Long.parseLong(request.getParameter("mainCategoryId")))) ;
			mv.addObject("mainCategoryList" , mainCategoryList) ;
			mv.addObject("list" , itemsSearch) ;
			mv.addObject("dto" , dto) ;
			mv.addObject("total" ,  itemsSearch.size()) ;
			mv.setViewName(itemPage) ;
		}else {
			session.setAttribute("categoryName" , null) ;
			mv.setViewName(loginPage) ;	
		}
		return mv ;
	}
	
	
	
	@RequestMapping(value =  "aao/searchItems" , method = RequestMethod.POST , params = "reset")
	public ModelAndView reset(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			session.setAttribute("categoryName" , null) ;
			session.setAttribute("searchNextPrevious" , null) ;	
			
			items = itemService.findAll() ;
			Map<String , Object> metaData = itemService.metaData() ;
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;

			mv.addObject("mainCategoryList" , mainCategoryList) ;
			mv.addObject("list" , items) ;
			mv.addObject("dto" , new ItemsDTO()) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(itemPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping(value =  "aao/searchItems" , method = RequestMethod.POST , params = "add")
	public ModelAndView addNewItem(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;						
			currencyList = generalService.currencyList() ;
			colorList = generalService.colorsList() ;
			
			ItemsDTO dto = new ItemsDTO() ;
			ItemSpecificationsDTO itemSpecificationsDTO = new ItemSpecificationsDTO() ;
						
			mv.addObject("mainCategoryList" , mainCategoryList) ;
			mv.addObject("dto" , dto) ;
			mv.addObject("itemSpec" , itemSpecificationsDTO) ;
			mv.addObject("categories" , categories) ;
			mv.addObject("currencyList" , currencyList) ;
			mv.addObject("colorList" , colorList) ;
			mv.setViewName(itemEditPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/edititem")
	public ModelAndView editItem(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		session.setAttribute("saved" , null) ;
		session.setAttribute("item" , null) ;
		
		if(session.getAttribute(sessionUserKey) != null) {
			Long id = Long.parseLong(request.getParameter("id")) ;
			session.setAttribute("item" , id) ;
					
			currencyList = generalService.currencyList() ;
			colorList = generalService.colorsList() ;
			
			dto = itemService.findById(id) ;					
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;
			categories = categoryService.findByParentCategoryId(dto.getParentCategoryId()) ;
			mv.addObject("mainCategoryList" , mainCategoryList) ;
			mv.addObject("dto" , dto) ;			
			mv.addObject("categories" , categories) ;
			mv.addObject("currencyList" , currencyList) ;
			mv.addObject("colorList" , colorList) ;
			mv.setViewName(itemEditPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping(value = "aao/saveItems" , method = RequestMethod.POST , params = "save")
	public ModelAndView doSaveItems(HttpServletRequest request , @RequestParam("fileItemLogo") MultipartFile fileItemLogo) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			ItemsDTO dto = null ;
			
			Long id = Long.parseLong(request.getParameter("id").equals("") ? "0" : request.getParameter("id")) ;
			Long categoryId = Long.parseLong(request.getParameter("categoryId")) ;
			Long parentCategoryId = 0L ;
			String itemName = request.getParameter("itemName") ;
			String itemCode = request.getParameter("itemCode") ;
			String description = null ;			
			String itemLogo = null ;
			String extension = null ;
			Double itemPrice = 1.0 ;
			Integer currencyId = 48 ;
			Integer isHidden = 0 ;
			
			if(fileItemLogo != null && !fileItemLogo.isEmpty()) {
				String fileName = fileItemLogo.getOriginalFilename() ;
				itemLogo = fileName.substring(0 , fileName.lastIndexOf(".")) ;
				extension = fileName.substring(fileName.lastIndexOf(".") + 1 , fileName.length()) ;
				itemLogo = request.getParameter("itemLogo").equals("") ? fileName : request.getParameter("itemLogo") ;
				
				if(!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("jpeg") && !extension.equalsIgnoreCase("png") && !extension.equals("webp")) {
					mv.addObject("extensionError" , 1) ;
					mv.addObject("saved" , null) ;
					dto = itemService.findById(id) ;
					dto.setCategoryId(categoryId) ;
					mv.addObject("dto" , dto) ;
					
					ItemSpecificationsDTO itemSpecificationsDTO = itemSpecificationService.findAllByItemId(id) ;					
					mv.addObject("itemSpec" , itemSpecificationsDTO) ;
				}else if(fileItemLogo.getSize() > (10 * 1024 * 1024)) {
					mv.addObject("sizeError" , 1) ;
					mv.addObject("saved" , null) ;
					mv.addObject("dto" , itemService.findById(id)) ;
					
					ItemSpecificationsDTO itemSpecificationsDTO = itemSpecificationService.findAllByItemId(id) ;					
					mv.addObject("itemSpec" , itemSpecificationsDTO) ;
				}else {
					if(request.getParameter("description") != null) {
						description = request.getParameter("description") ;
					}
					if(request.getParameter("itemPrice") != null) {
						itemPrice = Double.parseDouble(request.getParameter("itemPrice")) ;
					}
					if(request.getParameter("itemPrice") != null) {
						itemPrice = Double.parseDouble(request.getParameter("itemPrice")) ;				
					}
					if(request.getParameter("currencyId") != null) {
						currencyId = Integer.parseInt(request.getParameter("currencyId")) ;
					}
					if(request.getParameter("isHidden") != null) {
						isHidden = Integer.parseInt(request.getParameter("isHidden")) ;
					}
										
					if(id != 0L) {
						dto = itemService.findById(id) ;
						dto.setUpdatedBy(user.getId()) ;
						dto.setUpdatedDate(new Date()) ;
					}else {
						dto = new ItemsDTO() ;
						dto.setCreatedBy(user.getId()) ;
						dto.setCreatedDate(new Date()) ;
					}
					dto.setCategoryId(categoryId) ;
					dto.setCurrencyId(currencyId) ;
					dto.setDescription(description) ;
					dto.setIsHidden(isHidden) ;
					dto.setItemCode(itemCode) ;
					itemLogo = new Date().getTime() + "_" + user.getId() ;
					dto.setItemLogo(itemLogo + "." + extension) ;
					dto.setExtension(extension) ;
					dto.setItemName(itemName) ;
					dto.setItemPrice(itemPrice) ;
					
					if(itemPrice <= 0) {
						mv.addObject("itemPriceZeroError" , 1) ;				
					}else {
						HelperUtils.upload(itemLogo + "." + extension , fileItemLogo) ;
						id = itemService.save(dto) ;
						mv.addObject("saved" , 1) ;
					}
					dto = itemService.findById(id) ;
					dto.setParentCategoryId(parentCategoryId) ;
					mv.addObject("dto" , dto) ;
					
					ItemSpecificationsDTO itemSpecificationsDTO = itemSpecificationService.findAllByItemId(id) ;					
					mv.addObject("itemSpec" , itemSpecificationsDTO) ;
				}
			}else {
				if(request.getParameter("description") != null) {
					description = request.getParameter("description") ;
				}
				if(request.getParameter("itemPrice") != null) {
					itemPrice = Double.parseDouble(request.getParameter("itemPrice")) ;
				}
				if(request.getParameter("itemPrice") != null) {
					itemPrice = Double.parseDouble(request.getParameter("itemPrice")) ;				
				}
				if(request.getParameter("currencyId") != null) {
					currencyId = Integer.parseInt(request.getParameter("currencyId")) ;
				}
				if(request.getParameter("isHidden") != null) {
					isHidden = Integer.parseInt(request.getParameter("isHidden")) ;
				}
				if(request.getParameter("itemLogo") != null) {
					itemLogo = request.getParameter("itemLogo") ;
				}
				
				if(id != 0L) {
					dto = itemService.findById(id) ;
					dto.setUpdatedBy(user.getId()) ;
					dto.setUpdatedDate(new Date()) ;
				}else {
					dto = new ItemsDTO() ;
					dto.setCreatedBy(user.getId()) ;
					dto.setCreatedDate(new Date()) ;
				}
				dto.setCategoryId(categoryId) ;
				dto.setCurrencyId(currencyId) ;
				dto.setDescription(description) ;
				dto.setIsHidden(isHidden) ;
				dto.setItemCode(itemCode) ;
				dto.setItemName(itemName) ;
				dto.setItemPrice(itemPrice) ;
				
				if(itemPrice <= 0) {
					mv.addObject("itemPriceZeroError" , 1) ;				
				}else {
					id = itemService.save(dto) ;
					mv.addObject("saved" , 1) ;
				}
												
				ItemSpecificationsDTO itemSpecificationsDTO = itemSpecificationService.findAllByItemId(id) ;					
				mv.addObject("itemSpec" , itemSpecificationsDTO) ;
			}
			
			if(request.getParameter("parentCategoryId") != null && !request.getParameter("parentCategoryId").equals("")) {
				parentCategoryId = Long.parseLong(request.getParameter("parentCategoryId")) ;
			}
			dto = itemService.findById(id) ;
//			categoryId = Long.parseLong(request.getParameter("categoryId")) ;
			dto.setCategoryId(categoryId) ;
			dto.setParentCategoryId(parentCategoryId) ;			
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;
			
			mv.addObject("dto" , dto) ;
			mv.addObject("categories" , categoryService.findByParentCategoryId(parentCategoryId)) ;
			mv.addObject("mainCategoryList" , mainCategoryList) ;
			mv.addObject("categories" , categories) ;
			mv.addObject("currencyList" , currencyList) ;
			mv.addObject("colorList" , colorList) ;
			mv.setViewName(itemEditPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/resetSaveItems")
	public ModelAndView resetSaveItems(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		
		mv.setViewName("redirect: item") ;
		return mv ;
	}
	
	
	
	@RequestMapping("aao/activeItem")
	public ModelAndView activeInactiveItem(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
				
		if(session.getAttribute("user") != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute("user") ;
			int flag = 1 ;
			Long id = null ;
			
			if(request.getParameter("flag") == null) {
				flag = 1 ;
			}else {
				flag = Integer.parseInt(request.getParameter("flag")) ;
			}
			
			if(request.getParameter("id") != null) {
				id = Long.parseLong(request.getParameter("id")) ;
			}
			
			ItemsDTO dto = itemService.findById(id) ;
			if(dto != null) {
				dto.setIsHidden(flag) ;
				dto.setId(id) ;
				dto.setUpdatedBy(user.getId()) ;
				dto.setUpdatedDate(new Date()) ;
				itemService.save(dto) ;
			}
			
			items = itemService.findAll() ;
			Map<String , Object> metaData = itemService.metaData() ;
			
			mv.addObject("list" , items) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
 			mv.setViewName(itemPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/deleteItem")
	public ModelAndView deleteItem(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("user") != null) {
			Long id = null ;
			if(request.getParameter("id") != null) {
				id = Long.parseLong(request.getParameter("id")) ;
				itemService.delete(id) ;
			}
			mv.setViewName("redirect: item") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping(value = "aao/saveItemSpec" , method = RequestMethod.POST)
	public ModelAndView saveItemSpec(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			
			Long id = Long.parseLong(request.getParameter("itemSpecId").equals("") ? "0" : request.getParameter("itemSpecId")) ;
			Long itemId = Long.parseLong(request.getParameter("itemId")) ;
			Integer colorId = Integer.parseInt(request.getParameter("colorId")) ;
			Double depth = null ;
			Double height = null ;
			Double width = null ;
			String included = null ;
			String manufacturingTime = null ;
			String material = null ;
			String warranty = null ;
			
			if(request.getParameter("depth") != null && !request.getParameter("depth").equals("")) {
				depth = Double.parseDouble(request.getParameter("depth")) ;
			}
			
			if(request.getParameter("height") != null && !request.getParameter("height").equals("")) {
				height = Double.parseDouble(request.getParameter("height")) ;
			}
			
			if(request.getParameter("width") != null && !request.getParameter("width").equals("")) {
				width = Double.parseDouble(request.getParameter("width")) ;
			}
			
			if(request.getParameter("included") != null && !request.getParameter("included").equals("")) {
				included = request.getParameter("included") ;
			}
			
			if(request.getParameter("manufacturingTime") != null && !request.getParameter("manufacturingTime").equals("")) {
				manufacturingTime = request.getParameter("manufacturingTime") ;
			}

			if(request.getParameter("material") != null && !request.getParameter("material").equals("")) {
				material = request.getParameter("material") ;
			}
			
			if(request.getParameter("warranty") != null && !request.getParameter("warranty").equals("")) {
				warranty = request.getParameter("warranty") ;
			}
			
			ItemSpecificationsDTO dto = null ;
			
			if(id == 0L) {
				dto = new ItemSpecificationsDTO() ;
				dto.setCreatedBy(user.getId()) ;
				dto.setCreatedDate(new Date()) ;				
				dto.setColorId(colorId) ;
				dto.setCreatedBy(user.getId()) ;
				dto.setCreatedDate(new Date()) ;
				dto.setDepth(depth) ;
				dto.setHeight(height) ;
				dto.setId(id) ;
				dto.setIncluded(included) ;
				dto.setItemId(itemId) ;
				dto.setManufacturingTime(manufacturingTime) ;
				dto.setMaterial(material) ;
				dto.setWarranty(warranty) ;
				dto.setWidth(width) ;
			}else {
				dto = itemSpecificationService.findById(id) ;
				dto.setId(id) ;
				dto.setUpdatedBy(user.getId()) ;
				dto.setUpdatedDate(new Date()) ;				
				dto.setColorId(colorId == 0 ? dto.getColorId() : colorId) ;
				dto.setCreatedBy(user.getId()) ;
				dto.setCreatedDate(new Date()) ;
				dto.setDepth(depth == null ? dto.getDepth() : depth) ;
				dto.setHeight(height == null ? dto.getHeight() : height) ;
				dto.setId(id) ;
				dto.setIncluded(included == null ? dto.getIncluded() : included) ;
				dto.setItemId(itemId) ;
				dto.setManufacturingTime(manufacturingTime == null ? dto.getManufacturingTime() : manufacturingTime) ;
				dto.setMaterial(material == null ? dto.getMaterial() : material) ;
				dto.setWarranty(warranty == null ? dto.getWarranty() : warranty) ;
				dto.setWidth(width == null ? dto.getWidth() : width) ;
			}
			
			itemSpecificationService.save(dto) ;
			mv.addObject("savedSpec" , 1) ;
			mv.setViewName("redirect: edititem?id=" + itemId) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/resetSaveItemSpec")
	public ModelAndView resetSaveItemSpec(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			mv.setViewName("redirect: item") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/showtabs")
	public ModelAndView showTabs(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			int tab = Integer.parseInt(request.getParameter("tab")) ;
			Long itemId = Long.parseLong(session.getAttribute("item") == null ? "0" :  session.getAttribute("item").toString()) ;
			
			if(itemId != 0) {
				if(tab == 1) {
					ItemSpecificationsDTO itemSpecificationsDTO = itemSpecificationService.findAllByItemId(itemId) ;	
					mv.addObject("itemSpec" , itemSpecificationsDTO) ;		
				}
				
				if(tab == 2) {
					List<ItemProductTypeDTO> itemProductList = itemProductTypeService.findAllByItemId(itemId) ;
					if(itemProductList != null) {						
						mv.addObject("itemProductList" , itemProductList) ;
						List<ProductTypeDTO> productTypeList = productTypeService.findAllIsActive() ;
						mv.addObject("productTypeList" , productTypeList) ;
					}
				}
				
				if(tab == 3) {
					List<ItemBrandDTO> itemBrandList = itemBrandService.findAllByItemId(itemId) ;
					List<BrandDTO> brandList = brandService.findAllIsActive() ;
					mv.addObject("itemBrandList" , itemBrandList) ;					
					mv.addObject("brandList" , brandList) ;
				}
				
				if(tab == 4) {
					List<ItemOffersDTO> itemOffersList = itemOffersService.findAllByItemId(itemId) ;
					dto = itemService.findById(itemId) ;
					mv.addObject("itemOffersList" , itemOffersList) ;					
				}
			}
			
			mv.addObject("dto" , dto) ;					
			mv.addObject("categories" , categories) ;
			mv.addObject("currencyList" , currencyList) ;
			mv.addObject("colorList" , colorList) ;
			mv.addObject("item" , itemId) ;
			mv.setViewName(itemEditPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		
		return mv ;
	}
	
	
	
	@RequestMapping("aao/saveItemProductType")
	public ModelAndView saveItemProductType(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			Integer productTypeId = Integer.parseInt(request.getParameter("productTypeId")) ;
			Long itemId = Long.parseLong(session.getAttribute("item") != null ? session.getAttribute("item").toString() : "0") ;
			Integer saved = 0 ; 
			
			if(!request.getParameter("id").equals("")) {
				Integer id = Integer.parseInt(request.getParameter("id")) ;				
				ItemProductTypeDTO dto = itemProductTypeService.findById(id) ;
				dto.setProductTypeId(productTypeId);
				dto.setUpdatedBy(user.getId()) ;
				dto.setUpdatedDate(new Date()) ;
				
				itemId = dto.getItemId() ;
				
				saved = itemProductTypeService.save(dto) ;
			}else {				
				ItemProductTypeDTO dto = new ItemProductTypeDTO() ;
				dto.setCreatedBy(user.getId()) ;
				dto.setCreatedDate(new Date()) ;
				dto.setItemId(itemId);
				dto.setProductTypeId(productTypeId) ;	
				saved = itemProductTypeService.save(dto) ;
			}
			List<ItemProductTypeDTO> itemProductList = itemProductTypeService.findAllByItemId(itemId) ;
			List<ProductTypeDTO> productTypeList = productTypeService.findAllIsActive() ;
			
			mv.addObject("productTypeList" , productTypeList) ;
			mv.addObject("itemProductList" , itemProductList) ;
			mv.addObject("dto" , dto) ;					
			mv.addObject("categories" , categories) ;
			mv.addObject("currencyList" , currencyList) ;
			mv.addObject("colorList" , colorList) ;
			mv.addObject("saved" , saved) ;
			request.setAttribute("tab" , 2) ;
			mv.setViewName(itemEditPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/deleteItemType")
	public ModelAndView deleteItemType(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			Integer id = Integer.parseInt(request.getParameter("id")) ;
			int delete = itemProductTypeService.delete(id) ;
			Long itemId = Long.parseLong(session.getAttribute("item") != null ? session.getAttribute("item").toString() : "0") ;
			
			List<ItemProductTypeDTO> itemProductList = itemProductTypeService.findAllByItemId(itemId) ;
			List<ProductTypeDTO> productTypeList = productTypeService.findAllIsActive() ;
			
			mv.addObject("delete" , delete) ;
			mv.addObject("productTypeList" , productTypeList) ;
			mv.addObject("itemProductList" , itemProductList) ;
			mv.addObject("dto" , dto) ;					
			mv.addObject("categories" , categories) ;
			mv.addObject("currencyList" , currencyList) ;
			mv.addObject("colorList" , colorList) ;
			mv.addObject("tab" , 2) ;
			request.setAttribute("tab" , 2) ;
			mv.setViewName(itemEditPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/createItemType")
	public ModelAndView createItemType(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			Long itemId = Long.parseLong(session.getAttribute("item") != null ? session.getAttribute("item").toString() : "0") ;

			if(itemId != 0) {
				List<ItemProductTypeDTO> itemProductList = itemProductTypeService.findAllByItemId(itemId) ;
				if(itemProductList == null) {
					itemProductList = new ArrayList<ItemProductTypeDTO>() ;
				}
				ItemProductTypeDTO itemProductTypeDTO = new ItemProductTypeDTO() ;
				itemProductList.add(0 , itemProductTypeDTO) ;
				
				List<ProductTypeDTO> productTypeList = productTypeService.findAllIsActive() ;
				mv.addObject("productTypeList" , productTypeList) ;
				mv.addObject("itemProductList" , itemProductList) ;
				mv.addObject("dto" , dto) ;					
				mv.addObject("categories" , categories) ;
				mv.addObject("currencyList" , currencyList) ;
				mv.addObject("colorList" , colorList) ;
				mv.addObject("tab" , 2) ;
				mv.setViewName(itemEditPage) ;
			}
			
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/createItemBrand")
	public ModelAndView createItemBrand(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			Long itemId = Long.parseLong(session.getAttribute("item") != null ? session.getAttribute("item").toString() : "0") ;
			if(itemId != 0) {
				ItemBrandDTO brand = new ItemBrandDTO() ;
				List<ItemBrandDTO> itemBrandList = itemBrandService.findAllByItemId(itemId) ;
				if(itemBrandList == null) {
					itemBrandList = new ArrayList<ItemBrandDTO>() ;
				}
				itemBrandList.add(0 , brand) ;
				List<BrandDTO> brandList = brandService.findAllIsActive() ;

				mv.addObject("itemBrandList" , itemBrandList) ;					
				mv.addObject("brandList" , brandList) ;
				mv.addObject("dto" , dto) ;					
				mv.addObject("categories" , categories) ;
				mv.addObject("currencyList" , currencyList) ;
				mv.addObject("colorList" , colorList) ;
				mv.setViewName(itemEditPage) ;
			}
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/saveItemBrand")
	public ModelAndView saveItemBrand(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		int saved = 0 ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			Long itemId = Long.parseLong(session.getAttribute("item") != null ? session.getAttribute("item").toString() : "0") ;
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			Integer brandId = Integer.parseInt(request.getParameter("brandId")) ;
			if(!request.getParameter("id").equals("")) {
				Integer id = Integer.parseInt(request.getParameter("id")) ;			
				ItemBrandDTO brand = itemBrandService.findById(id) ;
				brand.setBrandId(brandId) ;
				brand.setUpdatedBy(user.getId()) ;
				brand.setUpdatedDate(new Date()) ;
				saved = itemBrandService.save(brand) ;
			}else {
				ItemBrandDTO brand = new ItemBrandDTO() ;
				brand.setBrandId(brandId) ;
				brand.setCreatedBy(user.getId()) ;
				brand.setCreatedDate(new Date()) ;
				brand.setItemId(itemId) ;
				saved = itemBrandService.save(brand) ;
			}
			List<BrandDTO> brandList = brandService.findAllIsActive() ;
			List<ItemBrandDTO> itemBrandList = itemBrandService.findAllByItemId(itemId) ;
			
			mv.addObject("saved" , saved) ;
			mv.addObject("itemBrandList" , itemBrandList) ;					
			mv.addObject("brandList" , brandList) ;
			mv.addObject("dto" , dto) ;					
			mv.addObject("categories" , categories) ;
			mv.addObject("currencyList" , currencyList) ;
			mv.addObject("colorList" , colorList) ;
			mv.setViewName(itemEditPage) ;
		}else {
			mv.setViewName(loginPage);
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/deleteItemBrand")
	public ModelAndView deleteItemBrand(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			Integer id = Integer.parseInt(request.getParameter("id")) ;
			int delete = itemBrandService.delete(id) ;
			Long itemId = Long.parseLong(session.getAttribute("item") != null ? session.getAttribute("item").toString() : "0") ;
			
			List<BrandDTO> brandList = brandService.findAllIsActive() ;
			List<ItemBrandDTO> itemBrandList = itemBrandService.findAllByItemId(itemId) ;
			
			mv.addObject("delete" , delete) ;
			mv.addObject("itemBrandList" , itemBrandList) ;					
			mv.addObject("brandList" , brandList) ;
			mv.addObject("dto" , dto) ;					
			mv.addObject("categories" , categories) ;
			mv.addObject("currencyList" , currencyList) ;
			mv.addObject("colorList" , colorList) ;
			mv.setViewName(itemEditPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/createItemOffer")
	public ModelAndView createItemOffer(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			if(session.getAttribute("item") != null) {
				Long itemId = Long.parseLong(session.getAttribute("item").toString()) ;
				Long count = itemOffersService.countByItemId(itemId) ;
				if(count == 0) {
					List<OffersDTO> availableOffersList = itemOffersService.availableOffers(itemId) ;
					if(availableOffersList != null && !availableOffersList.isEmpty()) {
						ItemsDTO item = itemService.findById(itemId) ;
						mv.addObject("item" , item) ;
						mv.addObject("dto" , new ItemOffersDTO()) ;
						mv.addObject("offer" , new OffersDTO()) ;
						mv.addObject("availableOffersList" , availableOffersList) ;
						mv.setViewName(itemOfferPage) ;
					}else {
						mv.addObject("noOffers" , 1) ;
						mv.setViewName("redirect: showtabs?tab=4") ;
					}
				}else {
					mv.addObject("itemOffersCountError" , 1) ;
					mv.setViewName("redirect: showtabs?tab=4") ;
				}
			}
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/resetSaveCategory")
	public ModelAndView resetSaveCategory(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			mv.setViewName("redirect: category") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/saveMainCategory")
	public ModelAndView saveMainCategory(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			Long id = null ;
			Integer isActive = null ;
			String categoryName = null ;
			int mode = Integer.parseInt(request.getParameter("mode") == null ? "1" : request.getParameter("mode")) ;
			
			if(request.getParameter("id") != null && !request.getParameter("id").equals("")) {
				id = Long.parseLong(request.getParameter("id")) ;
			}
			
			if(request.getParameter("isActive") != null && !request.getParameter("isActive").equals("")) {
				isActive = Integer.parseInt(request.getParameter("isActive")) ;
			}
			
			if(request.getParameter("categoryName") != null && !request.getParameter("categoryName").equals("")) {
				categoryName = request.getParameter("categoryName") ;
			}
			
			CategoryDTO dto = new CategoryDTO() ;
			if(mode == 1) {			// update
				dto.setId(id) ;
				dto.setUpdatedBy(user.getId()) ;
				dto.setUpdatedDate(new Date()) ;
			}else {					// insert
				dto.setCreatedBy(user.getId()) ;
				dto.setCreatedDate(new Date()) ;
			}			
			dto.setCategoryName(categoryName) ;
			dto.setIsActive(isActive) ;
			dto.setParentCategoryId(null) ;
			
			categoryService.save(dto) ;
			mv.addObject("saved" , 1) ;
			mv.setViewName("redirect: goMainCategory") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/createMainCategory")
	public ModelAndView createMainCategory(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("user") != null) {		
			List<CategoryDTO> mainCategoryList = categoryService.findByParentCategoryId(null) ;			
			if(mainCategoryList == null) {
				mainCategoryList = new ArrayList<>() ;
			}
			mainCategoryList.add(0 , new CategoryDTO()) ;
			mv.addObject("list" , mainCategoryList) ;
			mv.addObject("total" , mainCategoryList.size()) ;
			mv.setViewName(mainCategoryPage) ;				
		}else {
			mv.setViewName(loginPage) ;
		}
		
		return mv ;
	}
	
	

	public List<CategoryDTO> getList() {
		return categories;
	}
	public void setList(List<CategoryDTO> categories) {
		this.categories = categories;
	}




	public List<ItemsDTO> getItems() {
		return items;
	}	
	public void setItems(List<ItemsDTO> items) {
		this.items = items;
	}




	public Map<String, Object> getMetaData() {
		return metaData;
	}
	public void setMetaData(Map<String, Object> metaData) {
		this.metaData = metaData;
	}




	public List<GeneralDTO> getCurrencyList() {
		return currencyList;
	}
	public void setCurrencyList(List<GeneralDTO> currencyList) {
		this.currencyList = currencyList;
	}




	public List<GeneralDTO> getColorList() {
		return colorList;
	}
	public void setColorList(List<GeneralDTO> colorList) {
		this.colorList = colorList;
	}




	public ItemsDTO getDto() {
		return dto;
	}
	public void setDto(ItemsDTO dto) {
		this.dto = dto;
	}

}
