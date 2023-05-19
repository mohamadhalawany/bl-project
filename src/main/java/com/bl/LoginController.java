package com.bl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView doLogin(@RequestParam("userName") String userName , @RequestParam("password") String password , HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(userName == "moh") {
			System.err.println(userName + " ==================== ");
			session.setAttribute("user" , 100);
			mv.setViewName("home") ;
		}else if(userName.equals("moh")){
			System.err.println(userName + " eeeeeeeeqqqqqqqqquuuuuuuuuuuaaaaaaaallllllllllllllsssssssssssss");
			session.setAttribute("user" , null);
			mv.setViewName("login") ;
		}
		
//	UserData user = service.doLoginService(userName, password) == null ? null : service.doLoginService(userName, password) ;

//	if(user != null) {
//	    EmployeesDTO emplDTO = emplService.findByUserId(user.getUserId()) ;
//	    user.setFullName(emplDTO.getEmployeeName()) ;
//	    user.setEmployeeId(emplDTO.getEmployeeId());
//	    
//	    request.getSession().setAttribute("user", user);
//	    
//	    mv.addObject("userName" , user.getFullName()) ;
//	    mv.setViewName("home") ;	    
//	}
//	else {
//	    mv.setViewName("login?error=1");
//	}
		return mv;
	}

	@RequestMapping(value = "aao/login", method = RequestMethod.GET)
	public ModelAndView goLogin(@RequestParam("userName") String userName , @RequestParam("password") String password , HttpServletRequest request) {
		ModelAndView mv = new ModelAndView() ;
		HttpSession session = request.getSession() ;
		if(userName == "moh") {
			System.err.println(userName + " ==================== ");
			session.setAttribute("user" , 100);
			mv.setViewName("home") ;
		}else if(userName.equals("moh")){
			System.err.println(userName + " eeeeeeeeqqqqqqqqquuuuuuuuuuuaaaaaaaallllllllllllllsssssssssssss");
			session.setAttribute("user" , null);
			mv.setViewName("login") ;
		}
		return mv;
	}

	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public ModelAndView doLogout(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("login");
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.invalidate();
		return mv;
	}

//	public UserData getUd() {
//		return ud;
//	}
//
//	public void setUd(UserData ud) {
//		this.ud = ud;
//	}

}
