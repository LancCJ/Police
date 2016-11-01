package com.cybertech.police.base;

import com.cybertech.police.model.login.LoginResponse;
import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 健 on 2016/11/1.
 */

public class JsonResponseParser implements ResponseParser {// 如果实现 InputStreamResponseParser, 可实现自定义流数据转换.
    @Override
    public void checkResponse(UriRequest request) throws Throwable {
        // custom check ?
        // get headers ?
    }

    /**
     * 转换result为resultType类型的对象
     *
     * @param resultType  返回值类型(可能带有泛型信息)
     * @param resultClass 返回值类型
     * @param result      字符串数据
     * @return
     * @throws Throwable
     */
    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        // TODO: json to java bean
        if (resultClass == List.class) {
            // 这里只是个示例, 不做json转换.
            List<LoginResponse> list = new ArrayList<LoginResponse>();
            LoginResponse LoginResponse = new LoginResponse();
            LoginResponse.setUserName(result);
            list.add(LoginResponse);
            return list;
            // fastJson 解析:
            // return JSON.parseArray(result, (Class<?>) ParameterizedTypeUtil.getParameterizedType(resultType, List.class, 0));
        } else {
            // 这里只是个示例, 不做json转换.
            LoginResponse LoginResponse = new LoginResponse();
            LoginResponse.setUserName(result);
            return LoginResponse;
            // fastjson 解析:
            // return JSON.parseObject(result, resultClass);
        }

    }
}
