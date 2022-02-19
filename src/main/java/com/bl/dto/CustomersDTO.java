package com.bl.dto;

import java.util.Date;


public class CustomersDTO {
	
	private Long id;
	private String address;
	private Integer isBlocked ;
	private Long cityDistrictId;
	private Integer createdBy;
	private Date createdDate;
	private Integer customerType;
	private String email;
	private String fullName;
	private Integer isTax;
	private String mobile;
	private String password;
	private String phone ;
	private Integer updatedBy;
	private Date updatedDate;
	
	private Long countryId ;
	private Long governorateId ;
	
	private String countryNameAr ; 
	private String countryNameEn ;
	private String iso ;
	private String governorateNameAr ;
	private String governorateNameEn ;
	private String cityDistrictNameAr ;
	private String cityDistrictNameEn ;
	private String customerTypeValue ;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getCityDistrictId() {
		return cityDistrictId;
	}
	public void setCityDistrictId(Long cityDistrictId) {
		this.cityDistrictId = cityDistrictId;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Integer getIsTax() {
		return isTax;
	}
	public void setIsTax(Integer isTax) {
		this.isTax = isTax;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public Long getGovernorateId() {
		return governorateId;
	}
	public void setGovernorateId(Long governorateId) {
		this.governorateId = governorateId;
	}
	public String getCountryNameAr() {
		return countryNameAr;
	}
	public void setCountryNameAr(String countryNameAr) {
		this.countryNameAr = countryNameAr;
	}
	public String getCountryNameEn() {
		return countryNameEn;
	}
	public void setCountryNameEn(String countryNameEn) {
		this.countryNameEn = countryNameEn;
	}
	public String getIso() {
		return iso;
	}
	public void setIso(String iso) {
		this.iso = iso;
	}
	public String getGovernorateNameAr() {
		return governorateNameAr;
	}
	public void setGovernorateNameAr(String governorateNameAr) {
		this.governorateNameAr = governorateNameAr;
	}
	public String getGovernorateNameEn() {
		return governorateNameEn;
	}
	public void setGovernorateNameEn(String governorateNameEn) {
		this.governorateNameEn = governorateNameEn;
	}
	public String getCityDistrictNameAr() {
		return cityDistrictNameAr;
	}
	public void setCityDistrictNameAr(String cityDistrictNameAr) {
		this.cityDistrictNameAr = cityDistrictNameAr;
	}
	public String getCityDistrictNameEn() {
		return cityDistrictNameEn;
	}
	public void setCityDistrictNameEn(String cityDistrictNameEn) {
		this.cityDistrictNameEn = cityDistrictNameEn;
	}
	public Integer getIsBlocked() {
		return isBlocked;
	}
	public void setIsBlocked(Integer isBlocked) {
		this.isBlocked = isBlocked;
	}
	public String getCustomerTypeValue() {
		return customerTypeValue;
	}
	public void setCustomerTypeValue(String customerTypeValue) {
		this.customerTypeValue = customerTypeValue;
	}

}
