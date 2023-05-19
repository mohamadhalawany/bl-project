package com.bl.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bl.DomainValues;
import com.bl.dao.CustomDAO;
import com.bl.dao.EntityManagerHelper;
import com.bl.dao.OrderRequestDAO;
import com.bl.dao.OrderRequestMapper;
import com.bl.dto.GeneralDTO;
import com.bl.dto.OrderDTO;
import com.bl.dto.OrderRequestDTO;
import com.bl.entity.OrderEntity;
import com.bl.entity.OrderStatusEntity;
import com.bl.entity.cms.SettingEntity;

@Component
public class OrderRequestDAOImpl implements OrderRequestDAO {
	
	private JdbcTemplate temp ;
	
	@PersistenceContext
    private EntityManager em ;
	
	public OrderRequestDAOImpl() {		
	}
	
	public OrderRequestDAOImpl(DataSource ds) {
		temp = new JdbcTemplate(ds) ;
	}



	@Override
	public List<OrderRequestDTO> customerOrdersCart(Long customerId , Integer orderStatus , Long orderId) {
		
		String sql = "SELECT it.item_name , ord.TOTAL_PRICE , it.item_logo , ord.CURRENCY_ID , oi.QUANTITY , oi.id , oi.item_price , ord.id , ord.ORDER_NUMBER "
				+ " FROM order_items oi , orders ord , customers cu , items it "
				+ " WHERE oi.ORDER_ID = ord.ID "
				+ " AND ord.CUSTOMER_ID = cu.ID "
				+ " AND oi.ITEM_ID = it.id "
				+ " AND ord.ORDER_STATUS_ID = ? " 
				+ " AND ord.CUSTOMER_ID = ? "
				+ " AND oi.ORDER_ID = ?" ;
		
		return temp.query(sql , new Object[] {customerId , orderStatus , orderId} , new OrderRequestMapper()) ;
	}

	
	@Override
	public List<OrderRequestDTO> customerOrdersList(Long customerId, Integer orderStatusId , Long orderId) {
		String sql = "SELECT it.itemName , ord.totalPrice , it.itemLogo , ord.currencyId , oi.quantity ,  oi.itemPrice , oi.id , ord.id , ord.orderNumber "
				+ " FROM OrderItemEntity oi , OrderEntity ord , CustomersEntity cu , ItemEntity it "
				+ " WHERE oi.orderId = ord.id "
				+ " AND ord.customerId = cu.id "
				+ " AND oi.itemId = it.id "
				+ " AND ord.orderStatusId = ?1 " 
				+ " AND ord.customerId = ?2 "
				+ " AND oi.orderId = ?3 " ;
		
		EntityManager em = EntityManagerHelper.open() ;
		Query query = em.createQuery(sql) ;
		query.setParameter(1 , orderStatusId) ;
		query.setParameter(2 , customerId) ;
		query.setParameter(3 , orderId) ;
		
		List<Object[]> objs = query.getResultList();
		List<OrderRequestDTO> list = null ;
		if(objs != null && !objs.isEmpty()) {
			list = new ArrayList<OrderRequestDTO>() ;
			for (int i = 0; i < objs.size(); i++) {
				OrderRequestDTO dto = new OrderRequestDTO() ;
				dto.setItemName((String) objs.get(i)[0]) ;
				dto.setTotalPrice((Double) objs.get(i)[1]) ;
				dto.setItemLogo((String) objs.get(i)[2]) ;
				dto.setQuantity((Integer) objs.get(i)[4]) ;
				dto.setItemPrice((Double) objs.get(i)[5]) ;
				dto.setOrderItemId((Long) objs.get(i)[6]);
				dto.setOrderId((Long) objs.get(i)[7]) ;
				dto.setOrderNumber((Long) objs.get(i)[8]) ;
				
				Integer currencyId = (Integer) objs.get(i)[3] ;
				if(currencyId != null) {
					CustomDAO custom = new CustomDAOImpl() ;
					GeneralDTO currency = custom.currencyById(currencyId) ;
					if(currency != null) {
						dto.setCurrencyName(currency.getValueEn()) ;
						dto.setCurrencyNameAr(currency.getValueAr()) ;
						dto.setInternationalCode(currency.getIsoName()) ;
					}
				}
				list.add(dto) ;
			}
		}
		return list ;
	}

	@Override
	public GeneralDTO generateOrderNumber() {
		GeneralDTO dto = null ;
		String sql = "SELECT max(ORDER_NUMBER) || '' "
				+ " FROM order  "
				+ " WHERE year(sysdate()) = ?1 " ;
		
		EntityManager em = EntityManagerHelper.open() ;
		Query query = em.createNativeQuery(sql) ;
		query.setParameter(1 , new Date().getYear() + 1900) ;
		List<Object[]> objs = query.getResultList();
		if(objs != null && !objs.isEmpty()) {			
			for(int i = 0 ; i < objs.size() ; i++) {
				dto = new GeneralDTO() ;
				if(objs.get(i) != null) {
					Integer ob = (Integer) objs.get(0)[0] ;
					Long key = Long.valueOf(ob) ;
					dto.setKey(key);
				}else {
					dto.setKey(1L) ;
				}
			}
		}
		return dto ;
	}



