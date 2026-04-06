package com.finaljavaproject.www.service;

import java.util.List;
import java.util.Optional;

import com.finaljavaproject.www.domain.Member;
import com.finaljavaproject.www.repository.Repository;

public class MemberService extends AbstractService<Member, String>{
	public MemberService(Repository<Member, String> repository) {
		super(repository);
	}
	
	public void login(String id, String password) {
		List<Member> members = repository.findAll();
		 Optional<Member> member = members.stream().
				 filter(m -> m.getId().equalsIgnoreCase(id) && m.getPasswrod().equalsIgnoreCase(password))
				 .findFirst();
		 if(member != null) {
			 System.out.println(member.get().getName()+ "님 로그인 되었습니다.");
		 }else {
			 System.out.println("로그인에 실패했습니다.");
		 }
	}
	
	public void logout(String id) {
		List<Member> members = repository.findAll();
		Optional<Member> member = members.stream().
				 filter(m -> m.getId().equalsIgnoreCase(id))
				 .findFirst();
		 if(member != null) {
			 System.out.println(member.get().getName()+ "님 로그아웃 되었습니다.");
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
