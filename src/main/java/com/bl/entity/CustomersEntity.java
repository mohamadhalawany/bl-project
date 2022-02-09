package com.bl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the customers database table.
 * 
 */
@Entity
@Table(name="customers")
@NamedQuery(name="CustomersEntity.findAll", query="SELECT c FROM CustomersEntity c")
public class CustomersEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="ADDRESS")
	private String address;

	@Column(name="CITY_DISTRICT_ID")
	private Long cityDistrictId;

	@Column(name="CREATED_BY")
	private Integer createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="CUSTOMER_TYPE")
	private Integer customerType;

	@Column(name="EMAIL")
	private String email;

	@Column(name="FULL_NAME")
	private String fullName;

	@Column(name="IS_TAX")
	private Integer isTax;

	@Column(name="MOBILE")
	private String mobile;

	@Column(name="PASSWORD")
	private String password;

	@Column(name="PHONE")
	private String phone;

	@Column(name="UPDATED_BY")
	private Integer updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;


	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCityDistrictId() {
		return this.cityDistrictId;
	}

	public void setCityDistrictId(Long cityDistrictId) {
		this.cityDistrictId = cityDistrictId;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getIsTax() {
		return this.isTax;
	}

	public void setIsTax(Integer isTax) {
		this.isTax = isTax;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}