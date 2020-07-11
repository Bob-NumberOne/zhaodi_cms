package com.zhaodi.custom.entity;

public class User {
    private String loginName;
    private String PassWord;
    private String img;

    public User(String loginName, String passWord) {
        this.loginName = loginName;
        PassWord = passWord;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }
}

