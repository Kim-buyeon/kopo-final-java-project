package com.finaljavaproject.www.controller;

//controller가 기능을 정의한 인터페이스
public interface Controller<T> {
	//controllerdp 모든 프로세스들을 관리하는 메서드
	void start();
	//객체를 생성하는 메서드
	void processCreate();
	//객체들을 읽어오는 메서드
	void processRead();
	//객체를 업데이트 하는 메서드
	void processUpdate();
	//객체를 삭제하는 메서드
	void processDelete();
}
