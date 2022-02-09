package com.bl.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the colors database table.
 * 
 */
@Entity
@Table(name="colors")
@NamedQuery(name="ColorsEntity.findAll", query="SELECT c FROM ColorsEntity c")
public class ColorsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="COLOR_NAME_AR")
	private String colorNameAr;

	@Column(name="COLOR_NAME_EN")
	private String colorNameEn;

	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getColorNameAr() {
		return this.colorNameAr;
	}

	public void setColorNameAr(String colorNameAr) {
		this.colorNameAr = colorNameAr;
	}

	public String getColorNameEn() {
		return this.colorNameEn;
	}

	public void setColorNameEn(String colorNameEn) {
		this.colorNameEn = colorNameEn;
	}

}