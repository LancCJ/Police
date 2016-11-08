package com.cybertech.police.module.login.model;

/**
 * Created by 健 on 2016/11/2.
 */

public class LoginResponse {
    private String userName;
    private String reaylName;
    private String phoneNum;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReaylName() {
        return reaylName;
    }

    public void setReaylName(String reaylName) {
        this.reaylName = reaylName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
