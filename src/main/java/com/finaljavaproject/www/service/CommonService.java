package com.finaljavaproject.www.service;

import java.util.List;

public interface CommonService<T, ID> {
	void create(T item);
	void remove(ID id);
	T getOne(ID id);
	List<T> getAll();
}
