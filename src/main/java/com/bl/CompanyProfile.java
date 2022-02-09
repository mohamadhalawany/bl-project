package com.bl;

import java.util.Date;

public class CompanyProfile {
	private static int year = new Date().getYear() + 1900 ;
		
	public final static Integer CITY_DISTRICT_ID = 10 ;
	public final static Integer GOVERNORATE_ID = 1 ;
	public final static Integer COUNTRY_ID = 60 ;
	
	private String companyName = "Some Name"  ;
	private String address = "1 street of the Address" ;
	private String phone = "02 33994253 - 02 22033720" ;
	private String mobile = "01002080807" ;
	private String email = "mohamad.alhalawany@gmail.com" ;
	private String facebook = "FACEBOOK ACCOUNT" ;
	private String twitter = "TWITTER ACCOUNT" ;
	private String googleplus = "GOOGLEPLUS ACCOUNT" ;
	private String linkedin = "LINKEDIN ACCOUNT" ;
	private String youtube = "YOUTUBE ACCOUNT" ;
	private String vimeo = "VIMEO ACCOUNT" ;
	private String copyright = year + " © " + companyName + " ALL Rights Reserved." ;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getAddress() {
		return address;
	}
	public String getMobile() {
		return mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getGoogleplus() {
		return googleplus;
	}
	public void setGoogleplus(String googleplus) {
		this.googleplus = googleplus;
	}
	public String getLinkedin() {
		return linkedin;
	}
	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}
	public String getYoutube() {
		return youtube;
	}
	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}
	public String getVimeo() {
		return vimeo;
	}
	public void setVimeo(String vimeo) {
		this.vimeo = vimeo;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	
}
