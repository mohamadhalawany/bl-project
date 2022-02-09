package com.bl.dto;

import java.util.Date;



public class ItemSpecificationsDTO {

	private Long id;
	private Integer colorId;
	private Integer createdBy;
	private Date createdDate;
	private String depth;
	private String height;
	private String included;
	private Long itemId;
	private String manufacturingTime;
	private String material;
	private Integer updatedBy;
	private Date updatedDate;
	private String warranty;
	private String width;
	
	private String colorNameEn , colorNameAr ;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getColorId() {
		return colorId;
	}
	public void setColorId(Integer colorId) {
		this.colorId = colorId;
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
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getIncluded() {
		return included;
	}
	public void setIncluded(String included) {
		this.included = included;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getManufacturingTime() {
		return manufacturingTime;
	}
	public void setManufacturingTime(String manufacturingTime) {
		this.manufacturingTime = manufacturingTime;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
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
	public String getWarranty() {
		return warranty;
	}
	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getColorNameEn() {
		return colorNameEn;
	}
	public void setColorNameEn(String colorNameEn) {
		this.colorNameEn = colorNameEn;
	}
	public String getColorNameAr() {
		return colorNameAr;
	}
	public void setColorNameAr(String colorNameAr) {
		this.colorNameAr = colorNameAr;
	}
}
