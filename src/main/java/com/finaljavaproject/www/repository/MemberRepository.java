package com.finaljavaproject.www.repository;

import com.finaljavaproject.www.domain.Member;
import com.finaljavaproject.www.util.JsonReader;
import com.finaljavaproject.www.util.JsonWriter;

import java.util.List;

public class MemberRepository implements Repository{
    private JsonReader reader;
    private JsonWriter writer;
    public MemberRepository(JsonReader reader, JsonWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void make(List<?> newItems) {
        String id = String.valueOf(newItems.get(0));
        String name = String.valueOf(newItems.get(1));
        String password = String.valueOf(newItems.get(2));
        String email = String.valueOf(newItems.get(3));


    }

    @Override
    public void update(Object object, List<?> updateItem) {
        if(object instanceof Member){
            Member member = (Member) object;
            String memberName = String.valueOf(updateItem.get(0));
            String telNo = String.valueOf(updateItem.get(1));
            String password = String.valueOf(updateItem.get(2));
        }
    }

    @Override
    public void delete(Object object) {

    }

    @Override
    public List<String> getJson(String url) {
        List<String> memberJson = reader.read(url);
        return memberJson;
    }
}
