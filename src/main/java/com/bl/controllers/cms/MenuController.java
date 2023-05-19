package com.bl.controllers.cms;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bl.DomainValues;
import com.bl.dto.CategoryDTO;
import com.bl.dto.cms.LoginUserDTO;
import com.bl.dto.cms.MainMenuDTO;
import com.bl.service.cms.CategoryService;
import com.bl.service.cms.MainMenuService;

@Controller
public class MenuController {

	private String menuPage = "settings/menu" ;								// The main page of menu
	private String menuItemsPage = "settings/menu_items" ;			// The items and actions of each menu
	private String menuLinksPage = "settings/menu_links" ;
	private String loginPage = "login" ;
	
	private List<MainMenuDTO> list ;
	
	private MainMenuDTO dto ;
	
	@Autowired
	private MainMenuService service ;
	@Autowired
	private CategoryService categoryService ;
	
	@GetMapping("aao/menu")
	public ModelAndView menuList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null){
			list = service.findAll() ;
			mv.addObject("mainMenuList" , list) ;
			mv.setViewName(menuPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
 	}

	
	@RequestMapping("aao/links")
	public ModelAndView links(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null){
			if(request.getParameter("menuId") != null && !request.getParameter("menuId").equals("")) {
				Integer menuId = Integer.parseInt(request.getParameter("menuId")) ;				
				session.setAttribute("menuId" , menuId) ;
				
				mv.addObject("cat" , new CategoryDTO()) ;
				mv.addObject("categoryList" , categoryService.findByMenuIdNot(menuId)) ;		
				mv.addObject("mainCategoryList" , categoryService.findByParentCategoryIdIsNull(menuId)) ;
				mv.addObject("list" , service.findAllCategoryByMenuId(menuId)) ;
				mv.setViewName(menuItemsPage) ;
			}						
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping(value = "aao/saveMenu" , method = RequestMethod.POST)
	public ModelAndView saveMenu(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null){
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			
			Integer id = null ;
			MainMenuDTO menu = null ;
			
			if(request.getParameter("id") != null && !request.getParameter("id").equals("")) {
				id = Integer.parseInt(request.getParameter("id")) ;
				menu = service.findById(id) ;				
				menu.setId(id) ;
				menu.setUpdatedBy(user.getId()) ;
				menu.setUpdatedDate(new Date()) ;
				menu.setMenuName(request.getParameter("menuName")) ;				
			}else {
				if(menu == null) {
					menu = new MainMenuDTO() ;					
				}
				menu.setIsActive(1) ;
				menu.setCreatedBy(user.getId()) ;
				menu.setCreatedDate(new Date()) ;
				menu.setUpdatedBy(user.getId()) ;
				menu.setUpdatedDate(new Date()) ;
				menu.setMenuName(request.getParameter("menuName")) ;
			}
			service.save(menu) ;
			mv.addObject("mainMenuList" , service.findAll()) ;
			mv.setViewName("redirect: links") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping(value = "aao/saveMenuLink" , method = RequestMethod.POST , params = "save")
	public ModelAndView saveMenuLink(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null){
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			CategoryDTO dto = null ;
			Integer menuId = Integer.parseInt(session.getAttribute("menuId") == null ? "0" : session.getAttribute("menuId").toString()) ;
			Long categoryId = Long.parseLong(request.getParameter("categoryId")) ;
			Long parentCategoryId = Long.parseLong(request.getParameter("parentCategoryId")) ;
			
			if(parentCategoryId != 0 && categoryId != 0) {
				dto = categoryService.findById(categoryId) ;
			}else if(parentCategoryId != 0 && categoryId == 0){				
				dto = categoryService.findById(parentCategoryId) ;
			}
			if(menuId != null && menuId != 0) {
				dto.setMenuId(menuId) ;
			}
			dto.setUpdatedBy(user.getId()) ;
			dto.setUpdatedDate(new Date()) ;
			categoryService.save(dto) ;
			
			mv.addObject("saved" , 1) ;
			mv.addObject("dto" , dto) ;
			mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER)) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping(value = "aao/saveMenuLink" , method = RequestMethod.GET , params = "cancel")
	public ModelAndView cancelSaveMenuLink(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null){
			mv.setViewName("redirect: menu") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/removeMenuLink")
	public ModelAndView removeMenuLink(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null){
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			Integer menuId = Integer.parseInt(request.getParameter("menu")) ;
			Long categoryId = Long.parseLong(request.getParameter("id")) ;
			
			CategoryDTO dto = categoryService.findById(categoryId) ;
			dto.setMenuId(null) ;
			dto.setUpdatedBy(user.getId()) ;
			dto.setUpdatedDate(new Date()) ;
			categoryService.save(dto) ;
			
			mv.addObject("removed" , 1) ;
			mv.addObject("dto" , dto) ;
			mv.addObject("categoryList" , categoryService.findByMenuIdNot(menuId)) ;
			mv.addObject("list" , service.findAllCategoryByMenuId(menuId)) ;
			mv.setViewName(menuLinksPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping(value = "aao/saveMenuShopLink" , method = RequestMethod.GET , params = "save")
	public ModelAndView saveMenuShopLink(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			Long parentCategoryId = Long.parseLong(request.getParameter("parentCategoryId")) ;
			Long categoryId = Long.parseLong(request.getParameter("categoryId")) ;
			
			if(parentCategoryId != 0L) {
				CategoryDTO category = new CategoryDTO() ;
				category.setMenuId(1) ;
				category.setId(parentCategoryId) ;
				category.setUpdatedBy(user.getId()) ;
				
				if(categoryId != 0L) {
					CategoryDTO parent = new CategoryDTO() ;
					parent.setMenuId(1) ;
					parent.setId(categoryId) ;
					parent.setUpdatedBy(user.getId()) ;
					
					service.saveCategoryMenuId(category , parent) ;
				}else {
					category = categoryService.findById(parentCategoryId) ;
					category.setMenuId(1) ;
					category.setUpdatedBy(user.getId()) ;
					categoryService.save(category) ;
				}
				
				CategoryDTO dto = new CategoryDTO() ;
				dto.setId(categoryId) ;
				dto.setParentCategoryId(parentCategoryId) ;
				
				mv.addObject("dto" , dto) ;
				mv.addObject("list" , service.findAllCategoryByMenuId(1)) ;				
//				mv.addObject("mainCategoryList" , categoryService.findByParentCategoryIdIsNull()) ;
				mv.addObject("categoryList" , categoryService.findByParentCategoryId(parentCategoryId)) ;
			}
			mv.setViewName(menuLinksPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	@RequestMapping(value = "aao/saveMenuShopLink" , method = RequestMethod.GET , params = "cancel")
	public ModelAndView cancelSaveMenuShopLink(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null){
			mv.setViewName("redirect: menu") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/removeMenuShopLink")
	public ModelAndView removeMenuShopLink(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			Long categoryId = Long.parseLong(request.getParameter("id")) ;
			
			CategoryDTO dto = categoryService.findById(categoryId) ;
			dto.setMenuId(null) ;
			dto.setUpdatedBy(user.getId()) ;
			dto.setUpdatedDate(new Date()) ;
			categoryService.save(dto) ;
			
			mv.setViewName("redirect: links?menuId=1") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/actMenu")
	public ModelAndView actMenu(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null){
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			
			int flag = 0 ;
			Integer id = null ;
			MainMenuDTO menu = null ;
			
			if(request.getParameter("flag") != null && !request.getParameter("flag").equals("")) {
				flag = Integer.parseInt(request.getParameter("flag")) ;
				id = Integer.parseInt(request.getParameter("id")) ;
				menu = service.findById(id) ;
				menu.setId(id) ;
				menu.setUpdatedBy(user.getId()) ;
				menu.setUpdatedDate(new Date()) ;
				menu.setIsActive(flag) ;				
				service.save(menu) ;
			}			
			list = service.findAll() ;
			mv.addObject("mainMenuList" , list) ;
			mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER)) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/categoryByMain")
	public ModelAndView categoryByMain(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			Long parentCategoryId = Long.parseLong(request.getParameter("parent")) ;
			Integer menuId = Integer.parseInt(session.getAttribute("menuId").toString()) ;
			if(parentCategoryId != 0L) {
				CategoryDTO cat = new CategoryDTO() ;
				if(menuId != null && menuId != 0) {
					cat.setMenuId(menuId) ;
				}
				cat.setParentCategoryId(parentCategoryId) ;
				
				mv.addObject("menuid" , menuId) ;
				mv.addObject("cat" , cat) ;
				mv.addObject("list" , service.findAllCategoryByMenuId(menuId)) ;
				mv.addObject("mainCategoryList" , categoryService.findByParentCategoryIdIsNull(menuId)) ;
				mv.addObject("categoryList" , categoryService.findByParentCategoryIdAndMenuId(parentCategoryId , menuId)) ;
			}
			mv.setViewName(menuItemsPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@GetMapping("aao/createMenu")
	public ModelAndView createMenu(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			dto = new MainMenuDTO() ;
			dto.setCreatedBy(user.getId()) ;
			dto.setCreatedDate(new Date()) ;
			dto.setIsActive(null) ;
			list.add(0 , dto)  ;
			mv.addObject("mainMenuList" , list) ;
			mv.setViewName(menuPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	public List<MainMenuDTO> getList() {
		return list;
	}

	public void setList(List<MainMenuDTO> list) {
		this.list = list;
	}


	public MainMenuDTO getDto() {
		return dto;
	}


	public void setDto(MainMenuDTO dto) {
		this.dto = dto;
	}
}
