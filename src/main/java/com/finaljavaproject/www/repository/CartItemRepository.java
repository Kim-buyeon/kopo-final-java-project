package com.finaljavaproject.www.repository;

import java.util.List;

import com.finaljavaproject.www.domain.CartItem;
import com.finaljavaproject.www.util.JsonReader;
import com.finaljavaproject.www.util.JsonWriter;

public class CartItemRepository implements Repository<CartItem, String>{
	private final JsonReader reader;
	private final JsonWriter writer;
	private final String FILE_NAME = "carts.json";
	public CartItemRepository(JsonReader reader, JsonWriter writer) {
		super();
		this.reader = reader;
		this.writer = writer;
	}
	@Override
	public void save(CartItem item) {
		List<CartItem> carts = findAll();
	}
	@Override
	public List<CartItem> findAll() {
		return reader.read(FILE_NAME, CartItem.class);
	}
	@Override
	public CartItem findById(String id) {
		List<CartItem> carts = findAll();
		return carts.stream().filter(c -> c.getId().equalsIgnoreCase(id))
				.findFirst().orElse(null);
	}
	
	@Override
	public void deleteById(String id) {
		List<CartItem> carts = findAll();
		carts.removeIf(c -> c.getId().equals(id));
		writer.write(FILE_NAME, carts);
	}

}
