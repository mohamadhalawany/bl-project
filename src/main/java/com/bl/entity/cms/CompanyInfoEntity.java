package com.bl.entity.cms;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: CompanyInfoEntity
 *
 */
@Entity
@Table(name="company_info")
@NamedQuery(name="CompanyInfoEntity.findAll", query="SELECT c FROM CompanyInfoEntity c")
public class CompanyInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "COMPANY_NAME")
	private String companyName;
		
	@Column(name = "CITY_ID")
	private Integer cityId ;
	
	@Column(name = "ADDRESS")
	private String address ;
	
	@Column(name = "MOBILE")
	private String mobile ; 
	
	@Column(name = "PHONE")
	private String phone ;
	
	@Column(name = "EMAIL")
	private String email ;
	
	@Column(name = "LOGO")
	private String logo ;
	
	@Column(name = "SLOGAN")
	private String slogan ; 
	
	@Column(name = "UPDATED_BY")
	private Integer updatedBy ;
	
	@Column(name = "UPDATED_DATE")
	@Temporal(TemporalType.DATE)
	private Date updatedDate ;
	
	@Column(name = "IS_ACTIVE")
	private Integer isActive ;
	
	@Column(name = "FACEBOOK_ACCOUNT")
	private String facebookAccount ; 
	
	@Column(name = "TWITTER_ACCOUNT")
	private String twitterAccount ; 
	
	@Column(name = "GOOGLEPLUS_ACCOUNT")
	private String googleplusAccount ; 
	
	@Column(name = "LINKEDIN_ACCOUNT")
	private String linkedinAccount ; 
	
	@Column(name = "YOUTUBE_ACCOUNT")
	private String youtubeAccount ; 
	
	@Column(name = "VIMEO_ACCOUNT")
	private String  vimeoAccount ; 
	
	@Column(name = "browser_title_icon")
	private String  browserTitleIcon ; 
	
	@Column(name = "about_us")
	private String  aboutUs ; 
	
	public CompanyInfoEntity() {
		super();
	}   
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}   
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getCityId() {
		return cityId;
	}
	public String getAddress() {
		return address;
	}
	public String getMobile() {
		return mobile;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public String getLogo() {
		return logo;
	}
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	public String getSlogan() {
		return slogan;
	}
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	public String getFacebookAccount() {
		return facebookAccount;
	}
	public void setFacebookAccount(String facebookAccount) {
		this.facebookAccount = facebookAccount;
	}
	public String getTwitterAccount() {
		return twitterAccount;
	}
	public void setTwitterAccount(String twitterAccount) {
		this.twitterAccount = twitterAccount;
	}
	public String getGoogleplusAccount() {
		return googleplusAccount;
	}
	public void setGoogleplusAccount(String googleplusAccount) {
		this.googleplusAccount = googleplusAccount;
	}
	public String getLinkedinAccount() {
		return linkedinAccount;
	}
	public void setLinkedinAccount(String linkedinAccount) {
		this.linkedinAccount = linkedinAccount;
	}
	public String getYoutubeAccount() {
		return youtubeAccount;
	}
	public void setYoutubeAccount(String youtubeAccount) {
		this.youtubeAccount = youtubeAccount;
	}
	public String getVimeoAccount() {
		return vimeoAccount;
	}
	public void setVimeoAccount(String vimeoAccount) {
		this.vimeoAccount = vimeoAccount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBrowserTitleIcon() {
		return browserTitleIcon;
	}
	public void setBrowserTitleIcon(String browserTitleIcon) {
		this.browserTitleIcon = browserTitleIcon;
	}
	public String getAboutUs() {
		return aboutUs;
	}
	public void setAboutUs(String aboutUs) {
		this.aboutUs = aboutUs;
	}
   
}
