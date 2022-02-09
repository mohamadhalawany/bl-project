package com.bl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the item_specifications database table.
 * 
 */
@Entity
@Table(name="item_specifications")
@NamedQuery(name="ItemSpecificationsEntity.findAll", query="SELECT i FROM ItemSpecificationsEntity i")
public class ItemSpecificationsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="COLOR_ID")
	private Integer colorId;

	@Column(name="CREATED_BY")
	private Integer createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="depth")
	private String depth;
	
	@Column(name="height")
	private String height;

	@Column(name="included")
	private String included;

	@Column(name="ITEM_ID")
	private Long itemId;

	@Column(name="MANUFACTURING_TIME")
	private String manufacturingTime;

	@Column(name="material")
	private String material;

	@Column(name="UPDATED_BY")
	private Integer updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	@Column(name="warranty")
	private String warranty;

	@Column(name="width")
	private String width;

	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getColorId() {
		return this.colorId;
	}

	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDepth() {
		return this.depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getIncluded() {
		return this.included;
	}

	public void setIncluded(String included) {
		this.included = included;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getManufacturingTime() {
		return this.manufacturingTime;
	}

	public void setManufacturingTime(String manufacturingTime) {
		this.manufacturingTime = manufacturingTime;
	}

	public String getMaterial() {
		return this.material;
	}

	public void setMaterial(String material) {
		this.material = material;
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

	public String getWarranty() {
		return this.warranty;
	}

	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	public String getWidth() {
		return this.width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

}