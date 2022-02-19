package com.bl.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the governorates database table.
 * 
 */
@Entity
@Table(name="governorates")
@NamedQuery(name="GovernorateEntity.findAll", query="SELECT g FROM GovernorateEntity g")
public class GovernorateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="COUNTRY_ID")
	private int countryId;

	@Column(name="GOVERNORATE_NAME_AR")
	private String governorateNameAr;

	@Column(name="GOVERNORATE_NAME_EN")
	private String governorateNameEn;

	public GovernorateEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCountryId() {
		return this.countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getGovernorateNameAr() {
		return this.governorateNameAr;
	}

	public void setGovernorateNameAr(String governorateNameAr) {
		this.governorateNameAr = governorateNameAr;
	}

	public String getGovernorateNameEn() {
		return this.governorateNameEn;
	}

	public void setGovernorateNameEn(String governorateNameEn) {
		this.governorateNameEn = governorateNameEn;
	}

}