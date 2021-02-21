package com.appgate.api.calculator.services;

import com.appgate.api.calculator.models.Operators;

public interface CalculatorService {

    String createSession();

    void addOperand(String sessionId, String number);

    String calculate(String sessionId, Operators operator);

}
