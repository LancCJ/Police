package com.cybertech.police.base;

/**
 * 系统常量配置
 * Created by 健 on 2016/11/1.
 */

public class Constant {
    /**
     * 系统服务地址
     */
    public static final String serverHostUrl="http://192.168.142.1/services";
    /**
     * 用户登录请求
     * 登录发送报文:
     *     {userName:lanccj,uerPwd:123456}
     * 登录返回报文:
     *     {}
     */
    public static final String userLoginUrl="user/login.json";

    //登录状态码
    public static final Integer USER_LOGIN_NO_PARAMS = 103;//缺少参数
    public static final Integer USER_LOGIN_PARAMS_NO_USERNAME = 104;//缺少用户名
    public static final Integer USER_LOGIN_PARAMS_NO_USERPWD = 105;//缺少密码
    public static final Integer USER_LOGIN_PARAMS_USERNAMENOTMATCHPWD = 106;//用户密码不匹配
    public static final Integer USER_LOGIN_SUCCESS = 107;//用户登录成功


    public static final String EMPTY_STRING = "";
    //user information
    public static final String ISLOGIN = "isLogin";
    public static final String LOGINTIME = "loginTime";
    public static final String USERACCOUNT = "userAccount";
    public static final String USERNAME = "userName";
    public static final String USERID = "userID";
}
