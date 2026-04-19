package com.finaljavaproject.www.controller;

import com.finaljavaproject.www.domain.Member;
import com.finaljavaproject.www.service.MemberService;
import com.finaljavaproject.www.view.MemberView;

public class MemberController extends AbstractController<Member, String>{

	private static String sessionUserId = null;
	private final MemberService memberService;
	private final MemberView memberView;
	public MemberController(MemberService service, MemberView view) {
		        super(service, view);
		        this.memberService = service;
		        this.memberView = view;
	}
	
	public static String getSessionUserId() {
		return  sessionUserId;
	}

	@Override
	public void processRead() {
		memberView.renderList(memberService.getAll());
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
		String[] loginData = memberView.inputLoginData();
		String id = loginData[0];
		String pw = loginData[1];
		boolean success = memberService.login(id, pw);
		if(success) {
			sessionUserId = id;
		}
	}
	
	public  void processLogout() {
		if(sessionUserId != null) {
			memberService.logout(sessionUserId);
			sessionUserId = null;
		}
	}

}
