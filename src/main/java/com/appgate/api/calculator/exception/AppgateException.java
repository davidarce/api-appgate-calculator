package com.appgate.api.calculator.exception;

public class AppgateException extends RuntimeException {

    private static final long serialVersionUID = 1945437779090294987L;

    private int code;

    public AppgateException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
