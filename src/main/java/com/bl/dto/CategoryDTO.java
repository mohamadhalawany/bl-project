package com.bl.dto ;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CategoryDTO implements Serializable{
	
    private static final long serialVersionUID = 1L;

    private Long id;
    
    private String categoryName;
    private Long createdBy;
    private Long updatedBy;
    private Date createdDate;
    private Date updatedDate;
    
    private String createdByEmployeeName;
    private String updatedByEmployeeName;
    
    private List<ItemsDTO> itemsList;
    
    private int mode ;

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
}
