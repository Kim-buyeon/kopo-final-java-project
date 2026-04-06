package com.finaljavaproject.www.repository;

import java.util.List;

import com.finaljavaproject.www.domain.Member;
import com.finaljavaproject.www.util.JsonReader;
import com.finaljavaproject.www.util.JsonWriter;

public class MemberRepository implements Repository<Member,String>{
	private final JsonReader reader;
	private final JsonWriter writer;
	private final String FILE_NAME = "users.json";
	
	public MemberRepository(JsonReader reader, JsonWriter writer) {
		super();
		this.reader = reader;
		this.writer = writer;
	}
	@Override
	public void save(Member item) {
		List<Member> members = findAll();
		members.removeIf(m -> m.getId().equalsIgnoreCase(item.getId()));
		members.add(item);
		writer.write(FILE_NAME, members);
	}
	
	@Override
	public List<Member> findAll() {
		return reader.read(FILE_NAME, Member.class);
	}
	@Override
	public Member findById(String id) {
		List<Member> members = findAll();
		return members.stream().filter(m -> m.getId().equalsIgnoreCase(id))
				.findFirst()
				.orElse(null);
	}
	@Override
	public void deleteById(String id) {
		List<Member> members = findAll();
		members.removeIf(m -> m.getId().equalsIgnoreCase(id));
		writer.write(FILE_NAME, members);
	}
	



}
