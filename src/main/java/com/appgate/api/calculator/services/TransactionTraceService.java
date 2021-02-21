package com.appgate.api.calculator.services;

import com.appgate.api.calculator.models.TransactionActions;
import com.appgate.api.calculator.models.UserTransactionTrace;

import java.util.List;

public interface TransactionTraceService {

    void ok(String userSessionId, TransactionActions action);

    void error(String userSessionId, TransactionActions action, String stackTrace);

    List<UserTransactionTrace> getUserTransactions(String sessionId);

}
