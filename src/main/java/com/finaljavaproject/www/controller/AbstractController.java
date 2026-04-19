package com.finaljavaproject.www.controller;

import com.finaljavaproject.www.service.AbstractService;
import com.finaljavaproject.www.view.AbstractView;

//Controller 인터페이스에서 상속받은 추상 클래스로써 모든 컨트롤러 클래스에 공통으로 적용되는 메서드만 구현하고 타입에 따라 달라지는 메서드는 미 구현
public abstract class AbstractController<T, ID> implements Controller<T> {
	protected final AbstractService<T, ID> service;
	protected final AbstractView<T, ID> view;
	
	public AbstractController(AbstractService<T, ID> service,  AbstractView<T, ID> view) {
		super();
		this.service = service;
		this.view = view;
	}
	
	@Override
	public void processCreate() {
		T data = view.inputData();
		try {
			System.out.println("DEBUG: 컨트롤러 - 서비스 호출 직전");
			service.create(data);
			System.out.println("DEBUG: 컨트롤러 - 서비스 호출 완료");
			view.renderMessage((ID) "성공적으로 등록되었습니다.");
		} catch (Exception e) {
			view.renderMessage((ID)"등록 실패");
			e.printStackTrace();
		}
	}
	@Override
	public void processUpdate() {
		
		try {
			ID id = view.inputId();
			T existingItem = service.getOne(id);
			if(existingItem == null) {
				view.renderMessage((ID)"해당 아이디의 데이터를 찾을 수 없습니다.");
			}
			service.update(existingItem);
			view.renderMessage((ID)"성공적으로 업데이트 되었습니다.");
		} catch (Exception e) {
			view.renderMessage((ID)"업데이트 실패");
			e.printStackTrace();
		}
	}
	
	@Override
	public void processDelete() {
		ID id = view.inputId();
		try {
			service.remove(id);
			view.renderMessage((ID)"삭제 성공");
		} catch (Exception e) {
			e.printStackTrace();
			view.renderMessage((ID)"삭제 실패");
		}
	}
	
	@Override
	public abstract void processRead();
	@Override
	public abstract void start();
	

}
