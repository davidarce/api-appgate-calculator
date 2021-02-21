package com.appgate.api.calculator.exception;

public class DivisionByZeroException extends AppgateException {

    private static final long serialVersionUID = -4816298442335743181L;
    private static final Errors ERROR = Errors.DIVIDE_BY_ZERO;

    public DivisionByZeroException() {
        super(ERROR.getCode(), ERROR.getDescription());
    }
}
