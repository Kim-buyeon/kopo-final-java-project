package com.finaljavaproject.www.repository;

import java.util.List;

//파일에 접근하여 json 형식의 데이터르 읽어오거나 파일에 저장하는 기능 정의
public interface Repository<T, ID> {
	void save(T item);
	List<T> findAll();
	T findById(ID id);
	void deleteById(ID id);
}
