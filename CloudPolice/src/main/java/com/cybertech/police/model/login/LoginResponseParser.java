package com.cybertech.police.model.login;

import com.alibaba.fastjson.JSON;
import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;
import java.lang.reflect.Type;

/**
 * Created by 健 on 2016/11/1.
 */

public class LoginResponseParser implements ResponseParser {
    @Override
    public void checkResponse(UriRequest request) throws Throwable {
    }

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        return JSON.parseObject(result, resultClass);
    }
}
