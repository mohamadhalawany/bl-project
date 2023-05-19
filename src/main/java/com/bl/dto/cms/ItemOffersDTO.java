package com.bl.dto.cms;

import java.util.Date;

public class ItemOffersDTO {

	private Integer id;
	private Integer createdBy;
	private Date createdDate;
	private Long itemId;
	private Integer offerId;
	private Integer updatedBy;
	private Date updatedDate;
	
	private String offerName ;
	private Integer isPercent ;
	private Double offerValue ;
	private Date expireDate ;
	private Double itemPriceTemp ;
	private String itemLogo ;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Integer getOfferId() {
		return offerId;
	}
	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
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
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public Integer getIsPercent() {
		return isPercent;
	}
	public void setIsPercent(Integer isPercent) {
		this.isPercent = isPercent;
	}
	public Double getOfferValue() {
		return offerValue;
	}
	public void setOfferValue(Double offerValue) {
		this.offerValue = offerValue;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public Double getItemPriceTemp() {
		return itemPriceTemp;
	}
	public void setItemPriceTemp(Double itemPriceTemp) {
		this.itemPriceTemp = itemPriceTemp;
	}
	public String getItemLogo() {
		return itemLogo;
	}
	public void setItemLogo(String itemLogo) {
		this.itemLogo = itemLogo;
	}
}
