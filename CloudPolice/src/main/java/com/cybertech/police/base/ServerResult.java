package com.cybertech.police.base;

/**
 * Created by 健 on 2016/11/1.
 */

public class ServerResult {
    private Integer code;
    private String resultMessage;

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Integer getCode() {
        return code;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
