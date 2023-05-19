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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bl.CustomValidation;
import com.bl.DomainValues;
import com.bl.HelperUtils;
import com.bl.dto.CategoryDTO;
import com.bl.dto.ItemsDTO;
import com.bl.dto.cms.CompanyInfoDTO;
import com.bl.dto.cms.ContactFormDTO;
import com.bl.dto.cms.FaqDTO;
import com.bl.dto.cms.LoginUserDTO;
import com.bl.dto.cms.SliderDTO;
import com.bl.service.GeneralService;
import com.bl.service.ItemService;
import com.bl.service.cms.CategoryService;
import com.bl.service.cms.CompanyInfoService;
import com.bl.service.cms.ContactFormService;
import com.bl.service.cms.FaqService;
import com.bl.service.cms.LoginUserService;
import com.bl.service.cms.SliderService;


@Controller
public class AdminController {
	
	private String loginPage = "login" ;
	private String usersPage = "users/users" ;
	private String searchPage = "users/search" ;
	private String companyPage = "settings/company" ;
	private String faqPage = "settings/faq" ;
	private String contactFormPage = "settings/contact_form" ;
	private String aboutUsPage = "settings/about_us" ;
	private String sliderPage = "settings/slider" ;
	private String sliderFormPage = "settings/slider_form" ;
		
	private List<LoginUserDTO> list = new ArrayList<LoginUserDTO>() ;
	private List<FaqDTO> faqList = new ArrayList<FaqDTO>() ;
	private List<ContactFormDTO> contactFormList = new ArrayList<ContactFormDTO>() ;
	private List<CategoryDTO> categoriesList = new ArrayList<CategoryDTO>() ;
	private List<ItemsDTO> itemsList = new ArrayList<ItemsDTO>() ;
	
	private SliderDTO dto ;
	
	@Autowired
	private LoginUserService service ;
	@Autowired
	private CompanyInfoService companyInfoService ;
	@Autowired
	private FaqService faqService ;
	@Autowired
	private GeneralService generalService ;
	@Autowired
	private ContactFormService contactFormService ;
	@Autowired
	private SliderService sliderService ;
	@Autowired
	private ItemService itemService ;	
	@Autowired
	private CategoryService categoryService ;
	
