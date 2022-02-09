package com.bl.entity.cms;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the login_user database table.
 * 
 */
@Entity
@Table(name="login_user")
@NamedQuery(name="LoginUserEntity.findAll", query="SELECT l FROM LoginUserEntity l")

public class LoginUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_LOGIN")
	private Date lastLogin; 

	@Column(name="PASSWORD")
	private String password;

	@Column(name="USERNAME")
	private String username;
	
	@Column(name="FULL_NAME") 
	private String fullName ;
	
	@Column(name="LAST_LOGIN_DATE")
	private String lastLoginDate ;
	
	@Column(name = "ACTIVE_FLAG") 
	private Integer activeFlag ;

	
	

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Integer getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Integer activeFlag) {
		this.activeFlag = activeFlag;
	}

}