package com.finaljavaproject.www.service;

import java.util.List;

import com.finaljavaproject.www.repository.Repository;

//CommonService interface를 상속받은 추상클래스이다. 모든 서비스에 적용되는 메서드는 구현
public abstract class AbstractService<T,  ID> implements CommonService<T, ID> {
    protected Repository<T, ID> repository;
    

public AbstractService(Repository<T, ID> repository) {
	super();
	this.repository = repository;
}

@Override
public void create(T item) {
	System.out.println("데이터 생성로그" + item);
	repository.save(item);
}

public void update(T item) {
	System.out.println("데이터 업데이트 로그" + item);
	repository.save(item);
}

@Override
public void remove(ID id) {
	System.out.println("데이터 삭제로그");
	repository.deleteById(id);
}

@Override
public T getOne(ID id) {
	return repository.findById(id);
}

@Override
public List<T> getAll() {
	return repository.findAll();
}
    
    
}
