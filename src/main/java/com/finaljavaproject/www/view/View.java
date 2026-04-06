package com.finaljavaproject.www.view;

import java.util.List;

public interface View<T, ID> {
	void showMenu();
	ID inputSelection();
	T  inputData();
	//T inputUpdateData(T existingItem);
	ID inputId();
	void renderDetail(T item);
	void renderList(List<T> items);
	void printHeader(ID title);
	void renderMessage(ID message);
}
