package com.x.application.controller;

/**
 * Created by x on 2018/7/19.
 */
public class ViewResult {
    private int code;
    private String message;
    private Object result;

    public ViewResult() {

    }

    public ViewResult(int code, String message, Object result) {
        this.setCode(code);
        this.setMessage(message);
        this.setResult(result);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
