package com.appgate.api.calculator.services.impl;

import com.appgate.api.calculator.exception.*;
import com.appgate.api.calculator.interceptors.TransactionLogger;
import com.appgate.api.calculator.models.Operators;
import com.appgate.api.calculator.models.TransactionActions;
import com.appgate.api.calculator.models.UserSession;
import com.appgate.api.calculator.repositories.UserSessionRepository;
import com.appgate.api.calculator.services.CalculatorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    private final UserSessionRepository repository;

    public CalculatorServiceImpl(UserSessionRepository repository) {
        this.repository = repository;
    }

    @Override
    @TransactionLogger(action = TransactionActions.CREATE_SESSION)
    public String createSession() {
        UserSession userSession = UserSession.builder()
                .sessionId()
                .operands(new ArrayList<>())
                .build();
        repository.save(userSession);
        return userSession.getSessionId();
    }

    @Override
    @Transactional
    @TransactionLogger(action = TransactionActions.ADD_OPERAND)
    public void addOperand(String sessionId, String number) {
        UserSession userSession = retrieveSessionInformation(sessionId);
        userSession.addOperand(parseNumber(number));
        userSession.setUpdatedAt(LocalDateTime.now());
        repository.save(userSession);
    }

    @Override
    @Transactional
    @TransactionLogger(action = TransactionActions.CALCULATE_OPERATION)
    public String calculate(String sessionId, Operators operator) {
        UserSession userSession = retrieveSessionInformation(sessionId);

        if (userSession.getOperands().isEmpty()) {
            throw new NotOperandsFoundException();
        }

        if (userSession.getOperands().size() == 1) {
            return userSession.getOperands().get(0).toString();
        }

        return resolveOperation(operator, userSession);

    }

    private String resolveOperation(Operators operator, UserSession userSession) {
        BigDecimal result = null;
        List<BigDecimal> operands = userSession.getOperands();

        switch (operator) {
            case ADD:
                result = operands.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
                break;
            case SUBTRACT:
                result = operands.stream().reduce(BigDecimal::subtract).orElseThrow(OperationNotAllowed::new);
                break;
            case MULTIPLY:
                result = operands.stream().reduce(BigDecimal.ONE, BigDecimal::multiply);
                break;
            case DIVIDE:
                result = divide(operands);
                break;
            case EXP:
                result = exp(operands);
                break;
        }

        userSession.setOperands(Collections.singletonList(result));
        userSession.setUpdatedAt(LocalDateTime.now());
        repository.save(userSession);
        return result.stripTrailingZeros().toPlainString();
    }

    private UserSession retrieveSessionInformation(String sessionId) {
        return Optional.ofNullable(repository.findBySessionId(sessionId))
                .orElseThrow(SessionNotFoundException::new);
    }

    private BigDecimal parseNumber(String number) {
        try {
            return new BigDecimal(number);
        } catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
    }

    private BigDecimal exp(List<BigDecimal> operands) {
        BigDecimal result = operands.get(0);
        for (int i = 1; i < operands.size(); i++) {
            BigDecimal nextOperand = operands.get(i);
            result = result.pow(nextOperand.intValue());
        }
        return result;
    }

    private BigDecimal divide(List<BigDecimal> operands) {
        BigDecimal result = operands.get(0);
        for (int i = 1; i < operands.size(); i++) {
            BigDecimal nextOperand = operands.get(i);
            if (nextOperand.intValue() != BigDecimal.ZERO.intValue()) {
                result = result.divide(operands.get(i), MathContext.DECIMAL64);
            } else {
                throw new DivisionByZeroException();
            }
        }
        return result;
    }
}