	@Override
	public List<OrderRequestDTO> findAllOrdersOrdered() {
		String sql = "SELECT o.id , o.currencyId , o.customerId , o.paymentMethod , cast(o.orderDate as string) , o.orderNumber ,  o.orderStatusId , o.totalPrice , "
				+ " cu.currencyName , cu.currencyNameAr , cu.internationalCode , "
				+ " cm.fullName , cm.customerType , "
				+ " cast(extract(year from o.orderDate) as string) "
				+ " FROM OrderEntity o , CurrencyEntity cu , CustomersEntity cm"
				+ " WHERE o.currencyId = cu.id "
				+ " AND o.customerId = cm.id "
				+ " AND o.orderStatusId = 2" ;
		
		Query query = em.createQuery(sql);
		List<Object[]> objs = query.getResultList();
		List<OrderRequestDTO> list = null ;
		
		if(objs != null && !objs.isEmpty()) {
			list = new ArrayList<OrderRequestDTO>() ;
			for (int i = 0; i < objs.size(); i++) {
				OrderRequestDTO dto = new OrderRequestDTO() ;
				dto.setOrderId((Long) objs.get(i)[0]) ;
				dto.setCurrencyId((Integer) objs.get(i)[1]) ;
				dto.setCustomerId((Long) objs.get(i)[2]) ;
				dto.setPaymentMethod((Integer) objs.get(i)[3]);
				dto.setOrderDate((String) objs.get(i)[4]) ;
				dto.setOrderNumberLong((Long) objs.get(i)[5] + "/" + (String) objs.get(i)[13]) ;
				dto.setOrderStatusId((Integer)objs.get(i)[6]) ;
				dto.setTotalPrice((Double) objs.get(i)[7]) ;
				dto.setCurrencyName((String) objs.get(i)[8]) ;
				dto.setCurrencyNameAr((String) objs.get(i)[9]) ;
				dto.setInternationalCode((String) objs.get(i)[10]) ;
				dto.setCustomerFullName((String) objs.get(i)[11]) ;
				dto.setCustomerType((Integer) objs.get(i)[12]) ;
				
				list.add(dto) ;
			}
		}
		return list ;
	}

	
	


