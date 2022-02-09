package com.bl.dto ;

import java.io.Serializable;
import java.util.Date;


public class ItemsDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private Long categoryId ;
    private Integer currencyId ;
    private String itemName;
    private String itemCode;
    private String itemLogo;
    private Double itemPrice;
    private String categoryName;
    private String categoryCode;
    private Long createdBy ;
    private String createdByEmployeeName;
    private Date createdDate;
    private String createdDateString;
    private Long updatedBy ;
    private String updatedByEmployeeName;
    private Date updatedDate;
    private String updatedDateString;
    private String description ;
    private String overview ;
    
    private int mode ;
    private String currencyName , currencyNameAr , internationalCode ;
    
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getItemName() {
	return itemName;
    }

    public void setItemName(String itemName) {
	this.itemName = itemName;
    }

    public String getItemCode() {
	return itemCode;
    }

    public void setItemCode(String itemCode) {
	this.itemCode = itemCode;
    }

    public String getItemLogo() {
	return itemLogo;
    }

    public void setItemLogo(String itemLogo) {
	this.itemLogo = itemLogo;
    }

    public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getCategoryName() {
	return categoryName;
    }

    public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
    }

    public String getCategoryCode() {
	return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
	this.categoryCode = categoryCode;
    }

    public String getCreatedByEmployeeName() {
	return createdByEmployeeName;
    }

    public void setCreatedByEmployeeName(String createdByEmployeeName) {
	this.createdByEmployeeName = createdByEmployeeName;
    }

    public String getUpdatedByEmployeeName() {
	return updatedByEmployeeName;
    }

    public void setUpdatedByEmployeeName(String updatedByEmployeeName) {
	this.updatedByEmployeeName = updatedByEmployeeName;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCurrencyNameAr() {
		return currencyNameAr;
	}

	public void setCurrencyNameAr(String currencyNameAr) {
		this.currencyNameAr = currencyNameAr;
	}

	public String getInternationalCode() {
		return internationalCode;
	}

	public void setInternationalCode(String internationalCode) {
		this.internationalCode = internationalCode;
	}

    
}
