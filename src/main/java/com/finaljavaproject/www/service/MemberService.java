package com.finaljavaproject.www.service;

import com.finaljavaproject.www.domain.Member;
import com.finaljavaproject.www.repository.Repository;

public class MemberService extends AbstractService<Member, String>{
	
	private Member currentMember;
	public MemberService(Repository<Member, String> repository) {
		super(repository);
	}

	//로그인 하는 함수
	public boolean login(String id, String password) {
		Member member = repository.findById(id);
		 if(member != null && member.getPassword().equals(password)) {
			 this.currentMember = member;
			 System.out.println(member.getName()+ "님 로그인 되었습니다.");
			 return true;
		 }else {
			 System.out.println("로그인에 실패했습니다.");
			 return false;
		 }
	}

	public Member getCurrentMember() {
		return currentMember;
	}

	//로그아웃 하는 함수
	public void logout(String id) {
		Member member = repository.findById(id);
		 if(member != null) {
			 System.out.println(member.getName()+ "님 로그아웃 되었습니다.");
			 this.currentMember = null;
		 }else {
			 System.out.println("로그인에 실패했습니다.");
		 }
	}
	
	@Override
	public void create(Member member) {
		repository.save(member);
	}
	
	@Override
	public void remove(String id) {
		repository.deleteById(id);
	}
	
	@Override
	public void update(Member member) {
		repository.save(member);
	}

}
