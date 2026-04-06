package com.finaljavaproject.www.view;

import java.util.List;
import java.util.Scanner;

import com.finaljavaproject.www.domain.Member;
import com.finaljavaproject.www.domain.constant.MemberClassfication;

public class MemberView extends AbstractView<Member, String> {

	private Scanner scanner = new Scanner(System.in);
	@Override
	public void showMenu() {
		super.printHeader("회원관리");
		super.renderMessage("1. 회원가입 \t  2. 로그인\t  3. 회원정보 관리 ");
	}

	@Override
	public Member inputData() {
		Member newMember = null;
		System.out.print("아이디 :  ");
		String id = scanner.next();
		System.out.print("비밀번호  :");
		String password = scanner.next();
		System.out.print("이름 : ");
		String name = scanner.next();
		System.out.print("전화번호 : ");
		String telNo = scanner.next();
		System.out.print("이메일 : ");
		String email = scanner.next();
		System.out.print("회원 구분(일반회원 or 관리자로 입력): ");
		String classfication = scanner.next();
		String idRegex = "^[a-zA-Z0-9]{5,15}";
		String passwordRegex  = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{5,15}";
		if(id.matches(idRegex) && password.matches(passwordRegex)) {
			if(classfication.equals("관리자")) {
				newMember = new Member(id, password, name, telNo, email, MemberClassfication.MAMANGER);
			}else if (classfication.equals("일반회원")) {
				newMember = new Member(id, password, name, telNo, email, MemberClassfication.MEMBER);
			}else {
				super.renderMessage("해당하는 분류가 없습니다.");
			}
			
		}else {
			super.renderMessage("아이디와 비밀번호 제약 조건이 올바르지 않습니다. 다시 한번 확인해주세요");
		}
		
		return newMember;
	}


	@Override
	public void renderDetail(Member item) {
		super.renderMessage("아이디 : " + item.getId());
		super.renderMessage("비밀번호 : " + item.getName());
		super.renderMessage("이메일 : " + item.getEmail());
		super.renderMessage("전화번호 : " + item.getTelNo());
		super.renderMessage("회원구분 : " + item.getStatus());
	}

	@Override
	public void renderList(List<Member> items) {
		items.stream().forEach(item -> renderDetail(item));
	}

	@Override
	public String inputSelection() {
		String choice = scanner.next();
		return choice;
	}

}
