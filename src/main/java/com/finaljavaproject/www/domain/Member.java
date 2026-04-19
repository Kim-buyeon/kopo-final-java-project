package com.finaljavaproject.www.domain;

import com.finaljavaproject.www.domain.constant.MemberClassfication;

public class Member {
    private String id;
    private String password;
    private String name;
    private String telNo;
    private String email;
    private String status;
    private MemberClassfication memberClassfication;

    public Member(String id, String password, String name, String telNo, String email, MemberClassfication memberClassfication) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.telNo = telNo;
        this.email = email;
        this.status = "정상";
        this.memberClassfication = memberClassfication;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public MemberClassfication getMemberClassfication() {
        return memberClassfication;
    }

    public void setMemberClassfication(MemberClassfication memberClassfication) {
        this.memberClassfication = memberClassfication;
    }
}
