package com.finaljavaproject.www.domain;

import java.util.List;
import java.util.UUID;

public class Category {
    private String categoryId;
    private Category parent;
    private String name;
    private String sortOrder;

    public Category(){

    }
    public Category(Category parent, String name, String sortOrder) {
        this.categoryId = UUID.randomUUID().toString();
        this.parent = parent;
        this.name = name;
        this.sortOrder = sortOrder;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isMainCategory(){
        return this.parent  == null;
    }

    public String getParentName(){
        return (parent != null) ? parent.getName() : "없음 대분류";
    }

}
