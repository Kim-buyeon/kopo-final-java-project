package com.finaljavaproject.www.repository;

import com.finaljavaproject.www.domain.Order;
import com.finaljavaproject.www.util.JsonReader;
import com.finaljavaproject.www.util.JsonWriter;

import java.util.List;

public class OrderRepository implements Repository<Order, String> {
    private final JsonReader reader;
    private final JsonWriter writer;
    private final String FILE_NAME = "orders.json";

    public OrderRepository(JsonReader reader, JsonWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void save(Order item) {
        List<Order> orders = findAll();
        orders.removeIf(o->o.getId().equalsIgnoreCase(item.getId()));
        orders.add(item);
        writer.write(FILE_NAME, orders);
    }

    @Override
    public List<Order> findAll() {
        return reader.read(FILE_NAME,Order.class);
    }

    @Override
    public Order findById(String s) {
        return null;
    }

    @Override
    public void deleteById(String s) {

    }
}
