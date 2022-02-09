package com.bl.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.bl.dto.GeneralDTO;
import com.bl.dto.OrderRequestDTO;
import com.bl.service.GeneralService;
import com.bl.service.OrderRequestService;

public class BaseController {

	@Autowired
	private OrderRequestService orderRequestService;
	
	@Autowired
	private GeneralService generalService ;

	protected List<OrderRequestDTO> customerOrders(Long customerId, Integer orderStatus, HttpSession session) {
		List<OrderRequestDTO> list = orderRequestService.customerOrdersCart(customerId , orderStatus) ;
		
		Double totalPrice = 0.0;
		Integer currencyId = 0;
		Long orderNumber = 0L ;
		
		String currencyName = "";
		String currencyNameAr = "";
		String currencyCode = "";

		if (list != null) {
			for (OrderRequestDTO order : list) {
				totalPrice = order.getTotalPrice() ;
				currencyId = order.getCurrencyId();
				orderNumber = order.getOrderNumber() ;
			}

			if (currencyId != 0) {
				GeneralDTO generalDTO = generalService.currencyById(currencyId);
				currencyName = generalDTO.getValueEn();
				currencyNameAr = generalDTO.getValueAr();
				currencyCode = generalDTO.getIsoName();
				session.setAttribute("currencyName", currencyName);
				session.setAttribute("currencyNameAr", currencyNameAr);
				session.setAttribute("currencyCode", currencyCode);
			}
			session.setAttribute("orderNumber", orderNumber);
			session.setAttribute("totalPrice", totalPrice);
		} else {
			session.setAttribute("orderRequestListSize", 0);
		}
		return list;
	}

}
