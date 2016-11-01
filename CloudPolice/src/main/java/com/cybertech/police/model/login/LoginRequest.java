package com.cybertech.police.model.login;

import com.cybertech.police.base.Constant;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.DefaultParamsBuilder;

/**
 * 登录参数定义
 * Created by 健 on 2016/11/1.
 */

@HttpRequest(
        host = Constant.serverHostUrl,
        path = Constant.userLoginUrl,
        builder = DefaultParamsBuilder.class/*可选参数, 控制参数构建过程, 定义参数签名, SSL证书等*/)
public class LoginRequest extends RequestParams {

    public LoginRequest() {
        this.setAsJsonContent(true); // 请求body将参数转换为json形式发送
        this.addHeader("ClientType","Android");
    }
}
