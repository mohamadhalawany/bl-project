package com.bl.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cities_districts database table.
 * 
 */
@Entity
@Table(name="cities_districts")
@NamedQuery(name="CitiesDistrictEntity.findAll", query="SELECT c FROM CitiesDistrictEntity c")
public class CitiesDistrictEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="governorate_id")
	private int governorateId;

	@Column(name="name_ar")
	private String nameAr;

	@Column(name="name_en")
	private String nameEn;

	public CitiesDistrictEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGovernorateId() {
		return this.governorateId;
	}

	public void setGovernorateId(int governorateId) {
		this.governorateId = governorateId;
	}

	public String getNameAr() {
		return this.nameAr;
	}

	public void setNameAr(String nameAr) {
		this.nameAr = nameAr;
	}

	public String getNameEn() {
		return this.nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

}