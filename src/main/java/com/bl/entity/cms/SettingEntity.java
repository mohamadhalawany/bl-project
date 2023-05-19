package com.bl.entity.cms;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the settings database table.
 * 
 */
@Entity
@Table(name="settings")
@NamedQuery(name="SettingEntity.findAll", query="SELECT s FROM SettingEntity s")
public class SettingEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name="MAIN_PICTURE")
	private String mainPicture;
	
	@Column(name="MAIN_PICTURE_DESCRIPTION")
	private String mainPictureDescription ;
	
	@Column(name="MAIN_PICTURE_DESCRIPTION_LINE")
	private String mainPictureDescriptionLine ;
	
	@Column(name="SLICE_ITEMS")
	private Integer sliceItems;

	@Column(name="UPDATED_BY")
	private Integer updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name="CREATED_BY")
	private Integer createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="expire_days")
	private Integer expireDays ;
	

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMainPicture() {
		return this.mainPicture;
	}

	public void setMainPicture(String mainPicture) {
		this.mainPicture = mainPicture;
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

	public Integer getSliceItems() {
		return this.sliceItems;
	}

	public void setSliceItems(Integer sliceItems) {
		this.sliceItems = sliceItems;
	}

	public Integer getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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