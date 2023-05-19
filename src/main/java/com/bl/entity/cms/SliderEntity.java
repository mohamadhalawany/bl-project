package com.bl.entity.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the slider database table.
 * 
 */
@Entity
@Table(name="slider")
@NamedQuery(name="SliderEntity.findAll", query="SELECT s FROM SliderEntity s")
public class SliderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="description")
	private String description;

	@Column(name="first_line")
	private String firstLine;

	@Column(name="item_id")
	private Long itemId;

	@Column(name="item_price")
	private Double itemPrice;

	@Column(name="second_line")
	private String secondLine;

	@Column(name="show_more_details")
	private Integer showMoreDetails;

	@Column(name="show_price")
	private Integer showPrice;

	@Column(name="third_line")
	private String thirdLine;

	@Column(name="upload_pic")
	private String uploadPic;
 
	@Column(name="CREATED_BY")
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="IS_ACTIVE")
	private Integer isActive;

	@Column(name="UPDATED_BY")
	private Integer updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFirstLine() {
		return this.firstLine;
	}

	public void setFirstLine(String firstLine) {
		this.firstLine = firstLine;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Double getItemPrice() {
		return this.itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getSecondLine() {
		return this.secondLine;
	}

	public void setSecondLine(String secondLine) {
		this.secondLine = secondLine;
	}

	public Integer getShowMoreDetails() {
		return this.showMoreDetails;
	}

	public void setShowMoreDetails(Integer showMoreDetails) {
		this.showMoreDetails = showMoreDetails;
	}

	public Integer getShowPrice() {
		return this.showPrice;
	}

	public void setShowPrice(Integer showPrice) {
		this.showPrice = showPrice;
	}

	public String getThirdLine() {
		return this.thirdLine;
	}

	public void setThirdLine(String thirdLine) {
		this.thirdLine = thirdLine;
	}

	public String getUploadPic() {
		return this.uploadPic;
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

}