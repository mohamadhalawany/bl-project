package com.bl.controllers.cms;

import java.text.SimpleDateFormat;
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
import com.bl.dto.ItemsDTO;
import com.bl.dto.cms.BrandDTO;
import com.bl.dto.cms.CareerDTO;
import com.bl.dto.cms.CompanyTermsConditionDTO;
import com.bl.dto.cms.LoginUserDTO;
import com.bl.dto.cms.MainPageBlockDTO;
import com.bl.dto.cms.ProductTypeDTO;
import com.bl.dto.cms.SettingDTO;
import com.bl.service.ItemService;
import com.bl.service.cms.BrandService;
import com.bl.service.cms.CareerService;
import com.bl.service.cms.MainPageBlockService;
import com.bl.service.cms.ProductTypeService;
import com.bl.service.cms.SettingService;

@Controller
public class SettingController {
	
	private String setting = "settings/setting" ;
	private String brandsPage = "settings/brands" ;
	private String productTypePage = "settings/product_type" ;
	private String mainPageBlocksPage = "settings/blocks" ;	
	private String expirePage = "settings/expire" ;
	private String termsConditionsPage = "settings/terms_conditions" ;
	private String careerPage = "settings/career" ;
	private String addCareerPage = "settings/career_form" ;
	private String loginPage = "login" ;
	
	private List<ItemsDTO> itemsList ;
	private List<SettingDTO> sliceList ;
	private List<ProductTypeDTO> productTypeList ;
	private List<BrandDTO> brandsList ;
	private Map<String, Object> metaData ;
	private Map<String, Object> brandsMetaData ;	
	private Map<String, Object> productTypeMetaData ;
	private SettingDTO dto ;	
		
