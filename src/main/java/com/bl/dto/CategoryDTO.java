package com.bl.dto ;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CategoryDTO implements Serializable{
	
    private static final long serialVersionUID = 1L;

    private Long id;    
    private String categoryName;
    private Integer createdBy;
    private Integer updatedBy;
    private Date createdDate;
    private Date updatedDate;
    private Integer isActive ;
    private Long parentCategoryId ;
    private Integer menuId ;
    
    private String createdByEmployeeName;
    private String updatedByEmployeeName;
    private String subCategoryName ;
    private String parentCategoryName ;
    private int mode ;
    private Long subCategoryId ;
    
    private List<ItemsDTO> itemsList;
    private List<CategoryDTO> subCategoryList ;
    private List<CategoryDTO> parentCategoryList ;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ItemsDTO> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<ItemsDTO> itemsList) {
        this.itemsList = itemsList;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
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

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Long getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Long parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public List<CategoryDTO> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(List<CategoryDTO> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getParentCategoryName() {
		return parentCategoryName;
	}

	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}

	public List<CategoryDTO> getParentCategoryList() {
		return parentCategoryList;
	}

	public void setParentCategoryList(List<CategoryDTO> parentCategoryList) {
		this.parentCategoryList = parentCategoryList;
	}
}
