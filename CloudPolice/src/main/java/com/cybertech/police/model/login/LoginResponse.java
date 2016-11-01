package com.cybertech.police.model.login;

import com.cybertech.police.base.JsonResponseParser;
import com.cybertech.police.base.ServerResult;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by 健 on 2016/11/1.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class LoginResponse {
    private ServerResult serverResult;

    private String userName;

    public ServerResult getServerResult() {
        return serverResult;
    }

    public void setServerResult(ServerResult serverResult) {
        this.serverResult = serverResult;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userName;
    }
}
