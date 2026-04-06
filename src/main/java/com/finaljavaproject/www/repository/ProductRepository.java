package com.finaljavaproject.www.repository;

import java.util.List;

import com.finaljavaproject.www.domain.Product;
import com.finaljavaproject.www.util.JsonReader;
import com.finaljavaproject.www.util.JsonWriter;

public class ProductRepository implements Repository<Product, String> {
	private final JsonReader reader;
	private final JsonWriter writer;
	private final String FILE_NAME = "products.json";
	
	public ProductRepository(JsonReader reader, JsonWriter writer) {
		super();
		this.reader = reader;
		this.writer = writer;
	}

	@Override
	public void save(Product item) {
		List<Product> products = findAll();
		products.removeIf(p -> p.getProductId().equalsIgnoreCase(item.getProductId()));
		products.add(item);
		writer.write(FILE_NAME, products);
	}

	@Override
	public List<Product> findAll() {
		return reader.read(FILE_NAME, Product.class);
	}

	@Override
	public Product findById(String id) {
		List<Product> products = findAll();
		return products.stream().filter(p -> p.getProductId().equalsIgnoreCase(id))
				.findFirst().orElse(null);
	}

	@Override
	public void deleteById(String id) {
		List<Product> products = findAll();
		products.removeIf(p -> p.getProductId().equalsIgnoreCase(id));
		writer.write(FILE_NAME, products);
	}

}
