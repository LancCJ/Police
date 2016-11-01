package com.cybertech.police.base;

/**
 * 系统常量配置
 * Created by 健 on 2016/11/1.
 */

public class Constant {
    /**
     * 系统服务地址
     */
    public static final String serverHostUrl="http://localhost/";
    /**
     * 用户登录请求
     * 登录发送报文:
     *     {userName:lanccj,uerPwd:123456}
     * 登录返回报文:
     *     {}
     */
    public static final String loginUrl="services/user/login.json";
}
