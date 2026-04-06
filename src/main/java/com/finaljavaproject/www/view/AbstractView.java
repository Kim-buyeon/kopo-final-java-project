package com.finaljavaproject.www.view;

import java.util.List;
import java.util.Scanner;

public abstract class AbstractView<T, ID> implements View<T, ID> {
	
	protected final Scanner scanner = new Scanner(System.in);
	@Override
	public void printHeader(ID title) {
		System.out.println("=".repeat(10) + " " + title + "=".repeat(10));
	}
	
	@Override
	public void renderMessage(ID message) {
		System.out.println(">>" + message);
	}
	
	@Override
	public ID inputId() {
		System.out.print("ID를 입력하세요");
		String id=  scanner.next();
		if(id == null || id.trim().isEmpty()) {
			renderMessage((ID)"잘못된 입력입니다.");
			return inputId();
		}
		return  (ID)id;
	}
	
	
	public abstract void showMenu();
	public abstract T inputData();
	//public abstract T inputUpdateData(T existingItem);
	public abstract void renderDetail(T item);
	public abstract void renderList(List<T> items);
	public abstract ID inputSelection();
	
	
	
	

}
