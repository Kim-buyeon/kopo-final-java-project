package com.finaljavaproject.www.service;

import java.util.List;

//service에 들어갈 기능을 정희하는 인터페이스
public interface CommonService<T, ID> {
	//객체 생성하는 함수
	void create(T item);
	//객체 삭제하는 함수
	void remove(ID id);
	//아이디에 해당하는 객체 리턴하는 함수
	T getOne(ID id);
	//리스트애 들어있는 객체들을 모두 리턴하는 함수
	List<T> getAll();
}
