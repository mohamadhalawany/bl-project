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
    private Double itemPriceOffer ;
    private String categoryName;
    private String categoryCode;
    private Integer createdBy ;
    private String createdByEmployeeName;
    private Date createdDate;
    private String createdDateString;
    private Integer updatedBy ;
    private String updatedByEmployeeName;
    private Date updatedDate;
    private String updatedDateString;
    private String description ;
    private String overview ;
    private Integer isHidden ;
    private String extension ;
    
    private int mode ;
    private String currencyName , currencyNameAr , internationalCode , offerValidTo ;
    private Integer offerValidity , isPercent , offerId ;
    private Long parentCategoryId ;
    private Double offerValue ;
    private Date offerValidToDate ;
    
    private Integer idInteger ;

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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
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

	public Integer getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Integer isHidden) {
		this.isHidden = isHidden;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Double getItemPriceOffer() {
		return itemPriceOffer;
	}

	public void setItemPriceOffer(Double itemPriceOffer) {
		this.itemPriceOffer = itemPriceOffer;
	}

	public Integer getOfferValidity() {
		return offerValidity;
	}

	public void setOfferValidity(Integer offerValidity) {
		this.offerValidity = offerValidity;
	}

	public Long getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Long parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public Double getOfferValue() {
		return offerValue;
	}

	public void setOfferValue(Double offerValue) {
		this.offerValue = offerValue;
	}

	public Integer getIsPercent() {
		return isPercent;
	}

	public void setIsPercent(Integer isPercent) {
		this.isPercent = isPercent;
	}

	public String getOfferValidTo() {
		return offerValidTo;
	}

	public void setOfferValidTo(String offerValidTo) {
		this.offerValidTo = offerValidTo;
	}

	public Date getOfferValidToDate() {
		return offerValidToDate;
	}

	public void setOfferValidToDate(Date offerValidToDate) {
		this.offerValidToDate = offerValidToDate;
	}

	public Integer getOfferId() {
		return offerId;
	}

	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}

	public Integer getIdInteger() {
		return idInteger;
	}

	public void setIdInteger(Integer idInteger) {
		this.idInteger = idInteger;
	}


    
}
