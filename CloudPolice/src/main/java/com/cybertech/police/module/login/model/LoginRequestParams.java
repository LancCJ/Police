package com.cybertech.police.module.login.model;

/**
 * Created by 健 on 2016/11/1.
 */

public class LoginRequestParams {
    private String userName;
    private String userPwd;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
