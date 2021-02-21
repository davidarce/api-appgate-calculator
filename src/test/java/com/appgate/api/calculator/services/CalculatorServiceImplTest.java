package com.appgate.api.calculator.services;

import com.appgate.api.calculator.AppInitializer;
import com.appgate.api.calculator.exception.DivisionByZeroException;
import com.appgate.api.calculator.exception.InvalidNumberException;
import com.appgate.api.calculator.exception.NotOperandsFoundException;
import com.appgate.api.calculator.exception.SessionNotFoundException;
import com.appgate.api.calculator.models.UserSession;
import com.appgate.api.calculator.models.Operators;
import com.appgate.api.calculator.repositories.UserSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = AppInitializer.class)
@ExtendWith(SpringExtension.class)
@DirtiesContext
public class CalculatorServiceImplTest {

    @Autowired
    UserSessionRepository repository;

    @Autowired
    CalculatorService calculatorService;

    @Test
    public void shouldCreateNewSessionOk() {
        String newSession = calculatorService.createSession();

        UserSession userSession = repository.findBySessionId(newSession);

        assertNotNull(userSession);
        assertEquals(userSession.getSessionId(), newSession);
    }

    @Test
    public void shouldCreateNewSessionAndAddOperandsOK() {
        String newSession = calculatorService.createSession();

        calculatorService.addOperand(newSession, "1");
        calculatorService.addOperand(newSession, "2");
        calculatorService.addOperand(newSession, "3");

        UserSession userSession = repository.findBySessionId(newSession);

        List<BigDecimal> expectedOperands = new ArrayList<>();
        expectedOperands.add(new BigDecimal("1"));
        expectedOperands.add(new BigDecimal("2"));
        expectedOperands.add(new BigDecimal("3"));

        assertNotNull(userSession);
        assertEquals(newSession, userSession.getSessionId());
        assertEquals(expectedOperands, userSession.getOperands());
    }

    @Test
    public void shouldNotAcceptedInvalidOperandNumber() {
        String newSession = calculatorService.createSession();

        assertThrows(InvalidNumberException.class,
                () -> calculatorService.addOperand(newSession, "IamNotANumber"));
    }

    @Test
    public void shouldNotAcceptedOperandWhenSessionIdNotExits() {
        assertThrows(SessionNotFoundException.class,
                () -> calculatorService.addOperand("invalidSessionId", "1"));
    }

    @Test
    public void shouldCalculateResultWhenAddOperationIsSentOK() {
        String newSession = calculatorService.createSession();

        calculatorService.addOperand(newSession, "1");
        calculatorService.addOperand(newSession, "2");
        calculatorService.addOperand(newSession, "3");

        String result = calculatorService.calculate(newSession, Operators.ADD);

        assertEquals(result, "6");
    }

    @Test
    public void shouldCalculateResultWhenSubtractOperationIsSentOK() {
        String newSession = calculatorService.createSession();

        calculatorService.addOperand(newSession, "1");
        calculatorService.addOperand(newSession, "2");
        calculatorService.addOperand(newSession, "3");

        String result = calculatorService.calculate(newSession, Operators.SUBTRACT);

        assertEquals(result, "-4");
    }

    @Test
    public void shouldCalculateResultWhenMultiplyOperationIsSentOK() {
        String newSession = calculatorService.createSession();

        calculatorService.addOperand(newSession, "1");
        calculatorService.addOperand(newSession, "2");
        calculatorService.addOperand(newSession, "5");

        String result = calculatorService.calculate(newSession, Operators.MULTIPLY);

        assertEquals(result, "10");
    }

    @Test
    public void shouldCalculateResultWhenDivideOperationIsSentOK() {
        String newSession = calculatorService.createSession();

        calculatorService.addOperand(newSession, "1");
        calculatorService.addOperand(newSession, "2");
        calculatorService.addOperand(newSession, "5");

        String result = calculatorService.calculate(newSession, Operators.DIVIDE);

        assertEquals(result, "0.1");
    }

    @Test
    public void shouldCalculateResultWhenExpOperationIsSentOK() {
        String newSession = calculatorService.createSession();

        calculatorService.addOperand(newSession, "2");
        calculatorService.addOperand(newSession, "2");
        calculatorService.addOperand(newSession, "2");

        String result = calculatorService.calculate(newSession, Operators.EXP);

        assertEquals(result, "16");
    }

    @Test
    public void shouldCalculateChainedOperations() {
        String newSession = calculatorService.createSession();

        calculatorService.addOperand(newSession, "2");
        calculatorService.addOperand(newSession, "2");
        calculatorService.addOperand(newSession, "2");

        String firstCalculate = calculatorService.calculate(newSession, Operators.ADD);

        calculatorService.addOperand(newSession, "2");
        calculatorService.addOperand(newSession, "2");
        calculatorService.addOperand(newSession, "2");

        String secondCalculate = calculatorService.calculate(newSession, Operators.ADD);

        assertEquals(firstCalculate, "6");
        assertEquals(secondCalculate, "12");
    }

    @Test
    public void shouldReturnLasResultWhenCalculateIsCalledAgain() {
        String newSession = calculatorService.createSession();

        calculatorService.addOperand(newSession, "2");
        calculatorService.addOperand(newSession, "2");
        calculatorService.addOperand(newSession, "2");

        String result = calculatorService.calculate(newSession, Operators.ADD);
        String lastResult = calculatorService.calculate(newSession, Operators.ADD);

        assertEquals(result, "6");
        assertEquals(lastResult, "6");
    }

    @Test
    public void shouldThrowsExceptionWhenTryToCalculateDivideOperationWithZero() {
        String newSession = calculatorService.createSession();

        calculatorService.addOperand(newSession, "2");
        calculatorService.addOperand(newSession, "2");
        calculatorService.addOperand(newSession, "0");

        assertThrows(DivisionByZeroException.class,
                () -> calculatorService.calculate(newSession, Operators.DIVIDE));
    }

    @Test
    public void shouldThrowsExceptionWhenTryToCalculateOperationWithoutOperands() {
        String newSession = calculatorService.createSession();

        assertThrows(NotOperandsFoundException.class,
                () -> calculatorService.calculate(newSession, Operators.ADD));
    }

    @Test
    public void shouldThrowsExceptionWhenTryToCalculateOperationWhenSessionNotExits() {
        assertThrows(SessionNotFoundException.class,
                () -> calculatorService.calculate("invalidSessionId", Operators.ADD));
    }
}
