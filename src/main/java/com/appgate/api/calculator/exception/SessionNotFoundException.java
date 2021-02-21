package com.appgate.api.calculator.exception;

public class SessionNotFoundException extends AppgateException {

    private static final long serialVersionUID = 3454341636824189849L;
    private static final Errors ERROR = Errors.SESSION_NOT_FOUND;

    public SessionNotFoundException() {
        super(ERROR.getCode(), ERROR.getDescription());
    }
}
