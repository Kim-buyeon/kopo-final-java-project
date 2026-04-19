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
		super.renderMessage("1. 회원가입 \t  2. 로그인\t  3. 회원정보 조회");
	}

	@Override
	public Member inputData() {
		Member newMember = null;
		super.printHeader("회원가입");
		//if (scanner.hasNextLine()) scanner.nextLine();
		System.out.println("DEBUG: 입력을 시작합니다...");
		System.out.print("아이디 (영문/숫지 5~15자):  ");
		String id = scanner.nextLine().trim();
		System.out.println("DEBUG: 입력된 ID = [" + id + "]");
		System.out.print("비밀번호  :");
		String password = scanner.nextLine().trim();
		System.out.println("DEBUG: 입력된 ID = [" + password  + "]");
		System.out.print("이름 : ");
		String name = scanner.nextLine().trim();
		System.out.println("DEBUG: 입력된 ID = [" + name  + "]");
		System.out.print("전화번호 : ");
		String telNo = scanner.nextLine().trim();
		System.out.println("DEBUG: 입력된 ID = [" + telNo  + "]");
		System.out.print("이메일 : ");
		String email = scanner.nextLine().trim();
		System.out.println("DEBUG: 입력된 ID = [" + email  + "]");
		System.out.print("회원 구분(일반회원 or 관리자로 입력): ");
		String classfication = scanner.nextLine().trim();
		System.out.println("DEBUG: 입력된 ID = [" + classfication  + "]");
		String idRegex = "^[a-zA-Z0-9]{5,15}$";
		String passwordRegex  = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{5,15}$";
		
		if (!id.matches(idRegex) || !password.matches(passwordRegex)) {
		            super.renderMessage("아이디나 비밀번호 제약 조건이 올바르지 않습니다.");
		            return null; 
		 }
		
		if(id.matches(idRegex) && password.matches(passwordRegex)) {
			if(classfication.equals("관리자")) {
				newMember = new Member(id, password, name, telNo, email, MemberClassfication.MANAGER);
			}else if (classfication.equals("일반회원")) {
				newMember = new Member(id, password, name, telNo, email, MemberClassfication.MEMBER);
			}else {
				super.renderMessage("해당하는 분류가 없습니다.");
			}
		}
		
		return newMember;
	}


	@Override
	public void renderDetail(Member item) {
		System.out.println("------------------------------------");
		super.renderMessage("아이디 : " + item.getId());
		super.renderMessage("이름 : " + item.getName());
		super.renderMessage("이메일 : " + item.getEmail());
		super.renderMessage("전화번호 : " + item.getTelNo());
		super.renderMessage("회원구분 : " + item.getStatus());
	}

	@Override
	public void renderList(List<Member> items) {
		if(items.isEmpty()) {
			super.renderMessage("등록된 멤버가 없습니다.");
			return;
		}
		super.printHeader("멤버조회");
		items.stream().forEach(item -> renderDetail(item));
		System.out.println("------------------------------------");
	}

	@Override
	public String inputSelection() {
		System.out.print("메뉴 선택: ");
		String choice = scanner.next();
		return choice;
	}
	
	public String[] inputLoginData() {
		System.out.print("아이디: ");
		String id = scanner.next();
		System.out.print("비밀번호 : ");
		String pw = scanner.next();
		return new String[] { id, pw };
	}

}
