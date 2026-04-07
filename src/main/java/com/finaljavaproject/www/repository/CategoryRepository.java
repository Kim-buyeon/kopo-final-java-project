package com.finaljavaproject.www.repository;

import com.finaljavaproject.www.domain.Category;
import com.finaljavaproject.www.util.JsonReader;
import com.finaljavaproject.www.util.JsonWriter;

import java.util.List;

public class CategoryRepository implements Repository<Category ,String> {
    private final JsonReader reader;
    private final JsonWriter writer;
    private final String FILE_NAME = "categories.json";

    public CategoryRepository(JsonReader reader, JsonWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void save(Category item) {
            List<Category> categories = findAll();
            categories.removeIf(category -> category.getCategoryId().equalsIgnoreCase(item.getCategoryId()));
            categories.add(item);
            writer.write(FILE_NAME, categories);
    }

    @Override
    public List<Category> findAll() {
        return reader.read(FILE_NAME, Category.class);
    }

    @Override
    public Category findById(String id) {
        List<Category> categories = findAll();
        return categories.stream().filter(category -> category.getCategoryId().equalsIgnoreCase(id))
                .findFirst().orElse(null);
    }

    @Override
    public void deleteById(String id) {
        List<Category> categories = findAll();
        categories.removeIf(category -> category.getCategoryId().equalsIgnoreCase(id));
        writer.write(FILE_NAME,categories);
    }
}
