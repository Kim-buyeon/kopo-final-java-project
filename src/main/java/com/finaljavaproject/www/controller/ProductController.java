package com.finaljavaproject.www.controller;

import com.finaljavaproject.www.domain.Product;
import com.finaljavaproject.www.service.AbstractService;
import com.finaljavaproject.www.view.AbstractView;

public class ProductController extends AbstractController<Product, String> {

	public ProductController(AbstractService<Product, String> service, AbstractView<Product, String> view) {
		super(service, view);
	}
	
	@Override
	public void processRead() {
		view.renderList(service.getAll());
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
	}
	
	
	
}
