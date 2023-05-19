package com.bl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the career_applicant database table.
 * 
 */
@Entity
@Table(name="career_applicant")
@NamedQuery(name="CareerApplicantEntity.findAll", query="SELECT c FROM CareerApplicantEntity c")
public class CareerApplicantEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="applicant_cv")
	private String applicantCv;

	@Column(name="applicant_mail")
	private String applicantMail;

	@Column(name="applicant_mobile")
	private String applicantMobile;

	@Column(name="applicant_name")
	private String applicantName;

	@Column(name="career_description")
	private String careerDescription;

	@Column(name="career_id")
	private int careerId;

	@Column(name="career_name")
	private String careerName;

	@Column(name="company_id")
	private int companyId;

	private String note;

	@Temporal(TemporalType.DATE)
	@Column(name="status_date")
	private Date statusDate;

	@Column(name="status_id")
	private int statusId;

	public CareerApplicantEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApplicantCv() {
		return this.applicantCv;
	}

	public void setApplicantCv(String applicantCv) {
		this.applicantCv = applicantCv;
	}

	public String getApplicantMail() {
		return this.applicantMail;
	}

	public void setApplicantMail(String applicantMail) {
		this.applicantMail = applicantMail;
	}

	public String getApplicantMobile() {
		return this.applicantMobile;
	}

	public void setApplicantMobile(String applicantMobile) {
		this.applicantMobile = applicantMobile;
	}

	public String getApplicantName() {
		return this.applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getCareerDescription() {
		return this.careerDescription;
	}

	public void setCareerDescription(String careerDescription) {
		this.careerDescription = careerDescription;
	}

	public int getCareerId() {
		return this.careerId;
	}

	public void setCareerId(int careerId) {
		this.careerId = careerId;
	}

	public String getCareerName() {
		return this.careerName;
	}

	public void setCareerName(String careerName) {
		this.careerName = careerName;
	}

	public int getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getStatusDate() {
		return this.statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public int getStatusId() {
		return this.statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

}