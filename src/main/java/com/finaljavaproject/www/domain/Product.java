package com.finaljavaproject.www.domain;

import com.finaljavaproject.www.domain.constant.ProductStatus;

public class Product {
	private String productId;
	private String name;
	private String description;
	private int price;
	private int stock;
	private ProductStatus productStatus;
	public Product(String productId, String name, String description, int price, int stock,
			ProductStatus productStatus) {
		super();
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.productStatus = productStatus;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	
	public void addStock(int quantity) {
		this.stock += quantity;
		if(this.stock > 0 && this.productStatus == ProductStatus.SUSPENSION) {
			setProductStatus(ProductStatus.NORMAL);
		}
	}
	
	public void removeStock(int quantity) {
		if (this.stock < quantity) {
		        this.stock = 0; 
		        setProductStatus(ProductStatus.SUSPENSION);
		    } else {
		        this.stock -= quantity;
		        if (this.stock == 0) {
		            setProductStatus(ProductStatus.SUSPENSION);
		        }
		    }
	}
	
	
	public ProductStatus getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(ProductStatus productStatus) {
		this.productStatus = productStatus;
	}
	
	
}