	@Autowired
	private SettingService settingService ;	
	@Autowired
	private ItemService itemService ;	
	@Autowired
	private BrandService brandService ;	
	@Autowired
	private ProductTypeService productTypeService ;
	@Autowired
	private MainPageBlockService mainPageBlockService ;
	@Autowired
	private CareerService careerService ;
	
	
	
	
	@RequestMapping("aao/slice")
	public ModelAndView slice(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			sliceList = settingService.findAllForSlice() ;			
			
			mv.addObject("dto" , dto) ;
			mv.addObject("sliceList" , sliceList) ;
			mv.setViewName(setting) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/showSlice")
	public ModelAndView showSlice(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			sliceList = settingService.findAllForSlice() ;
			if(request.getParameter("id") != null) {
				dto = settingService.findById(Integer.parseInt(request.getParameter("id"))) ;
			}
			
			mv.addObject("dto" , dto) ;
			mv.addObject("sliceList" , sliceList) ;
			mv.setViewName(setting) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/mainpic")
	public ModelAndView mainPic(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			dto = settingService.findById(1) ;
			mv.addObject("dto" , dto) ;
			mv.setViewName(setting) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/nextSetting")
	public ModelAndView next(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			itemsList = settingService.next() ;
			metaData = settingService.metaData() ;
			
			mv.addObject("dto" , dto) ;
			mv.addObject("list" , itemsList) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(setting) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/previousSetting")
	public ModelAndView previous(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			itemsList = settingService.previous() ;
			metaData = settingService.metaData() ;
			
			mv.addObject("dto" , dto) ;
			mv.addObject("list" , itemsList) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("total" ,  metaData.get("total")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(setting) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}

	
	
	@RequestMapping("aao/hideItem")
	public ModelAndView hide(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			Long id = Long.parseLong(request.getParameter("id")) ;
			ItemsDTO item = itemService.findById(id) ;
			item.setIsHidden(1) ;
			itemService.save(item) ;
			
			mv.addObject("dto" , dto) ;
			mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER)) ;
		}
		return mv ;
	}
	
	
	@RequestMapping(value = "aao/saveSliceItems" , method = RequestMethod.POST , params = "save")
	public ModelAndView save(HttpServletRequest request , @RequestParam(value = "mainPicture" , required = false) MultipartFile mainPicture) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			String mainPictureDescription = request.getParameter("mainPictureDescription") ;
			String mainPictureDescriptionLine = request.getParameter("mainPictureDescriptionLine") ;
			String mainPictureFileName = null ;
			String fileExt = null ;
			
			if(mainPicture != null && !mainPicture.isEmpty()) {
				mainPictureFileName = mainPicture.getOriginalFilename() ;
				fileExt = mainPictureFileName.substring(mainPictureFileName.lastIndexOf(".") + 1 , mainPictureFileName.length()) ;

				if(!fileExt.equalsIgnoreCase("jpg") && !fileExt.equalsIgnoreCase("jpeg") && !fileExt.equalsIgnoreCase("png") && !fileExt.equalsIgnoreCase("webp")) {					
					mv.addObject("extensionError" , 1) ;
					mv.addObject("saved" , null) ;
					mv.addObject("dto" , dto) ;					
				}else if(mainPicture.getSize() > (10 * 1024 * 1024)) {
					mv.addObject("sizeError" , 1) ;
					mv.addObject("saved" , null) ;
					mv.addObject("dto" , dto) ;
				}else {
					mainPictureFileName = new Date().getTime() + "_" + user.getId() + "." + fileExt ;
					HelperUtils.uploadImages(2 , mainPictureFileName , mainPicture) ;
					dto.setMainPicture(mainPictureFileName) ;
					
					if(request.getParameter("id") != null && !request.getParameter("id").equals("")) {
						Integer id = Integer.parseInt(request.getParameter("id")) ;
						dto = settingService.findById(id) ;
						dto.setUpdatedBy(user.getId()) ;
						dto.setUpdatedDate(new Date()) ;
						dto.setMainPictureDescription(mainPictureDescription) ;
						dto.setMainPictureDescriptionLine(mainPictureDescriptionLine) ;
					}else {
						dto = new SettingDTO() ;
						dto.setCreatedBy(user.getId()) ;
						dto.setCreatedDate(new Date()) ;
					}				
					settingService.save(dto) ;
				}						
			}else {
				if(request.getParameter("id") != null && !request.getParameter("id").equals("")) {
					Integer id = Integer.parseInt(request.getParameter("id")) ;
					dto = settingService.findById(id) ;
					dto.setUpdatedBy(user.getId()) ;
					dto.setUpdatedDate(new Date()) ;
					dto.setMainPictureDescription(mainPictureDescription) ;
					dto.setMainPictureDescriptionLine(mainPictureDescriptionLine) ;
					settingService.save(dto) ;
				}
			}
			
			dto = settingService.findById(1) ;
			
			mv.addObject("dto" , dto) ;
			mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER)) ; 
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}

	
	@RequestMapping(value = "aao/saveMainPic" , method = RequestMethod.POST , params = "save")
	public ModelAndView saveMainPic(HttpServletRequest request , @RequestParam(value = "mainPicture" , required = false) MultipartFile mainPicture) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			
			String extension = null ;
			
			if(mainPicture != null && !mainPicture.isEmpty()) {
				String fileName = mainPicture.getOriginalFilename() ;
				extension = fileName.substring(fileName.lastIndexOf(".") + 1 , fileName.length()) ;
								
				dto = settingService.findById(1) ;
				
				if(!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("jpeg") && !extension.equalsIgnoreCase("png") && !extension.equalsIgnoreCase("webp")) {					
					mv.addObject("extensionError" , 1) ;
					mv.addObject("saved" , null) ;
					mv.addObject("dto" , dto) ;					
				}else if(mainPicture.getSize() > (10 * 1024 * 1024)) {
					mv.addObject("sizeError" , 1) ;
					mv.addObject("saved" , null) ;
					mv.addObject("dto" , dto) ;
				}else {
					fileName = new Date().getTime() + "_" + user.getId() + "." + extension ;
										
					dto.setId(1) ;
					dto.setMainPicture(fileName) ;
					dto.setUpdatedBy(user.getId()) ;
					dto.setUpdatedDate(new Date()) ;
					if(request.getParameter("mainPictureDescription") != null && !request.getParameter("mainPictureDescription").equals("")) {
						dto.setMainPictureDescription(request.getParameter("mainPictureDescription")) ;
					}
					
					settingService.save(dto) ;
					HelperUtils.uploadImages(2 , fileName , mainPicture) ;
					dto = settingService.findById(1) ;
					mv.addObject("dto" , dto) ;
					mv.addObject("saved" , 1) ;
					mv.addObject("extensionError" , null) ;
					mv.addObject("sizeError" , null) ;
					mv.addObject("fileError" , null) ;
				}
			}else {
				mv.addObject("fileError" , 1) ;
			}
			mv.setViewName("redirect: mainpic?page=2") ; 
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/brands")
	public ModelAndView brands(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			brandsList = brandService.findAll() ;
			brandsMetaData = brandService.metaData() ;
			
			mv.addObject("list" , brandsList) ;
			mv.addObject("currentPage" , brandsMetaData.get("currentPage")) ;
			mv.addObject("total" ,  brandsMetaData.get("total")) ;
			mv.addObject("totalPages" , brandsMetaData.get("totalPages")) ;
			mv.addObject("isFirst" , brandsMetaData.get("isFirst")) ;
			mv.addObject("isLast" , brandsMetaData.get("isLast")) ;
			mv.setViewName(brandsPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}

	
	@RequestMapping("aao/nextBrands")
	public ModelAndView nextBrands(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			brandsList = brandService.next() ;
			brandsMetaData = brandService.metaData() ;
			
			mv.addObject("list" , brandsList) ;
			mv.addObject("currentPage" , brandsMetaData.get("currentPage")) ;
			mv.addObject("total" ,  brandsMetaData.get("total")) ;
			mv.addObject("totalPages" , brandsMetaData.get("totalPages")) ;
			mv.addObject("isFirst" , brandsMetaData.get("isFirst")) ;
			mv.addObject("isLast" , brandsMetaData.get("isLast")) ;
			mv.setViewName(brandsPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/previousBrands")
	public ModelAndView previousBrands(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			brandsList = brandService.previous() ;
			brandsMetaData = brandService.metaData() ;
			
			mv.addObject("list" , brandsList) ;
			mv.addObject("currentPage" , brandsMetaData.get("currentPage")) ;
			mv.addObject("total" ,  brandsMetaData.get("total")) ;
			mv.addObject("totalPages" , brandsMetaData.get("totalPages")) ;
			mv.addObject("isFirst" , brandsMetaData.get("isFirst")) ;
			mv.addObject("isLast" , brandsMetaData.get("isLast")) ;
			mv.setViewName(brandsPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/activeBrands")
	public ModelAndView activeBrands(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			if(request.getParameter("id") != null) {
				LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
				
				Integer id = Integer.parseInt(request.getParameter("id").equals("") ? "0" : request.getParameter("id")) ;
				Integer flag = Integer.parseInt(request.getParameter("flag")) ;
				BrandDTO dto = brandService.findById(id) ;
				dto.setIsActive(flag) ;
				dto.setUpdatedBy(user.getId()) ;
				dto.setUpdatedDate(new Date()) ;
				Integer saved = brandService.save(dto) ;
				if(saved > 0) {
					saved = 1 ;
				}else {
					saved = null ;
				}
				
				brandsList = brandService.findAll() ;
				brandsMetaData = brandService.metaData() ;
				
				mv.addObject("saved" , saved) ;
				mv.addObject("list" , brandsList) ;
				mv.addObject("currentPage" , brandsMetaData.get("currentPage")) ;
				mv.addObject("total" ,  brandsMetaData.get("total")) ;
				mv.addObject("totalPages" , brandsMetaData.get("totalPages")) ;
				mv.addObject("isFirst" , brandsMetaData.get("isFirst")) ;
				mv.addObject("isLast" , brandsMetaData.get("isLast")) ;
			}
			mv.setViewName(brandsPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/deleteBrand")
	public ModelAndView deleteBrand(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			if(request.getParameter("id") != null) {
				Integer id = Integer.parseInt(request.getParameter("id").equals("") ? "0" : request.getParameter("id")) ;
				
				int delete = brandService.delete(id) ;
				
				brandsList = brandService.findAll() ;
				brandsMetaData = brandService.metaData() ;
				
				mv.addObject("delete" , delete) ;
				mv.addObject("list" , brandsList) ;
				mv.addObject("currentPage" , brandsMetaData.get("currentPage")) ;
				mv.addObject("total" ,  brandsMetaData.get("total")) ;
				mv.addObject("totalPages" , brandsMetaData.get("totalPages")) ;
				mv.addObject("isFirst" , brandsMetaData.get("isFirst")) ;
				mv.addObject("isLast" , brandsMetaData.get("isLast")) ;
			}
			mv.setViewName(brandsPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/createBrand")
	public ModelAndView createBrand(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			brandsList = brandService.findAll() ; 
			brandsList.add(0 , new BrandDTO()) ;
			brandsMetaData = brandService.metaData() ;
			
			mv.addObject("list" , brandsList) ;
			mv.addObject("currentPage" , brandsMetaData.get("currentPage")) ;
			mv.addObject("total" ,  brandsMetaData.get("total")) ;
			mv.addObject("totalPages" , brandsMetaData.get("totalPages")) ;
			mv.addObject("isFirst" , brandsMetaData.get("isFirst")) ;
			mv.addObject("isLast" , brandsMetaData.get("isLast")) ;
			mv.setViewName(brandsPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/cancelBrand")
	public ModelAndView cancelBrand(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			mv.setViewName("redirect: brands") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/saveBrand")
	public ModelAndView saveBrand(HttpServletRequest request , @RequestParam(value = "brandLogo" , required = false) MultipartFile brandLogo) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			BrandDTO dto = null ;
						
			if(!request.getParameter("id").equals("")) {				
				Integer id = Integer.parseInt(request.getParameter("id")) ;
				dto = brandService.findById(id) ;
				dto.setBrandName(request.getParameter("brandName")) ;
				dto.setUpdatedBy(user.getId()) ;
				dto.setUpdatedDate(new Date()) ;				
			}else {
				dto = new BrandDTO() ;
				dto.setBrandName(request.getParameter("brandName")) ;
				dto.setCreatedBy(user.getId()) ;
				dto.setCreatedDate(new Date()) ;
			}
			brandService.save(dto) ;
			mv.setViewName("redirect: brands") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping(value = "aao/saveBrandLogo" , method = RequestMethod.POST)
	public ModelAndView saveBrandLogo(HttpServletRequest request , @RequestParam(value = "brandLogo" , required = false) MultipartFile brandLogo) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			String extension = null ;
			String fileName = null ;
			
			if(brandLogo != null && !brandLogo.isEmpty()) {
				fileName = brandLogo.getOriginalFilename() ;
				extension = fileName.substring(fileName.lastIndexOf(".") + 1 , fileName.length()) ;
				if(!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("jpeg") && !extension.equalsIgnoreCase("png") 
						&& !extension.equalsIgnoreCase("webp")) {					
					mv.addObject("extensionError" , 1) ;
					mv.addObject("saved" , null) ;
					mv.addObject("dto" , dto) ;					
				}else if(brandLogo.getSize() > (10 * 1024 * 1024)) {
					mv.addObject("sizeError" , 1) ;
					mv.addObject("saved" , null) ;
					mv.addObject("dto" , dto) ;
				}else {
					BrandDTO dto = null ;
					fileName = new Date().getTime() + "_" + user.getId() + "." + extension ;

					if(!request.getParameter("id").equals("")) {												
						Integer id = Integer.parseInt(request.getParameter("id")) ;
						dto = brandService.findById(id) ;
						dto.setBrandLogo(fileName) ;
						dto.setUpdatedBy(user.getId()) ;
						dto.setUpdatedDate(new Date()) ;				
					}else {
						dto = new BrandDTO() ;
						dto.setBrandLogo(fileName) ;
						dto.setCreatedBy(user.getId()) ;
						dto.setCreatedDate(new Date()) ;
					}
					HelperUtils.uploadImages(1 , fileName , brandLogo) ;
					brandService.save(dto) ;					
				}
			}
			mv.setViewName("redirect: brands") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/productType")
	public ModelAndView productType(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			productTypeList = productTypeService.findAll() ;
			productTypeMetaData = productTypeService.metaData() ;
			
			mv.addObject("list" , productTypeList) ;
			mv.addObject("currentPage" , productTypeMetaData.get("currentPage")) ;
			mv.addObject("total" ,  productTypeMetaData.get("total")) ;
			mv.addObject("totalPages" , productTypeMetaData.get("totalPages")) ;
			mv.addObject("isFirst" , productTypeMetaData.get("isFirst")) ;
			mv.addObject("isLast" , productTypeMetaData.get("isLast")) ;
			mv.setViewName(productTypePage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/nextProductType")
	public ModelAndView nextProductType(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			productTypeList = productTypeService.next() ;
			productTypeMetaData = productTypeService.metaData() ;
			
			mv.addObject("list" , productTypeList) ;
			mv.addObject("currentPage" , productTypeMetaData.get("currentPage")) ;
			mv.addObject("total" ,  productTypeMetaData.get("total")) ;
			mv.addObject("totalPages" , productTypeMetaData.get("totalPages")) ;
			mv.addObject("isFirst" , productTypeMetaData.get("isFirst")) ;
			mv.addObject("isLast" , productTypeMetaData.get("isLast")) ;
			mv.setViewName(productTypePage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/previousProductType")
	public ModelAndView previousProductType(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			productTypeList = productTypeService.previous() ;
			productTypeMetaData = productTypeService.metaData() ;
			
			mv.addObject("list" , productTypeList) ;
			mv.addObject("currentPage" , productTypeMetaData.get("currentPage")) ;
			mv.addObject("total" ,  productTypeMetaData.get("total")) ;
			mv.addObject("totalPages" , productTypeMetaData.get("totalPages")) ;
			mv.addObject("isFirst" , productTypeMetaData.get("isFirst")) ;
			mv.addObject("isLast" , productTypeMetaData.get("isLast")) ;
			mv.setViewName(productTypePage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/createProductType")
	public ModelAndView createProductType(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			productTypeList = productTypeService.findAll() ;
			if(productTypeList == null) {
				productTypeList = new ArrayList<ProductTypeDTO>() ;
			}
			productTypeList.add(0 , new ProductTypeDTO()) ;
			productTypeMetaData = productTypeService.metaData() ;
			
			mv.addObject("list" , productTypeList) ;
			mv.addObject("currentPage" , productTypeMetaData.get("currentPage")) ;
			mv.addObject("total" ,  productTypeMetaData.get("total")) ;
			mv.addObject("totalPages" , productTypeMetaData.get("totalPages")) ;
			mv.addObject("isFirst" , productTypeMetaData.get("isFirst")) ;
			mv.addObject("isLast" , productTypeMetaData.get("isLast")) ;
			mv.setViewName(productTypePage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/cancelProductType")
	public ModelAndView cancelProductType(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			mv.setViewName("redirect: productType") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/saveProductType")
	public ModelAndView saveProductType(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			ProductTypeDTO dto = null ;
			if(!request.getParameter("id").equals("")) {				
				Integer id = Integer.parseInt(request.getParameter("id")) ;
				dto = productTypeService.findById(id) ;
				if(dto != null) {
					dto.setProductType(request.getParameter("productType")) ;
					dto.setUpdatedBy(user.getId()) ;
					dto.setUpdatedDate(new Date()) ;
				}
			}else {
				dto = new ProductTypeDTO() ;
				dto.setProductType(request.getParameter("productType")) ;
				dto.setCreatedBy(user.getId()) ;
				dto.setCreatedDate(new Date()) ;
				dto.setIsActive(1) ;
			}
			productTypeService.save(dto) ;
			mv.setViewName("redirect: productType") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	


	@RequestMapping("aao/activeProductType")
	public ModelAndView activeProductType(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			if(request.getParameter("id") != null) {
				LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
				
				Integer id = Integer.parseInt(request.getParameter("id").equals("") ? "0" : request.getParameter("id")) ;
				Integer flag = Integer.parseInt(request.getParameter("flag")) ;
				ProductTypeDTO dto = productTypeService.findById(id) ;
				dto.setIsActive(flag) ;
				dto.setUpdatedBy(user.getId()) ;
				dto.setUpdatedDate(new Date()) ;
				Integer saved = productTypeService.save(dto) ;
				if(saved > 0) {
					saved = 1 ;
				}else {
					saved = null ;
				}
			}
			mv.setViewName("redirect: productType") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/deleteProductType")
	public ModelAndView deleteProductType(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			if(request.getParameter("id") != null) {
				Integer id = Integer.parseInt(request.getParameter("id").equals("") ? "0" : request.getParameter("id")) ;
				
				Integer delete = productTypeService.delete(id) ;					
				mv.addObject("delete" , delete) ;
			}			
			mv.setViewName("redirect: productType") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/blocks")
	public ModelAndView blocks(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			MainPageBlockDTO dto = mainPageBlockService.findById(1) ;
			mv.addObject("dto" , dto) ;
			mv.setViewName(mainPageBlocksPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping(value = "aao/saveBlockOne" , method = RequestMethod.POST)
	public ModelAndView saveBlocks(HttpServletRequest request , @RequestParam(value = "blockOnePic" , required = false) MultipartFile blockOne) {
		
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			MainPageBlockDTO dto = mainPageBlockService.findById(1) ;
			
			String blockOnePic = null ;
			String blockOnePicExt = null ;				
			int saved = 0 ;

			if(dto.getBlockOnePic() == null && blockOne.getOriginalFilename().equals("")) {
				mv.addObject("fileErrorOne" , 1) ;
			}else {
				if(blockOne != null && !blockOne.isEmpty()) {
					blockOnePic = blockOne.getOriginalFilename() ;
					blockOnePicExt = blockOnePic.substring(blockOnePic.lastIndexOf(".") + 1 , blockOnePic.length()) ;
	
					if(!blockOnePicExt.equalsIgnoreCase("jpg") && !blockOnePicExt.equalsIgnoreCase("jpeg") && !blockOnePicExt.equalsIgnoreCase("png") 
							&& !blockOnePicExt.equalsIgnoreCase("webp")) {		
						
						mv.addObject("extensionErrorOne" , 1) ;
						mv.addObject("savedOne" , null) ;
						mv.addObject("dto" , dto) ;					
					}else if(blockOne.getSize() > (10 * 1024 * 1024)) {
						mv.addObject("sizeErrorOne" , 1) ;
						mv.addObject("savedOne" , null) ;
						mv.addObject("dto" , dto) ;
					}else {
						
						dto.setBlockOnePic(blockOnePic) ;		
						HelperUtils.uploadImages(2 , blockOnePic, blockOne) ;
						if(request.getParameter("blockOneDesc") != null && !request.getParameter("blockOneDesc").equals("")) {
							dto.setBlockOneDesc(request.getParameter("blockOneDesc")) ;
						}								
						saved = mainPageBlockService.save(dto) ;
						mv.addObject("savedOne" , saved) ;
					}			
				}else {
					if(request.getParameter("blockOneDesc") != null && !request.getParameter("blockOneDesc").equals("")) {
						dto.setBlockOneDesc(request.getParameter("blockOneDesc")) ;
					}								
					saved = mainPageBlockService.save(dto) ;
					mv.addObject("savedOne" , saved) ;
				}
			}
			dto = mainPageBlockService.findById(1) ;
			
			mv.addObject("dto" , dto) ;			
			mv.setViewName(mainPageBlocksPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping(value = "aao/saveBlocksTwo" , method = RequestMethod.POST)
	public ModelAndView saveBlocksTwo(HttpServletRequest request , @RequestParam(value = "blockTwoPic" , required = false) MultipartFile blockTwo) {
		
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			MainPageBlockDTO dto = mainPageBlockService.findById(1) ;
			
			String blockTwoPic = null ;
			String blockTwoPicExt = null ;				
			int saved = 0 ;

			if(dto.getBlockTwoPic() == null && blockTwo.getOriginalFilename().equals("")) {
				mv.addObject("fileErrorTwo" , 1) ;
			}else {
				if(blockTwo != null && !blockTwo.isEmpty()) {
					blockTwoPic = blockTwo.getOriginalFilename() ;
					blockTwoPicExt = blockTwoPic.substring(blockTwoPic.lastIndexOf(".") + 1 , blockTwoPic.length()) ;
	
					if(!blockTwoPicExt.equalsIgnoreCase("jpg") && !blockTwoPicExt.equalsIgnoreCase("jpeg") && !blockTwoPicExt.equalsIgnoreCase("png") 
							&& !blockTwoPicExt.equalsIgnoreCase("webp")) {		
						
						mv.addObject("extensionErrorTwo" , 1) ;
						mv.addObject("savedTwo" , null) ;
						mv.addObject("dto" , dto) ;					
					}else if(blockTwo.getSize() > (10 * 1024 * 1024)) {
						mv.addObject("sizeErrorTwo" , 1) ;
						mv.addObject("savedTwo" , null) ;
						mv.addObject("dto" , dto) ;
					}else {
						blockTwoPic = new Date().getTime() + "_" + user.getId() + "." + blockTwoPicExt ;
						dto.setBlockTwoPic(blockTwoPic) ;		
						HelperUtils.uploadImages(2 , blockTwoPic, blockTwo) ;
						
						if(request.getParameter("blockTwoDesc") != null && !request.getParameter("blockTwoDesc").equals("")) {
							dto.setBlockTwoDesc(request.getParameter("blockTwoDesc")) ;
						}								
						saved = mainPageBlockService.save(dto) ;
						mv.addObject("savedTwo" , saved) ;
					}			
				}else {
					if(request.getParameter("blockTwoDesc") != null && !request.getParameter("blockTwoDesc").equals("")) {
						dto.setBlockTwoDesc(request.getParameter("blockTwoDesc")) ;
					}								
					saved = mainPageBlockService.save(dto) ;
					mv.addObject("savedTwo" , saved) ;
				}
			}
			dto = mainPageBlockService.findById(1) ;
			
			mv.addObject("dto" , dto) ;			
			mv.setViewName(mainPageBlocksPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping(value = "aao/saveBlocksThree" , method = RequestMethod.POST)
	public ModelAndView saveBlocksThree(HttpServletRequest request , @RequestParam(value = "blockThreePic" , required = false) MultipartFile blockThree) {
		
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			MainPageBlockDTO dto = mainPageBlockService.findById(1) ;
			
			String blockThreePic = null ;
			String blockThreePicExt = null ;				
			int saved = 0 ;

			if(dto.getBlockThreePic() == null && blockThree.getOriginalFilename().equals("")) {
				mv.addObject("fileErrorThree" , 1) ;
			}else {
				if(blockThree != null && !blockThree.isEmpty()) {
					blockThreePic = blockThree.getOriginalFilename() ;
					blockThreePicExt = blockThreePic.substring(blockThreePic.lastIndexOf(".") + 1 , blockThreePic.length()) ;
	
					if(!blockThreePicExt.equalsIgnoreCase("jpg") && !blockThreePicExt.equalsIgnoreCase("jpeg") && !blockThreePicExt.equalsIgnoreCase("png") 
							&& !blockThreePicExt.equalsIgnoreCase("webp")) {		
						
						mv.addObject("extensionErrorThree" , 1) ;
						mv.addObject("savedThree" , null) ;
						mv.addObject("dto" , dto) ;					
					}else if(blockThree.getSize() > (10 * 1024 * 1024)) {
						mv.addObject("sizeErrorThree" , 1) ;
						mv.addObject("savedThree" , null) ;
						mv.addObject("dto" , dto) ;
					}else {
						blockThreePic = new Date().getTime() + "_" + user.getId() + "." + blockThreePicExt ;
						dto.setBlockThreePic(blockThreePic) ;		
						HelperUtils.uploadImages(2 , blockThreePic, blockThree) ;
						
						if(request.getParameter("blockThreePic") != null && !request.getParameter("blockThreePic").equals("")) {
							dto.setBlockThreeDesc(request.getParameter("blockThreePic")) ;
						}								
						saved = mainPageBlockService.save(dto) ;
						mv.addObject("savedThree" , saved) ;
					}			
				}else {
					if(request.getParameter("blockThreeDesc") != null && !request.getParameter("blockThreeDesc").equals("")) {
						dto.setBlockThreeDesc(request.getParameter("blockThreeDesc")) ;
					}								
					saved = mainPageBlockService.save(dto) ;
					mv.addObject("savedThree" , saved) ;
				}
			}
			dto = mainPageBlockService.findById(1) ;
			
			mv.addObject("dto" , dto) ;			
			mv.setViewName(mainPageBlocksPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping(value = "aao/saveBlocksFour" , method = RequestMethod.POST)
	public ModelAndView saveBlocksFour(HttpServletRequest request , @RequestParam(value = "blockFourPic" , required = false) MultipartFile blockFour) {
		
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			MainPageBlockDTO dto = mainPageBlockService.findById(1) ;
			
			String blockFourPic = null ;
			String blockFourPicExt = null ;				
			int saved = 0 ;

			if(dto.getBlockFourPic() == null && blockFour.getOriginalFilename().equals("")) {
				mv.addObject("fileErrorFour" , 1) ;
			}else {
				if(blockFour != null && !blockFour.isEmpty()) {
					blockFourPic = blockFour.getOriginalFilename() ;
					blockFourPicExt = blockFourPic.substring(blockFourPic.lastIndexOf(".") + 1 , blockFourPic.length()) ;
	
					if(!blockFourPicExt.equalsIgnoreCase("jpg") && !blockFourPicExt.equalsIgnoreCase("jpeg") && !blockFourPicExt.equalsIgnoreCase("png") 
							&& !blockFourPicExt.equalsIgnoreCase("webp")) {		
						
						mv.addObject("extensionErrorFour" , 1) ;
						mv.addObject("savedFour" , null) ;
						mv.addObject("dto" , dto) ;					
					}else if(blockFour.getSize() > (10 * 1024 * 1024)) {
						mv.addObject("sizeErrorFour" , 1) ;
						mv.addObject("savedFour" , null) ;
						mv.addObject("dto" , dto) ;
					}else {
						blockFourPic = new Date().getTime() + "_" + user.getId() + "." + blockFourPicExt ;
						dto.setBlockFourPic(blockFourPic) ;		
						HelperUtils.uploadImages(2 , blockFourPic , blockFour) ;
						
						if(request.getParameter("blockFourPic") != null && !request.getParameter("blockFourPic").equals("")) {
							dto.setBlockFourDesc(request.getParameter("blockFourPic")) ;
						}								
						saved = mainPageBlockService.save(dto) ;
						mv.addObject("savedFour" , saved) ;
					}			
				}else {
					if(request.getParameter("blockFourDesc") != null && !request.getParameter("blockFourDesc").equals("")) {
						dto.setBlockFourDesc(request.getParameter("blockFourDesc")) ;
					}								
					saved = mainPageBlockService.save(dto) ;
					mv.addObject("savedFour" , saved) ;
				}
			}
			dto = mainPageBlockService.findById(1) ;
			
			mv.addObject("dto" , dto) ;			
			mv.setViewName(mainPageBlocksPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping(value = "aao/saveBlocksFive" , method = RequestMethod.POST)
	public ModelAndView saveBlocksFive(HttpServletRequest request , @RequestParam(value = "blockFivePic" , required = false) MultipartFile blockFive) {
		
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			MainPageBlockDTO dto = mainPageBlockService.findById(1) ;
			
			String blockFivePic = null ;
			String blockFivePicExt = null ;				
			int saved = 0 ;

			if(dto.getBlockFivePic() == null && blockFive.getOriginalFilename().equals("")) {
				mv.addObject("fileErrorFive" , 1) ;
			}else {
				if(blockFive != null && !blockFive.isEmpty()) {
					blockFivePic = blockFive.getOriginalFilename() ;
					blockFivePicExt = blockFivePic.substring(blockFivePic.lastIndexOf(".") + 1 , blockFivePic.length()) ;
	
					if(!blockFivePicExt.equalsIgnoreCase("jpg") && !blockFivePicExt.equalsIgnoreCase("jpeg") && !blockFivePicExt.equalsIgnoreCase("png") 
							&& !blockFivePicExt.equalsIgnoreCase("webp")) {		
						
						mv.addObject("extensionErrorFive" , 1) ;
						mv.addObject("savedFive" , null) ;
						mv.addObject("dto" , dto) ;					
					}else if(blockFive.getSize() > (10 * 1024 * 1024)) {
						mv.addObject("sizeErrorFive" , 1) ;
						mv.addObject("savedFive" , null) ;
						mv.addObject("dto" , dto) ;
					}else {
						blockFivePic = new Date().getTime() + "_" + user.getId() + "." + blockFivePicExt ;
						dto.setBlockFivePic(blockFivePic) ;		
						HelperUtils.uploadImages(2 , blockFivePic , blockFive) ;
						
						if(request.getParameter("blockFivePic") != null && !request.getParameter("blockFivePic").equals("")) {
							dto.setBlockFiveDesc(request.getParameter("blockFivePic")) ;
						}								
						saved = mainPageBlockService.save(dto) ;
						mv.addObject("savedFive" , saved) ;
					}			
				}else {
					if(request.getParameter("blockFiveDesc") != null && !request.getParameter("blockFiveDesc").equals("")) {
						dto.setBlockFiveDesc(request.getParameter("blockFiveDesc")) ;
					}								
					saved = mainPageBlockService.save(dto) ;
					mv.addObject("savedFive" , saved) ;
				}
			}
			dto = mainPageBlockService.findById(1) ;
			
			mv.addObject("dto" , dto) ;			
			mv.setViewName(mainPageBlocksPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	@RequestMapping(value = "aao/saveBlocksSix" , method = RequestMethod.POST)
	public ModelAndView saveBlocksSix(HttpServletRequest request , @RequestParam(value = "blockSixPic" , required = false) MultipartFile blockSix) {
		
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			MainPageBlockDTO dto = mainPageBlockService.findById(1) ;
			
			String blockSixPic = null ;
			String blockSixPicExt = null ;				
			int saved = 0 ;

			if(dto.getBlockSixPic() == null && blockSix.getOriginalFilename().equals("")) {
				mv.addObject("fileErrorSix" , 1) ;
			}else {
				if(blockSix != null && !blockSix.isEmpty()) {
					blockSixPic = blockSix.getOriginalFilename() ;
					blockSixPicExt = blockSixPic.substring(blockSixPic.lastIndexOf(".") + 1 , blockSixPic.length()) ;
	
					if(!blockSixPicExt.equalsIgnoreCase("jpg") && !blockSixPicExt.equalsIgnoreCase("jpeg") && !blockSixPicExt.equalsIgnoreCase("png") 
							&& !blockSixPicExt.equalsIgnoreCase("webp")) {		
						
						mv.addObject("extensionErrorSix" , 1) ;
						mv.addObject("savedSix" , null) ;
						mv.addObject("dto" , dto) ;					
					}else if(blockSix.getSize() > (10 * 1024 * 1024)) {
						mv.addObject("sizeErrorSix" , 1) ;
						mv.addObject("savedSix" , null) ;
						mv.addObject("dto" , dto) ;
					}else {
						blockSixPic = new Date().getTime() + "_" + user.getId() + "." + blockSixPicExt ;
						dto.setBlockSixPic(blockSixPic) ;		
						HelperUtils.uploadImages(2 , blockSixPic , blockSix) ;
						
						if(request.getParameter("blockSixPic") != null && !request.getParameter("blockSixPic").equals("")) {
							dto.setBlockSixDesc(request.getParameter("blockSixPic")) ;
						}								
						saved = mainPageBlockService.save(dto) ;
						mv.addObject("savedSix" , saved) ;
					}			
				}else {					
					if(request.getParameter("blockSixDesc") != null && !request.getParameter("blockSixDesc").equals("")) {
						dto.setBlockSixDesc(request.getParameter("blockSixDesc")) ;
					}								
					saved = mainPageBlockService.save(dto) ;
					mv.addObject("savedSix" , saved) ;
				}
			}
			dto = mainPageBlockService.findById(1) ;
			
			mv.addObject("dto" , dto) ;			
			mv.setViewName(mainPageBlocksPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	
	
	
	
	@RequestMapping("aao/expire")
	public ModelAndView expireDate(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			SettingDTO dto = settingService.findById(4) ;
			if(dto == null) {
				dto = new SettingDTO() ;
				dto.setExpireDays(1) ;
			}

			mv.addObject("dto" , dto) ;
			mv.setViewName(expirePage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping(value = "aao/saveExpireDate" , method = RequestMethod.POST)
	public ModelAndView saveExpireDate(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			Integer expireDays = Integer.parseInt(request.getParameter("expireDays").equals("") ? "1" : request.getParameter("expireDays")) ;
			if(expireDays <= 0) {
				mv.addObject("error" , 1) ;
				mv.setViewName("redirect: expire") ;
				return mv ;
			}
			LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
			SettingDTO dto = settingService.findById(4) ;
			
			if(dto == null) {
				dto = new SettingDTO() ;				
			}
			dto.setId(4) ;
			dto.setExpireDays(expireDays) ;
			dto.setUpdatedBy(user.getId()) ;
			dto.setUpdatedDate(new Date()) ;
			settingService.save(dto) ;
			dto = settingService.findById(4) ;
			mv.addObject("dto" , dto) ;
			mv.addObject("saved" , 1) ;
			
			mv.setViewName("redirect: expire") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/terms_conditions")
	public ModelAndView termsConditions(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			CompanyTermsConditionDTO dto = settingService.findTermsConditionByCompanyId(1) ;	// CHANGE IT WHEN ESA LINK THE LOGGED IN USER WHITH COMPANY
			
			mv.addObject("dto" , dto) ;
			mv.setViewName(termsConditionsPage) ;			
 		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/save_terms_conditions")
	public ModelAndView saveTermsConditions(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			CompanyTermsConditionDTO dto = settingService.findTermsConditionByCompanyId(1) ;	// CHANGE IT WHEN ESA LINK THE LOGGED IN USER WHITH COMPANY
			if(dto == null) {
				dto = new CompanyTermsConditionDTO() ;
				dto.setId(1) ;
			}
			if(request.getParameter("termsConditions") != null && !request.getParameter("termsConditions").equals("")) {
				dto.setTermsConditions(request.getParameter("termsConditions")) ;
			}
			Integer save = settingService.saveTermsCondition(dto) ;
			if(save != null) {
				mv.addObject("saved" , 1)  ;
			}
			mv.setViewName("redirect: terms_conditions") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	@RequestMapping("aao/career")
	public ModelAndView career(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			List<CareerDTO> list = careerService.findAll() ;
			mv.addObject("list" , list) ;
			mv.setViewName(careerPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/add_career")
	public ModelAndView addCareer(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {			
			CareerDTO dto = new CareerDTO() ;
			new SimpleDateFormat("yyyy-MM-dd");
			Date createdDate = new Date() ;
			String today = (createdDate.getYear() + 1900) + "/" + (createdDate.getMonth() + 1) + "/" + createdDate.getDate() ;
			dto.setToday(today) ;
			mv.addObject("dto" , dto) ;
 			mv.setViewName(addCareerPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	public List<ItemsDTO> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<ItemsDTO> itemsList) {
		this.itemsList = itemsList;
	}



	public Map<String, Object> getMetaData() {
		return metaData;
	}
	public void setMetaData(Map<String, Object> metaData) {
		this.metaData = metaData;
	}



	public SettingDTO getDto() {
		return dto;
	}
	public void setDto(SettingDTO dto) {
		this.dto = dto;
	}



	public Map<String, Object> getBrandsMetaData() {
		return brandsMetaData;
	}
	public void setBrandsMetaData(Map<String, Object> brandsMetaData) {
		this.brandsMetaData = brandsMetaData;
	}



	public List<BrandDTO> getBrandsList() {
		return brandsList;
	}
	public void setBrandsList(List<BrandDTO> brandsList) {
		this.brandsList = brandsList;
	}



	public String getProductTypePage() {
		return productTypePage;
	}
	public void setProductTypePage(String productTypePage) {
		this.productTypePage = productTypePage;
	}



	public Map<String, Object> getProductTypeMetaData() {
		return productTypeMetaData;
	}
	public void setProductTypeMetaData(Map<String, Object> productTypeMetaData) {
		this.productTypeMetaData = productTypeMetaData;
	}



	public List<SettingDTO> getSliceList() {
		return sliceList;
	}
	public void setSliceList(List<SettingDTO> sliceList) {
		this.sliceList = sliceList;
	}
}
