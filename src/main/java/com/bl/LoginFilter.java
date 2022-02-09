package com.bl;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet Filter for Logged in user
 */
//@WebFilter("/aao/*")
public class LoginFilter {//extends HttpFilter{//implements Filter {
	
	private static final long serialVersionUID = 1L;
	
//    @Override
//    public void doFilter(HttpServletRequest request , HttpServletResponse response, FilterChain chain)throws IOException, ServletException {
//
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpSession session = req.getSession() ;
//		if (session.getAttribute("user") == null) {
//		    req.getRequestDispatcher("goLogin").forward(request, response) ;
//		}
//		chain.doFilter(request, response);
//	 }
}
