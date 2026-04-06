package com.finaljavaproject.www.repository;

import java.util.List;

public interface Repository {
    public void make(List<?> newItems);
    public void update(Object object, List<?> updatedItem);
    public void delete(Object object);
    public List<String> getJson(String url);
}
