package com.bl.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the countries database table.
 * 
 */
@Entity
@Table(name="countries")
@NamedQuery(name="CountryEntity.findAll", query="SELECT c FROM CountryEntity c")
public class CountryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="COUNTRY_NAME")
	private String countryName;

	@Column(name="COUNTRY_NAME_AR")
	private String countryNameAr;

	@Column(name="ISO")
	private String iso;

	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryNameAr() {
		return this.countryNameAr;
	}

	public void setCountryNameAr(String countryNameAr) {
		this.countryNameAr = countryNameAr;
	}

	public String getIso() {
		return this.iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

}