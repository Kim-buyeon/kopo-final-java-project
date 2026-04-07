package com.finaljavaproject.www.view;

import java.util.List;

//콘솔창에 띄우는 기능을 정의한 인터페이스
public interface View<T, ID> {
	//메뉴를 보여줌
	void showMenu();
	//전체 메뉴애서 고른 선택사항 리턴 이에따라 선택사항에 따라 다른 메서드 실행
	ID inputSelection();
	//scanner로부터 입력을 받아 생성 또는 수정된 객체 리턴
	T  inputData();
	//T inputUpdateData(T existingItem);
	//입력받은 아이디 리턴
	ID inputId();
	//데이터의 세부정보들을 출력해주는 함수
	void renderDetail(T item);
	//리스트에 들어있는 전체 데이터 출력
	void renderList(List<T> items);
	//헤더 출력하는 함수
	void printHeader(ID title);
	//메시지 출력하는 함수
	void renderMessage(ID message);
}
