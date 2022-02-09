package com.bl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the order_status database table.
 * 
 */
@Entity
@Table(name="order_status")
@NamedQuery(name="OrderStatusEntity.findAll", query="SELECT o FROM OrderStatusEntity o")
public class OrderStatusEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="ORDER_ID")
	private Long orderId;

	@Temporal(TemporalType.DATE)
	@Column(name="STATUS_DATE")
	private Date statusDate;

	@Column(name="STATUS_ID")
	private Integer statusId;

	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getStatusDate() {
		return this.statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public Integer getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

}