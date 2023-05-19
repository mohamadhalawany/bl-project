package com.bl.entity.cms;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the contact_form database table.
 * 
 */
@Entity
@Table(name="contact_form")
@NamedQuery(name="ContactFormEntity.findAll", query="SELECT c FROM ContactFormEntity c")
public class ContactFormEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="CUSTOMER_MAIL")
	private String customerMail;

	@Column(name="CUSTOMER_NAME")
	private String customerName;

	@Column(name="MESSAGE")
	private String message;

	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerMail() {
		return this.customerMail;
	}

	public void setCustomerMail(String customerMail) {
		this.customerMail = customerMail;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}