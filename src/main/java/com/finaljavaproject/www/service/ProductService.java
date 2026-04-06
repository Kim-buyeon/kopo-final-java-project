package com.finaljavaproject.www.service;

import com.finaljavaproject.www.domain.Product;
import com.finaljavaproject.www.repository.Repository;

public class ProductService extends AbstractService<Product, String>{

	public ProductService(Repository<Product, String> repository) {
		super(repository);
	}
	
	@Override
	public void create(Product product) {
		repository.save(product);
	}
	
	@Override
	public void update(Product product) {
		repository.save(product);
	}
	
	@Override
	public void remove(String id) {
		repository.deleteById(id);
	}
	
	public void manageStock(String id, int stock, boolean isAdding) {
		Product product = repository.findById(id);
		if(product == null)return;
		if(isAdding) {
			product.addStock(stock);
		}else {
			product.removeStock(stock);
		}
		update(product);
		
	}

}
