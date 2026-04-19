package com.finaljavaproject.www.domain;

public class Category {
    private String categoryId;
    private String parentId; 
    private String name;
    private String sortOrder;

    public Category() {}

    public Category(String categoryId,String parentId, String name, String sortOrder) {
        this.categoryId = categoryId;
        this.parentId = parentId;
        this.name = name;
        this.sortOrder = sortOrder;
    }

    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSortOrder() { return sortOrder; }
    public void setSortOrder(String sortOrder) { this.sortOrder = sortOrder; }

    public boolean isMainCategory() {
        return this.parentId == null;
    }
}