	@Override
	public List<OrderRequestDTO> search(OrderRequestDTO dto) {
		String condition = " AND 1 = 0 " ;
		String conditionCustomerName = "" ;
		String conditionCategoryName = "" ;
		String conditionCustomerType = "" ;
		String conditionItemNameCode = "" ;
		String conditionOrderNumberYear = "" ;
		String conditionOrderStatusId = "" ;
		String conditionOrderDate = "" ;
		
		String sql = "SELECT DISTINCT (o.id) , o.currencyId , o.customerId , o.paymentMethod , cast(o.orderDate as string) , o.orderNumber ,  o.orderStatusId , o.totalPrice , "
				+ " oi.id , oi.itemId , oi.itemPrice , oi.quantity , " // 8
				+ " i.itemName , i.itemCode , i.categoryId , "  // 12
				+ " c.categoryName , "
				+ " cu.currencyName , cu.currencyNameAr , cu.internationalCode , "
				+ " cm.fullName , cm.customerType , "
				+ " cast(extract(year from o.orderDate) as string) , "
				+ " o.orderDate "
				+ " FROM OrderEntity o , OrderItemEntity oi , ItemEntity i , CategoryEntity c , CurrencyEntity cu , CustomersEntity cm"
				+ " WHERE o.id = oi.orderId "
				+ " AND oi.itemId = i.id "
				+ " AND i.categoryId = c.id "
				+ " AND i.currencyId = cu.id "
				+ " AND o.customerId = cm.id " ;
		
		if(dto.getCustomerFullName() != null) {
			conditionCustomerName = " AND cm.fullName LIKE :customerName " ;
			condition = "" ;
		}
		if(dto.getCategoryName() != null) {
			conditionCategoryName = " AND c.categoryName LIKE :categoryName " ;
			condition = "" ;
		}
		if(dto.getCustomerType() != null) {
			conditionCustomerType = " AND cm.customerType = :customerType " ;
			condition = "" ;
		}
		if(dto.getItemNameCode() != null) {
			conditionItemNameCode = " AND i.itemName LIKE :itemNameCode AND i.itemCode LIKE :itemNameCode " ;
			condition = "" ;
		}
		if(dto.getOrderNumber() != null && dto.getOrderYear() != null) {
			conditionOrderNumberYear = " AND o.orderNumber = :orderNumber AND year(o.orderDate) = :orderYear" ;
			condition = "" ;
		}
		if(dto.getOrderStatusId() != null) {
			conditionOrderStatusId = " AND o.orderStatusId = :orderStatusId " ;
			condition = "" ;
		}
		if(dto.getOrderDate() != null && dto.getOrderDateTo() != null) {
			conditionOrderDate = " AND cast(o.orderDate as string) BETWEEN (:orderDateFrom) AND (:orderDateTo) " ;
			condition = "" ;
		}
		
		Query query = em.createQuery(sql + condition + conditionCustomerName + conditionCategoryName + conditionCustomerType + conditionItemNameCode 
				+ conditionOrderNumberYear + conditionOrderStatusId + conditionOrderDate) ;
		
		if(dto.getCustomerFullName() != null) {
			query.setParameter("customerName" , "%" + dto.getCustomerFullName() + "%") ;
		}
		if(dto.getCategoryName() != null) {
			query.setParameter("categoryName" , "%" + dto.getCategoryName() + "%") ;
		}
		if(dto.getCustomerType() != null) {
			query.setParameter("customerType" , dto.getCustomerType()) ;
		}
		if(dto.getItemNameCode() != null) {
			query.setParameter("itemNameCode" , "%" + dto.getItemNameCode() + "%") ;
		}
		if(dto.getOrderNumber() != null && dto.getOrderYear() != null) {
			query.setParameter("orderNumber" , dto.getOrderNumber()) ;
			query.setParameter("orderYear" , dto.getOrderYear()) ;
		}
		if(dto.getOrderStatusId() != null) {
			query.setParameter("orderStatusId" , dto.getOrderStatusId()) ;
		}
		if(dto.getOrderDate() != null && dto.getOrderDateTo() != null) {
			query.setParameter("orderDateFrom" , dto.getOrderDate()) ;
			query.setParameter("orderDateTo" , dto.getOrderDateTo()) ;
		}
		
		List<Object[]> objs = query.getResultList();
		List<OrderRequestDTO> list = null ;
		
		if(objs != null && !objs.isEmpty()) {
			list = new ArrayList<OrderRequestDTO>() ;
			for (int i = 0; i < objs.size(); i++) {
				OrderRequestDTO order = new OrderRequestDTO() ;
				order.setOrderId((Long) objs.get(i)[0]) ;
				order.setCurrencyId((Integer) objs.get(i)[1]) ;
				order.setCustomerId((Long) objs.get(i)[2]) ;
				order.setPaymentMethod((Integer) objs.get(i)[3]);
				
				String orderDate = (String) objs.get(i)[4] ;
				order.setOrderDate(orderDate.substring(0 , 10)) ;
				order.setOrderNumberLong((Long) objs.get(i)[5] + "/" + (String) objs.get(i)[21]) ;
				order.setOrderStatusId((Integer)objs.get(i)[6]) ;
				order.setTotalPrice((Double) objs.get(i)[7]) ;
				order.setCurrencyName((String) objs.get(i)[16]) ;
				order.setCurrencyNameAr((String) objs.get(i)[17]) ;
				order.setInternationalCode((String) objs.get(i)[18]) ;
				order.setCustomerFullName((String) objs.get(i)[19]) ;
				order.setCustomerType((Integer) objs.get(i)[20]) ;
				
				order.setItemId((Long) objs.get(i)[9]) ;
				order.setItemPrice((Double) objs.get(i)[10]) ;
				order.setQuantity((Integer) objs.get(i)[11]) ;
				order.setItemName((String) objs.get(i)[12]) ;
				order.setItemCode((String) objs.get(i)[13]) ;
				order.setCategoryName((String) objs.get(i)[15]) ;
				
				list.add(order) ;
			}
		}
		return list ;
	}
	
	
	
	
	@Override
	public OrderRequestDTO findOrderRequestById(Long id) {
		String sql = "SELECT o.id , o.currencyId , o.customerId , o.paymentMethod , cast(o.orderDate as string) , o.orderNumber ,  o.orderStatusId , o.totalPrice , "
				+ " cu.currencyName , cu.currencyNameAr , cu.internationalCode , "
				+ " cm.fullName , cm.customerType , "
				+ " cast(extract(year from o.orderDate) as string) "
				+ " FROM OrderEntity o , CurrencyEntity cu , CustomersEntity cm"
				+ " WHERE o.currencyId = cu.id "
				+ " AND o.customerId = cm.id "
				+ " AND o.id = ?1" ;
		
		Query query = em.createQuery(sql);
		query.setParameter(1 , id) ;
		OrderRequestDTO dto = null ;
		List<Object[]> objs = query.getResultList();
		
		if(objs != null && !objs.isEmpty()) {
			dto = new OrderRequestDTO() ;
			for (int i = 0; i < objs.size(); i++) {				
				dto.setOrderId((Long) objs.get(i)[0]) ;
				dto.setCurrencyId((Integer) objs.get(i)[1]) ;
				dto.setCustomerId((Long) objs.get(i)[2]) ;
				dto.setPaymentMethod((Integer) objs.get(i)[3]);
				dto.setOrderDate((String) objs.get(i)[4]) ;
				dto.setOrderNumberLong((Long) objs.get(i)[5] + "/" + (String) objs.get(i)[13]) ;
				dto.setOrderStatusId((Integer)objs.get(i)[6]) ;
				dto.setTotalPrice((Double) objs.get(i)[7]) ;
				dto.setCurrencyName((String) objs.get(i)[8]) ;
				dto.setCurrencyNameAr((String) objs.get(i)[9]) ;
				dto.setInternationalCode((String) objs.get(i)[10]) ;
				dto.setCustomerFullName((String) objs.get(i)[11]) ;
				dto.setCustomerType((Integer) objs.get(i)[12]) ;				
			}
		}
		return dto ;
	}



