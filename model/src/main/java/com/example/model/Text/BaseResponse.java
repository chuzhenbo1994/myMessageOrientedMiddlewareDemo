package com.example.model.Text;


public class BaseResponse<T> {
    private Integer code; //状态码
    private String msg;  //描述状态
    private T data;     // 响应数据

    public BaseResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }

    public BaseResponse(StatusCode statusCode, T message) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = message;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