	@RequestMapping("aao/users")
	public ModelAndView users(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			mv.addObject("dto" , new LoginUserDTO()) ;
			mv.setViewName(usersPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/search")
	public ModelAndView search(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			list = service.findAll() ;
			Map<String , Object> metaData = service.metaData() ;
			
			mv.addObject("list" , list) ;		
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("totalIUsers" , list.size()) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	
	@RequestMapping(value = "aao/saveUser" , method = RequestMethod.POST , params = "save")
	public ModelAndView saveUser(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		
		String fullName = request.getParameter("fullName") ;
		String username = request.getParameter("username") ;
		String password = request.getParameter("password") ;
		
		LoginUserDTO dto = new LoginUserDTO() ;
		dto.setFullName(fullName) ;
		dto.setPassword(password) ;
		dto.setUsername(username) ;
		
		int found = service.countByUsername(username) ;
		if(found == 0) {
			dto.setActiveFlag(1) ;
			int saved = service.saveUser(dto) ;
			if(saved == 1) {
				mv.addObject("saved" , 1) ;
			}else {
				mv.addObject("saved" , null) ;
			}
			mv.addObject("dto" , dto) ;
		}else {
			mv.addObject("found" , 1) ;
			mv.addObject("dto" , new LoginUserDTO()) ;
		}
		mv.setViewName(usersPage) ;
		return mv ;
	}
	
	
	
	
	
	@RequestMapping("aao/next")
	public ModelAndView next(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			list = service.nextPage() ;
			Map<String , Object> metaData = service.metaData() ;
			
			mv.addObject("list" , list) ;		
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("totalIUsers" , list.size()) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	
	
	@RequestMapping("aao/previous")
	public ModelAndView previous(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			list = service.previousPage() ;
			Map<String , Object> metaData = service.metaData() ;
			
			mv.addObject("list" , list) ;		
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("totalIUsers" , list.size()) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	
	@RequestMapping("aao/active")
	public ModelAndView activeInactive(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
				
		if(session.getAttribute("user") != null) {
			int flag = 1 ;
			Integer id = 0 ;
			
			if(request.getParameter("flag") == null) {
				flag = 1 ;
			}else {
				flag = Integer.parseInt(request.getParameter("flag")) ;
			}
			
			if(request.getParameter("id") != null) {
				id = Integer.parseInt(request.getParameter("id")) ;
			}
			
			LoginUserDTO dto = service.findById(id) ;
			if(dto != null) {
				dto.setActiveFlag(flag) ;
				service.updateActiveAndInactive(id , flag) ;
			}
			mv.addObject("list" , service.findAll()) ;
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	
	@RequestMapping(value = "aao/doSearch" , method = RequestMethod.POST , params = "save")
	public ModelAndView doSearch(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			String username = null ;
			String fullName = null ;
			
			if(request.getParameter("username") != null && !request.getParameter("username").equals("")) {
				username = request.getParameter("username") ;
			}
			
			if(request.getParameter("fullName") != null && !request.getParameter("fullName").equals("")) {
				fullName = request.getParameter("fullName") ;
			}
			
			LoginUserDTO dto = new LoginUserDTO() ;
			dto.setFullName(fullName) ;
			dto.setUsername(username) ;
			
			mv.addObject("dto" , dto) ;
			mv.addObject("list" , service.searchByUsernameOrFullName(username , fullName)) ;
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		
		return mv ;
	}
	
	
	
	
	
	
	@RequestMapping(value = "aao/doSearch" , method = RequestMethod.POST , params = "reset")
	public ModelAndView reset(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		
		if(session.getAttribute("user") != null) {
			List<LoginUserDTO> list = service.findAll() ;
			Map<String , Object> metaData = service.metaData() ;
			
			mv.addObject("list" , list) ;		
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("totalIUsers" , list.size()) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.addObject("dto" , new LoginUserDTO()) ;			
			mv.setViewName(searchPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}

	
	
	
	@RequestMapping("aao/delete")
	public ModelAndView delete(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			if(request.getParameter("id") != null) {
				Integer id = Integer.parseInt(request.getParameter("id")) ;
				LoginUserDTO dto = service.findById(id) ;
				service.delete(dto) ;
				mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER)) ;
			}
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping("aao/company")
	public ModelAndView companyInfo(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			CompanyInfoDTO dto = companyInfoService.findById(1) ;
			if(dto == null) {
				dto = new CompanyInfoDTO() ;
			}
			
			if(session.getAttribute("countryId") != null) {
				Integer countryId = Integer.parseInt(session.getAttribute("countryId").toString()) ;
				dto.setCountryId(countryId) ;
				mv.addObject("governorateList" , generalService.governorateList(countryId)) ;
				
				if(session.getAttribute("governorateId") != null) {
					Integer governorateId = Integer.parseInt(session.getAttribute("governorateId").toString()) ;
					dto.setGovernorateId(governorateId) ;
					mv.addObject("cityDistrictList" , generalService.cityDistrictList(governorateId)) ;
				}
			}
			int aboutUsLength = dto.getAboutUs().length() ;
			if(aboutUsLength > 20) {
				String aboutUs = dto.getAboutUs() ;
				dto.setAboutUs(aboutUs.substring(0 , 20)) ;
			}
			mv.addObject("dto" , dto) ;
			init(mv) ;
			mv.setViewName(companyPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/govs_com")
	public ModelAndView govs(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			if(request.getParameter("countryId") != null) {
				Integer countryId = Integer.parseInt(request.getParameter("countryId")) ;
				session.setAttribute("countryId" , countryId) ;
			}
			mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER)) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/city_dist_com")
	public ModelAndView cityDist(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			if(request.getParameter("governorateId") != null) {
				Integer governorateId = Integer.parseInt(request.getParameter("governorateId")) ;
				session.setAttribute("governorateId" , governorateId) ;
			}
			mv.setViewName("redirect: " + request.getHeader(HttpHeaders.REFERER)) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@RequestMapping(value = "aao/saveCompanyInfo" , method = RequestMethod.POST)
	public ModelAndView saveCompanyInfo(HttpServletRequest request , @RequestParam("fileLogo") MultipartFile fileLogo , 
			@RequestParam("browserTitleIconFile") MultipartFile browserTitleIconFile) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute("user") != null) {
			LoginUserDTO user = (LoginUserDTO) session.getAttribute("user") ; 
			CompanyInfoDTO dto = new CompanyInfoDTO() ;
			
			if(companyInfoService.findById(1) == null) {
				dto = new CompanyInfoDTO() ;
			}else {
				dto = companyInfoService.findById(1) ;
			}
			String fileName = fileLogo.getOriginalFilename() ;
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1 , fileName.length()) ;
			
			String fileBrowserTitleIconFileName = browserTitleIconFile.getOriginalFilename() ;
			String fileBrowserTitleIconExtension = fileBrowserTitleIconFileName.substring(fileBrowserTitleIconFileName.lastIndexOf(".") + 1 , fileBrowserTitleIconFileName.length()) ;
			
			if(fileLogo != null && !fileLogo.isEmpty()) {
				if(!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("jpeg") && !extension.equalsIgnoreCase("png")) {
					mv.addObject("extensionError" , 1) ;
					mv.addObject("saved" , null) ;
					mv.setViewName("redirect: company") ;
					return mv ;
				}else if(fileLogo.getSize() > (10 * 1024 * 1024)) {
					mv.addObject("sizeError" , 1) ;
					mv.addObject("saved" , null) ;
					mv.setViewName("redirect: company") ;
					return mv ;
				}
			}
			
			if(browserTitleIconFile != null && !browserTitleIconFile.isEmpty()) {
				if(!fileBrowserTitleIconExtension.equalsIgnoreCase("jpg") && !fileBrowserTitleIconExtension.equalsIgnoreCase("jpeg") && !fileBrowserTitleIconExtension.equalsIgnoreCase("png")) {
					mv.addObject("extensionError" , 1) ;
					mv.addObject("saved" , null) ;
					mv.setViewName("redirect: company") ;
					return mv ;
				}else if(browserTitleIconFile.getSize() > (10 * 1024 * 1024)) {
					mv.addObject("sizeError" , 1) ;
					mv.addObject("saved" , null) ;
					mv.setViewName("redirect: company") ;
					return mv ;
				}
			}
			
			Integer id = null ;	
			String companyName = null ;
			Integer cityId = null ;
			String address = null ;
			String mobile = null ; 
			String phone = null ;
			String email = null ;
			String slogan = null ;
			Integer countryId = null ;
			Integer governorateId = null ;
			String facebookAccount = null ;
			String twitterAccount = null ;
			String googleplusAccount = null ;
			String linkedinAccount = null ;
			String youtubeAccount = null ; 
			String  vimeoAccount = null ;
			
			if(request.getParameter("id") != null && !request.getParameter("id").equals("")) {				
				id = Integer.parseInt(request.getParameter("id")) ;
				dto = companyInfoService.findById(1) ;
				dto.setId(id) ;
			}else {
				id = 1 ;
				dto.setId(id) ;
			}
						
			if(request.getParameter("companyName") != null && !request.getParameter("companyName").equals("")) {
				companyName = request.getParameter("companyName") ;
				dto.setCompanyName(companyName) ;
			}
			
			if(request.getParameter("cityId") != null && !request.getParameter("cityId").equals("")) {
				cityId = Integer.parseInt(request.getParameter("cityId")) ;
				dto.setCityId(cityId) ;
			}

			if(request.getParameter("address") != null && !request.getParameter("address").equals("")) {
				address = request.getParameter("address") ;
				dto.setAddress(address) ;
			}
			
			if(request.getParameter("mobile") != null && !request.getParameter("mobile").equals("")) {
				mobile = request.getParameter("mobile") ;
				dto.setMobile(mobile) ;
			}
			
			if(request.getParameter("phone") != null && !request.getParameter("phone").equals("")) {
				phone = request.getParameter("phone") ;
				dto.setPhone(phone) ;
			}
			
			if(request.getParameter("email") != null && !request.getParameter("email").equals("")) {
				email = request.getParameter("email") ;
				dto.setEmail(email) ;
			}
						
			if(request.getParameter("slogan") != null && !request.getParameter("slogan").equals("")) {
				slogan = request.getParameter("slogan") ;
				dto.setSlogan(slogan) ;
			}
			
			if(request.getParameter("countryId") != null && !request.getParameter("countryId").equals("")) {
				countryId = Integer.parseInt(request.getParameter("countryId")) ;
				dto.setCountryId(countryId) ;
			}
			
			if(request.getParameter("governorateId") != null && !request.getParameter("governorateId").equals("")) {
				governorateId = Integer.parseInt(request.getParameter("governorateId")) ;
				dto.setGovernorateId(governorateId) ;
			}
			
			if(request.getParameter("facebookAccount") != null && !request.getParameter("facebookAccount").equals("")) {
				facebookAccount = request.getParameter("facebookAccount") ;
				dto.setFacebookAccount(facebookAccount) ;
			}
			
			if(request.getParameter("twitterAccount") != null && !request.getParameter("twitterAccount").equals("")) {
				twitterAccount = request.getParameter("twitterAccount") ;
				dto.setTwitterAccount(twitterAccount) ;
			}
			
			if(request.getParameter("googleplusAccount") != null && !request.getParameter("googleplusAccount").equals("")) {
				googleplusAccount = request.getParameter("googleplusAccount") ;
				dto.setGoogleplusAccount(googleplusAccount) ;
			}
			
			if(request.getParameter("linkedinAccount") != null && !request.getParameter("linkedinAccount").equals("")) {
				linkedinAccount = request.getParameter("linkedinAccount") ;
				dto.setLinkedinAccount(linkedinAccount) ;
			}
			
			if(request.getParameter("youtubeAccount") != null && !request.getParameter("youtubeAccount").equals("")) {
				youtubeAccount = request.getParameter("youtubeAccount") ;
				dto.setYoutubeAccount(youtubeAccount) ;
			}
			
			if(request.getParameter("vimeoAccount") != null && !request.getParameter("vimeoAccount").equals("")) {
				vimeoAccount = request.getParameter("vimeoAccount") ;
				dto.setVimeoAccount(vimeoAccount) ;
			}
			dto.setUpdatedBy(user.getId()) ;
			dto.setUpdatedDate(new Date()) ;
			
			if(fileName == null || fileName.equals("")) {
				dto.setLogo(request.getParameter("logo")) ;
			}else {
				dto.setLogo(fileName) ;
			}
			
			if(fileBrowserTitleIconFileName == null || fileBrowserTitleIconFileName.equals("")) {
				dto.setBrowserTitleIcon(request.getParameter("browserTitleIcon")) ;
			}else {
				dto.setBrowserTitleIcon(fileBrowserTitleIconFileName) ;
			}

			HelperUtils.uploadImages(3 , fileName , fileLogo) ;
			HelperUtils.uploadImages(3 , fileBrowserTitleIconFileName , browserTitleIconFile) ;
			
			companyInfoService.save(dto) ;
			dto = companyInfoService.findById(1) ;
			mv.addObject("dto" , dto) ;
			mv.addObject("saved" , 1) ;
			mv.setViewName("redirect: company") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	@RequestMapping("aao/faq")
	public ModelAndView faq(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			faqList = faqService.findAll() ;
			Map<String , Object> metaData = faqService.metaData() ;
			
			mv.addObject("list" , faqList) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("totalIUsers" , list.size()) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.setViewName(faqPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	@RequestMapping("aao/createFaq")
	public ModelAndView createFaq(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			FaqDTO dto = new FaqDTO() ;
			if(request.getParameter("id") != null && !request.getParameter("id").equals("")) {
				Integer id = Integer.parseInt(request.getParameter("id")) ;
				dto = faqService.findById(id) ;
			}
			mv.addObject("dto" , dto) ;
			mv.setViewName(faqPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	@RequestMapping(value = "aao/saveFaq" , method = RequestMethod.POST , params = "save")
	public ModelAndView saveFaq(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;		
		FaqDTO dto = null ;
		
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {			
			if(request.getParameter("id") != null && !request.getParameter("id").equals("")) {				
				Integer id = Integer.parseInt(request.getParameter("id")) ;				
				dto = faqService.findById(id) ;
				dto.setId(id) ;				
				dto.setQuestion(request.getParameter("question")) ;
				dto.setAnswer(request.getParameter("answer")) ;
			}else {
				dto = new FaqDTO() ;
				dto.setQuestion(request.getParameter("question")) ;
				dto.setAnswer(request.getParameter("answer")) ;
			}
			faqService.save(dto) ;
			mv.addObject("dto" , dto) ;
			mv.addObject("save" , 1) ;
			mv.setViewName("redirect: faq?style=1") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/deleteFaq")
	public ModelAndView editFaq(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			if(request.getParameter("id") != null && !request.getParameter("id").equals("")) {
				Integer id = Integer.parseInt(request.getParameter("id")) ;
				faqService.delete(id) ;
			}
			mv.setViewName("redirect: faq?style=1&delete=1") ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	@RequestMapping("aao/nextFaq")
	public ModelAndView nextFaq(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			faqList = faqService.next() ;
			Map<String , Object> metaData = faqService.metaData() ;
			
			mv.addObject("list" , faqList) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("totalIUsers" , list.size()) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.addObject("style" , 1) ;
			mv.setViewName(faqPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}

	@RequestMapping("aao/previousFaq")
	public ModelAndView previousFaq(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			faqList = faqService.previous() ;
			Map<String , Object> metaData = faqService.metaData() ;
			
			mv.addObject("list" , faqList) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("totalIUsers" , list.size()) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.addObject("style" , 1) ;
			mv.setViewName(faqPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping("aao/contact")
	public ModelAndView contact(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			contactFormList = contactFormService.findAll() ;
			Map<String , Object> metaData = contactFormService.metaData() ;
			
			mv.addObject("list" , contactFormList) ;			
			mv.addObject("total" , contactFormList.size()) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.addObject("style" , 1) ;
			mv.setViewName(contactFormPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	@RequestMapping("aao/nextContact")
	public ModelAndView nextContact(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			contactFormList = contactFormService.next() ;
			Map<String , Object> metaData = contactFormService.metaData() ;
			
			mv.addObject("list" , contactFormList) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("totalIUsers" , list.size()) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.addObject("style" , 1) ;
			mv.setViewName(contactFormPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	@RequestMapping("aao/previousContact")
	public ModelAndView previousContact(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			contactFormList = contactFormService.previous() ;
			Map<String , Object> metaData = contactFormService.metaData() ;
			
			mv.addObject("list" , contactFormList) ;
			mv.addObject("currentPage" , metaData.get("currentPage")) ;
			mv.addObject("totalIUsers" , list.size()) ;
			mv.addObject("totalPages" , metaData.get("totalPages")) ;
			mv.addObject("isFirst" , metaData.get("isFirst")) ;
			mv.addObject("isLast" , metaData.get("isLast")) ;
			mv.addObject("style" , 1) ;
			mv.setViewName(contactFormPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	@RequestMapping("aao/about_us")
	public ModelAndView aboutUs(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			if(request.getParameter("id") != null) {
				Integer id = Integer.parseInt(request.getParameter("id")) ;
				CompanyInfoDTO dto = companyInfoService.findById(id) ;
				if(dto != null) {
					mv.addObject("dto" ,  dto) ;
					mv.setViewName(aboutUsPage) ;
				}
			}
			mv.setViewName(aboutUsPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@RequestMapping(value = "aao/saveAboutUs" , method = RequestMethod.POST , params = "save")
	public ModelAndView saveAboutUs(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			if(request.getParameter("id") != null && request.getParameter("aboutUs") != null && !request.getParameter("aboutUs").equals("")) {
				LoginUserDTO user = (LoginUserDTO) session.getAttribute(DomainValues.SessionKeys.USER) ;
				Integer id = Integer.parseInt(request.getParameter("id")) ;
				CompanyInfoDTO dto = companyInfoService.findById(id) ;
				dto.setAboutUs(request.getParameter("aboutUs")) ;
				dto.setUpdatedBy(user.getId()) ;
				dto.setUpdatedDate(new Date()) ;
				companyInfoService.save(dto) ;
				mv.addObject("dto" , dto) ;
				mv.addObject("saved" , 1) ;
				mv.setViewName("redirect: about_us?id=" + dto.getId()) ;
			}
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	@GetMapping("aao/slide")
	public ModelAndView slide(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {
			List<SliderDTO> list = sliderService.findAll() ;
			if(list != null && !list.isEmpty()) {
				mv.addObject("total" , list.size()) ;
			}
			mv.addObject("list" , list) ;
			mv.setViewName(sliderPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	@GetMapping("aao/slider-form")
	public ModelAndView sliderForm(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {			
			mv.setViewName(sliderFormPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	@GetMapping("aao/hide-item")
	public ModelAndView hideShowItemBlock(HttpServletRequest request , @RequestParam int hasItem) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {		
			if(hasItem == 1) {
				categoriesList = categoryService.findByParentCategoryIdIsNotNull() ;
				mv.addObject("categoriesList" , categoriesList) ;
				mv.addObject("hasItem" , 1) ;
			}else {
				mv.addObject("hasItem" , null) ;
			}
			mv.setViewName(sliderFormPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	@GetMapping("aao/show-item")
	public ModelAndView hideShowCategoryItemBlock(HttpServletRequest request , @RequestParam long categoryId) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {		
			itemsList = itemService.findAllByCategoryId(categoryId) ;
			dto = new SliderDTO() ;
			dto.setCategoryId(categoryId) ;
			
			mv.addObject("dto" , dto) ;
			mv.addObject("categoriesList" , categoriesList) ;
			mv.addObject("itemsList" , itemsList) ;
			mv.addObject("hasItem" , 1) ;
			mv.setViewName(sliderFormPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}		
		return mv ;
	}
	
	
	
	@GetMapping("aao/item-detail")
	public ModelAndView itemDetail(HttpServletRequest request , @RequestParam long id) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {		
			ItemsDTO item = itemService.findById(id) ;
			if(item != null && id != 0) {
				dto.setCurrencyNameEn(item.getCurrencyName()) ;
				dto.setItemPrice(item.getItemPrice()) ;
				dto.setItemId(item.getId()) ;
				dto.setFirstLine(item.getItemName()) ;
				dto.setUploadPic(item.getItemLogo()) ;
				
				mv.addObject("dto" , dto) ;
				mv.addObject("itemLogo" , item.getItemLogo()) ;
				mv.addObject("hasItem" , 1) ;
				mv.addObject("categoriesList" , categoriesList) ;
				mv.addObject("itemsList" , itemsList) ;				
			}else {
				mv.addObject("hasItem" , 1) ;
				mv.addObject("categoriesList" , categoriesList) ;
				mv.addObject("itemsList" , itemsList) ;		
			}
			mv.setViewName(sliderFormPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	@PostMapping("aao/save-slider")
	public ModelAndView saveSlider(HttpServletRequest request , @RequestParam Map<Object , Object> params , @RequestParam("uploadPic") MultipartFile uploadPic) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		int mode = Integer.parseInt(params.get("mode").toString()) ;
		
		if(session.getAttribute(DomainValues.SessionKeys.USER) != null) {		
			String firstLine = params.get("firstLine").toString() ;
			String secondLine = params.get("secondLine").toString() ;
			String thirdLine = params.get("thirdLine").toString() ;
						
			if(uploadPic != null && !uploadPic.isEmpty()) {
				boolean validatePictureSize = CustomValidation.validatePictureSize(uploadPic) ;
				if(!validatePictureSize) {
					mv.addObject("PictureSizeError" , 1) ;
				}else{
					String fileName = uploadPic.getOriginalFilename() ;	
					String prefixName = fileName.substring(0 , fileName.lastIndexOf(".")) ;
					String extension = fileName.substring(fileName.lastIndexOf(".") + 1 , fileName.length()) ;
					if(!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("jpeg") && !extension.equalsIgnoreCase("png")) {
						mv.addObject("PictureExtensionError" , 1) ;
					}
				}
			}
			mv.addObject("mode" , mode) ;
			mv.setViewName(sliderFormPage) ;
		}else {
			mv.setViewName(loginPage) ;
		}
		return mv ;
	}
	
	
	
	
	
	private void init(ModelAndView mv) {
		mv.addObject("countryList" , generalService.countryList()) ;
	}


	public List<LoginUserDTO> getList() {
		return list;
	}
	public void setList(List<LoginUserDTO> list) {
		this.list = list;
	}

	public List<FaqDTO> getFaqList() {
		return faqList;
	}
	public void setFaqList(List<FaqDTO> faqList) {
		this.faqList = faqList;
	}
	
	public List<ContactFormDTO> getContactFormList() {
		return contactFormList;
	}
	public void setContactFormList(List<ContactFormDTO> contactFormList) {
		this.contactFormList = contactFormList;
	}

	public List<CategoryDTO> getCategoriesList() {
		return categoriesList;
	}
	public void setCategoriesList(List<CategoryDTO> categoriesList) {
		this.categoriesList = categoriesList;
	}




	public List<ItemsDTO> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<ItemsDTO> itemsList) {
		this.itemsList = itemsList;
	}




	public SliderDTO getDto() {
		return dto;
	}
	public void setDto(SliderDTO dto) {
		this.dto = dto;
	}

}
