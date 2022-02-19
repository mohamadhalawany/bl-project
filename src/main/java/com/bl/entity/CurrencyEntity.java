package com.bl.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the currency database table.
 * 
 */
@Entity
@Table(name="currency")
@NamedQuery(name="CurrencyEntity.findAll", query="SELECT c FROM CurrencyEntity c")
public class CurrencyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="CURRENCY_NAME")
	private String currencyName;

	@Column(name="CURRENCY_NAME_AR")
	private String currencyNameAr;

	@Column(name="INTERNATIONAL_CODE")
	private String internationalCode;

	public CurrencyEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCurrencyName() {
		return this.currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCurrencyNameAr() {
		return this.currencyNameAr;
	}

	public void setCurrencyNameAr(String currencyNameAr) {
		this.currencyNameAr = currencyNameAr;
	}

	public String getInternationalCode() {
		return this.internationalCode;
	}

	public void setInternationalCode(String internationalCode) {
		this.internationalCode = internationalCode;
	}

}