	@Override
	public Long maxOrderIdByCustomerIdAndOrderStatus(Long customerId, Integer orderStatus) {
		EntityManager em = EntityManagerHelper.open() ;
		Long max = null ;
		String sql = "SELECT MAX(oi.orderId) "
				+ " FROM OrderItemEntity oi , OrderEntity ord "
				+ " WHERE ord.id = oi.orderId "
				+ " and  ord.customerId = ?1 "
				+ " and ord.orderStatusId = ?2 " ;
		Query query = em.createQuery(sql) ;
		query.setParameter(1 , customerId) ;
		query.setParameter(2 , orderStatus) ;
		Object obj = query.getSingleResult() ;
		if(obj != null) {
			max = Long.parseLong(obj.toString()) ;
		}
 		return max ;
	}

	@Override
	public OrderDTO findById(Long id) {
		OrderDTO dto = null ;
		EntityManager em = EntityManagerHelper.open() ;
		Query query = em.createNamedQuery("OrderEntity.findById") ;
		query.setParameter("id" , id) ;
		List<OrderEntity> objs = query.getResultList() ;
		if(objs != null && !objs.isEmpty()) {
			for(int i = 0 ; i < objs.size() ; i++) {
				dto = new OrderDTO() ;
				dto.setCurrencyId(objs.get(i).getCurrencyId()) ;
				dto.setCustomerId(objs.get(i).getCustomerId()) ;
				dto.setId(objs.get(i).getId()) ;
				dto.setOrderDate(objs.get(i).getOrderDate());
				dto.setOrderNumber(objs.get(i).getOrderNumber()) ;
				dto.setOrderStatusId(objs.get(i).getOrderStatusId()) ;
				dto.setPaymentMethod(objs.get(i).getPaymentMethod()) ;
				dto.setTotalPrice(objs.get(i).getTotalPrice()) ;
			}
		}
		return dto ;
	}

	@Override
	@Transactional
	public void saveOrder(OrderDTO dto) {
		EntityManager em = EntityManagerHelper.open() ;
		EntityTransaction transaction = em.getTransaction() ;
		transaction.begin() ; 
		OrderEntity entity = new OrderEntity() ;
		entity.setId(dto.getId()) ;
		entity.setOrderStatusId(11) ;
		entity.setUpdatedDate(new Date()) ;
		entity.setCurrencyId(dto.getCurrencyId()) ;
		entity.setCustomerId(dto.getCustomerId()) ;
		entity.setOrderDate(dto.getOrderDate()) ;
		entity.setPaymentMethod(dto.getPaymentMethod()) ;
		entity.setTotalPrice(dto.getTotalPrice()) ;
		
		OrderStatusEntity orderStatusEntity = new OrderStatusEntity() ;
		orderStatusEntity.setOrderId(dto.getId()) ;
		orderStatusEntity.setStatusDate(new Date()) ;
		orderStatusEntity.setStatusId(DomainValues.OrderStatus.ORDER_EXPIRE) ;
		em.merge(entity) ;
		transaction.commit() ;
	}

	@Override
	public Integer orderExpireDays() {
		Integer days = 0 ;
		String sql = "SELECT s FROM SettingEntity s where s.id = 4" ;
		EntityManager em = EntityManagerHelper.open() ;
		Query query = em.createQuery(sql) ;
		List<SettingEntity> objs = query.getResultList() ;
		if(objs != null && !objs.isEmpty()) {
			for(SettingEntity entity : objs) {
				days = entity.getExpireDays() ;
			}
		}
		return days ;
	}	
		
}
