package com.finaljavaproject.www.service;

import com.finaljavaproject.www.repository.Repository;

import java.util.List;

public class Service {
    private Repository repository;

    public void update(Object object, List<?> updatedItems){
        repository.update(object,updatedItems);
    }

    public void make(List<?> newItems){
        repository.make(newItems);
    }

    public void delete(Object object){
        repository.delete(object);
    }

    public void print(String url){
       List<String> json = repository.getJson(url);
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
