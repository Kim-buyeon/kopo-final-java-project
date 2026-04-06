package com.finaljavaproject.www.repository;

import java.util.List;

public interface Repository<T, ID> {
	void save(T item);
	List<T> findAll();
	T findById(ID id);
	void deleteById(ID id);
}
