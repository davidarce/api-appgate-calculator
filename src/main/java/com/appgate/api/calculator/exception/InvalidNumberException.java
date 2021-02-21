package com.appgate.api.calculator.exception;

public class InvalidNumberException extends AppgateException {

    private static final long serialVersionUID = 1142759358499387431L;
    private static final Errors ERROR = Errors.INVALID_NUMBER_FORMAT;

    public InvalidNumberException() {
        super(ERROR.getCode(), ERROR.getDescription());
    }
}
