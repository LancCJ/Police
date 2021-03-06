package com.cybertech.police.module.login.model;

import com.cybertech.police.base.BaseResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by 健 on 2016/11/1.
 * json 返回值示例, 如果它作为Callback的泛型,
 * 那么xUtils将自动调用JsonResponseParser将字符串转换为BaiduResponse.
 *
 * @HttpResponse 注解 和 ResponseParser接口仅适合做json, xml等文本类型数据的解析,
 * 如果需要其他类型的解析可参考:
 * {@link org.xutils.http.loader.LoaderFactory}
 * 和 {@link org.xutils.common.Callback.PrepareCallback}.
 * LoaderFactory提供PrepareCallback第一个泛型参数类型的自动转换,
 * 第二个泛型参数需要在prepare方法中实现.
 * (LoaderFactory中已经默认提供了部分常用类型的转换实现, 其他类型需要自己注册.)
 */
@HttpResponse(parser = BaseResponseParser.class)
public class LoginResponseBase {
    private Integer code;
    private String message;
    private LoginResponse data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginResponse getData() {
        return data;
    }

    public void setData(LoginResponse data) {
        this.data = data;
    }
}
