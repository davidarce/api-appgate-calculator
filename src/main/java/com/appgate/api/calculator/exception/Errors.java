package com.appgate.api.calculator.exception;

public enum Errors {
    SESSION_NOT_FOUND(404, "Session id doesn't exist"),
    INVALID_NUMBER_FORMAT(400, "Invalid number format"),
    DIVIDE_BY_ZERO(400, "Division by zero error"),
    NOT_OPERANDS_FOUND(400, "Not operands found to calculate operation"),
    OPERATION_NOT_ALLOWED(400, "Operation not allowed");


    private final Integer code;
    private final String description;

    Errors(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Errors getByCode(Integer code) {
        for(Errors e : values()) {
            if(e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
