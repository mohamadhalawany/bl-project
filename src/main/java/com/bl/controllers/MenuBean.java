package com.bl.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.bl.DomainValues;
import com.bl.dto.CategoryDTO;
import com.bl.dto.CustomersDTO;
import com.bl.dto.OrderRequestDTO;
import com.bl.dto.cms.BrandDTO;
import com.bl.dto.cms.CompanyInfoDTO;
import com.bl.dto.cms.MainMenuDTO;
import com.bl.service.CustomerOdersService;
import com.bl.service.MainMenuService;
import com.bl.service.impl.CustomerOdersServiceImpl;
import com.bl.service.impl.MainMenuServiceImpl;

@ManagedBean
public class MenuBean {
	
	private List<MainMenuDTO> list ;
	private List<CategoryDTO> mainCategoryList ;
	private List<BrandDTO> brandsList ; 
	private List<OrderRequestDTO> orderRequestList ;
	
	private MainMenuDTO menu ;	
	private CompanyInfoDTO company ;
	
	@PostConstruct
	public void init() {
		MainMenuService service = new MainMenuServiceImpl() ;
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true) ;
		
		list = service.findAllByIsActive() ;
		mainCategoryList = service.findAllMainCategories() ;
		brandsList = service.brandsMenu() ;
		menu = service.findById(1) ;		
		company = service.companyProfile() ;
		
		if(company == null) {
			company = new CompanyInfoDTO() ;
			company.setMobile("+2 01002080807") ;
			company.setPhone("+2 0233994253") ;
			company.setLogo("default-logo.png") ;
			company.setBrowserTitleIcon("default-logo.png") ;
			company.setAddress("10 Der Yassin st, Tersa, Haram") ;
			company.setCityName("Giza") ;
			company.setGovernorateName("Giza") ;
			company.setCountryName("Egypt") ;
			company.setEmail("mohamad.alhalawany@gmail.com") ;
			company.setCompanyName("AAO Shop") ;
			session.setAttribute("company" , 1) ;
		}
		
		if(session.getAttribute(DomainValues.SessionKeys.CUSTOMER) != null) {
			CustomersDTO customer = (CustomersDTO) session.getAttribute(DomainValues.SessionKeys.CUSTOMER) ;
			CustomerOdersService customerOdersService = new CustomerOdersServiceImpl() ;
			orderRequestList = customerOdersService.customerOrdersList(customer.getId() , DomainValues.OrderStatus.ADD_TO_CART) ;
		}else {
			orderRequestList = null ;
		}
	}

	
	

	public List<MainMenuDTO> getList() {
		return list;
	}


	public void setList(List<MainMenuDTO> list) {
		this.list = list;
	}


	public MainMenuDTO getMenu() {
		return menu;
	}


	public void setMenu(MainMenuDTO menu) {
		this.menu = menu;
	}


	public List<CategoryDTO> getMainCategoryList() {
		return mainCategoryList;
	}


	public void setMainCategoryList(List<CategoryDTO> mainCategoryList) {
		this.mainCategoryList = mainCategoryList;
	}


	public List<BrandDTO> getBrandsList() {
		return brandsList;
	}


	public void setBrandsList(List<BrandDTO> brandsList) {
		this.brandsList = brandsList;
	}


	public CompanyInfoDTO getCompany() {
		return company;
	}


	public void setCompany(CompanyInfoDTO company) {
		this.company = company;
	}


	public List<OrderRequestDTO> getOrderRequestList() {
		return orderRequestList;
	}


	public void setOrderRequestList(List<OrderRequestDTO> orderRequestList) {
		this.orderRequestList = orderRequestList;
	}

}
