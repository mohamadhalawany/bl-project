package com.bl.dto.cms;

import java.util.Date;

public class SettingDTO {
	
	private Integer id;
	private String mainPicture;
	private String mainPictureDescription ;
	private String mainPictureDescriptionLine ;
	private Integer sliceItems;
	private Integer updatedBy;
	private Date updatedDate;
	private Integer createdBy;
	private Date createdDate;
	private Integer expireDays ;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMainPicture() {
		return mainPicture;
	}
	public void setMainPicture(String mainPicture) {
		this.mainPicture = mainPicture;
	}
	public Integer getSliceItems() {
		return sliceItems;
	}
	public void setSliceItems(Integer sliceItems) {
		this.sliceItems = sliceItems;
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
	public String getMainPictureDescription() {
		return mainPictureDescription;
	}
	public void setMainPictureDescription(String mainPictureDescription) {
		this.mainPictureDescription = mainPictureDescription;
	}
	public String getMainPictureDescriptionLine() {
		return mainPictureDescriptionLine;
	}
	public void setMainPictureDescriptionLine(String mainPictureDescriptionLine) {
		this.mainPictureDescriptionLine = mainPictureDescriptionLine;
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
	public Integer getExpireDays() {
		return expireDays;
	}
	public void setExpireDays(Integer expireDays) {
		this.expireDays = expireDays;
	}
	
	
}
