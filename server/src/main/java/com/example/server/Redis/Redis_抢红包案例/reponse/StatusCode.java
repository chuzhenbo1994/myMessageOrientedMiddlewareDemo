package com.example.server.Redis.Redis_抢红包案例.reponse;

public enum  StatusCode {
    SUCCESS(0,"成功"),
    FAIL(-1,"失败"),
    INVALIDPARAMS(201,"非法的参数"),
    INVALIDGRANTTYPE(202,"非法的授权类型");
    private Integer code;
    private String msg;

    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
