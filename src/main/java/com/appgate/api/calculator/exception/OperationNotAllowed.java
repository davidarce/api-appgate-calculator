package com.appgate.api.calculator.exception;

public class OperationNotAllowed extends AppgateException {

    private static final long serialVersionUID = 2341001814082995779L;
    private static final Errors ERROR = Errors.OPERATION_NOT_ALLOWED;

    public OperationNotAllowed() {
        super(ERROR.getCode(), ERROR.getDescription());
    }
}
