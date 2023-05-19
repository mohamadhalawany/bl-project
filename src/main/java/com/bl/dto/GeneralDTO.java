package com.bl.dto;

public class GeneralDTO {
	
	private Long key ;
	private Integer id ;
	private String valueEn , valueAr , isoName ;
	
	
	
	public Long getKey() {
		return key;
	}
	public void setKey(Long key) {
		this.key = key;
	}
	public String getValueEn() {
		return valueEn;
	}
	public void setValueEn(String valueEn) {
		this.valueEn = valueEn;
	}
	public String getValueAr() {
		return valueAr;
	}
	public void setValueAr(String valueAr) {
		this.valueAr = valueAr;
	}
	public String getIsoName() {
		return isoName;
	}
	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
