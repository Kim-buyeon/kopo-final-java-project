package com.finaljavaproject.www.controller;

import com.finaljavaproject.www.domain.Member;
import com.finaljavaproject.www.service.MemberService;
import com.finaljavaproject.www.view.MemberView;

public class MemberController extends AbstractController<Member, String>{

	//이거 로그인아이디 저장하는 거 main class 역할을 하는 Ecommerce class에서 다시 작성할 예정
	private static String sessionUserId = null;
	public MemberController(MemberService service, MemberView view) {
		        super(service, view);
	}
	
	public static String getSessionUserId() {
		return  sessionUserId;
	}

	@Override
	public void processRead() {
		view.renderList(service.getAll());
	}

	@Override
	public void start() {
		boolean isRunning = true;
		        while (isRunning) {
		            view.showMenu();
		            String choice = view.inputSelection();

		            switch (choice) {
		                case "1": 
		                    processCreate();
		                    break;
		                case "2": 
		                    if (sessionUserId == null) {
		                        processLogin();
		                    } else {
		                        processLogout();
		                    }
		                    break;
		                case "3":
		                    processRead();
		                    break;
		                case "0":
		                    view.renderMessage("프로그램을 종료합니다.");
		                    isRunning = false;
		                    break;
		                default:
		                    view.renderMessage("잘못된 입력입니다.");
		            }
		        }
		
	}

	public void processLogin() {
		view.printHeader("로그인");
		System.out.print("아이디 : ");
		String id = view.inputId();
		System.out.print("비밀번호 : ");
		String password=  view.inputSelection();
		((MemberService)service).login(id, password);
		sessionUserId = id;
	}
	
	public  void processLogout() {
		if(sessionUserId != null) {
			((MemberService)service).logout(sessionUserId);
			sessionUserId = null;
			view.renderMessage("로그아웃 되었습니다.");
		}
	}

}
