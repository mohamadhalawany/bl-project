package com.bl.dto.cms;

import java.util.Date;

public class SliderDTO {

	private Integer id;
	private String description;
	private String firstLine;
	private Long itemId;
	private Double itemPrice;
	private String secondLine;
	private Integer showMoreDetails;
	private Integer showPrice;
	private String thirdLine;
	private String uploadPic;
	private Integer createdBy;
	private Date createdDate;
	private Integer isActive;
	private Integer updatedBy;
	private Date updatedDate;
	
	private Long categoryId , currencyId ;
	private String currencyNameAr , currencyNameEn ;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFirstLine() {
		return firstLine;
	}
	public void setFirstLine(String firstLine) {
		this.firstLine = firstLine;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getSecondLine() {
		return secondLine;
	}
	public void setSecondLine(String secondLine) {
		this.secondLine = secondLine;
	}
	public Integer getShowMoreDetails() {
		return showMoreDetails;
	}
	public void setShowMoreDetails(Integer showMoreDetails) {
		this.showMoreDetails = showMoreDetails;
	}
	public Integer getShowPrice() {
		return showPrice;
	}
	public void setShowPrice(Integer showPrice) {
		this.showPrice = showPrice;
	}
	public String getThirdLine() {
		return thirdLine;
	}
	public void setThirdLine(String thirdLine) {
		this.thirdLine = thirdLine;
	}
	public String getUploadPic() {
		return uploadPic;
	}
	public void setUploadPic(String uploadPic) {
		this.uploadPic = uploadPic;
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
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
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
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrencyNameAr() {
		return currencyNameAr;
	}
	public void setCurrencyNameAr(String currencyNameAr) {
		this.currencyNameAr = currencyNameAr;
	}
	public String getCurrencyNameEn() {
		return currencyNameEn;
	}
	public void setCurrencyNameEn(String currencyNameEn) {
		this.currencyNameEn = currencyNameEn;
	}
}
