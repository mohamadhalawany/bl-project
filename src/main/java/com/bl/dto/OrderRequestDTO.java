package com.bl.dto;

public class OrderRequestDTO {
	
	private Long itemId , customerId , orderId , orderItemId , orderNumber , categoryId ;
	private String itemName , itemLogo , itemCode , categoryName , orderDate , orderDateTo , orderNumberLong , currencyName , currencyNameAr , internationalCode , 
							 customerFullName , itemNameCode ;
	private Double itemPrice , totalPrice ;
	private Integer currencyId , quantity , paymentMethod , orderStatusId , customerType , invalidOrderNumberLong , orderYear , orderNo , orderItemIdShort ;
	
	
	
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemLogo() {
		return itemLogo;
	}
	public void setItemLogo(String itemLogo) {
		this.itemLogo = itemLogo;
	}
	public Double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Long getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Integer getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderNumberLong() {
		return orderNumberLong;
	}
	public void setOrderNumberLong(String orderNumberLong) {
		this.orderNumberLong = orderNumberLong;
	}
	public Integer getOrderStatusId() {
		return orderStatusId;
	}
	public void setOrderStatusId(Integer orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getCurrencyNameAr() {
		return currencyNameAr;
	}
	public void setCurrencyNameAr(String currencyNameAr) {
		this.currencyNameAr = currencyNameAr;
	}
	public String getInternationalCode() {
		return internationalCode;
	}
	public void setInternationalCode(String internationalCode) {
		this.internationalCode = internationalCode;
	}
	public Integer getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	public String getCustomerFullName() {
		return customerFullName;
	}
	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}
	public String getOrderDateTo() {
		return orderDateTo;
	}
	public void setOrderDateTo(String orderDateTo) {
		this.orderDateTo = orderDateTo;
	}
	public String getItemNameCode() {
		return itemNameCode;
	}
	public void setItemNameCode(String itemNameCode) {
		this.itemNameCode = itemNameCode;
	}
	public Integer getInvalidOrderNumberLong() {
		return invalidOrderNumberLong;
	}
	public void setInvalidOrderNumberLong(Integer invalidOrderNumberLong) {
		this.invalidOrderNumberLong = invalidOrderNumberLong;
	}
	public Integer getOrderYear() {
		return orderYear;
	}
	public void setOrderYear(Integer orderYear) {
		this.orderYear = orderYear;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getOrderItemIdShort() {
		return orderItemIdShort;
	}
	public void setOrderItemIdShort(Integer orderItemIdShort) {
		this.orderItemIdShort = orderItemIdShort;
	}
}
