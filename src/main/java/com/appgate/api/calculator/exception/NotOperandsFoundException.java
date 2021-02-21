package com.appgate.api.calculator.exception;

public class NotOperandsFoundException extends AppgateException {

    private static final long serialVersionUID = 8259248748401452980L;
    private static final Errors ERROR = Errors.NOT_OPERANDS_FOUND;

    public NotOperandsFoundException() {
        super(ERROR.getCode(), ERROR.getDescription());
    }
}
