package com.bl.dto.cms;

import java.util.Date;

public class OffersDTO {

	private Integer id;
	private Integer createdBy;
	private Date createdDate;
	private Integer isActive;
	private Integer isPercent;
	private String offerName;
	private Double offerValue;
	private Integer updatedBy;
	private Date updatedDate;
	private Date validFrom;
	private Date validTo;
	
	private String from , to , createdDateString , updatedDateString ;
	private int expired ;

	
	
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

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Integer getIsPercent() {
		return isPercent;
	}

	public void setIsPercent(Integer isPercent) {
		this.isPercent = isPercent;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public Double getOfferValue() {
		return offerValue;
	}

	public void setOfferValue(Double offerValue) {
		this.offerValue = offerValue;
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

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCreatedDateString() {
		return createdDateString;
	}

	public void setCreatedDateString(String createdDateString) {
		this.createdDateString = createdDateString;
	}

	public String getUpdatedDateString() {
		return updatedDateString;
	}

	public void setUpdatedDateString(String updatedDateString) {
		this.updatedDateString = updatedDateString;
	}

	public int getExpired() {
		return expired;
	}

	public void setExpired(int expired) {
		this.expired = expired;
	}
}
