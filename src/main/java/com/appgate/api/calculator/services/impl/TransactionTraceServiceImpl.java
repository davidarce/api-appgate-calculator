package com.appgate.api.calculator.services.impl;

import com.appgate.api.calculator.models.TransactionActions;
import com.appgate.api.calculator.models.UserTransactionTrace;
import com.appgate.api.calculator.repositories.UserTransactionTraceRepository;
import com.appgate.api.calculator.services.TransactionTraceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionTraceServiceImpl implements TransactionTraceService {

    private final UserTransactionTraceRepository repository;

    public TransactionTraceServiceImpl(UserTransactionTraceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void ok(String userSessionId, TransactionActions action) {
        UserTransactionTrace trace = UserTransactionTrace.builder()
                .userSessionId(userSessionId)
                .action(action)
                .success(true)
                .createdAt(LocalDateTime.now()).build();

        repository.save(trace);
    }

    @Override
    public void error(String userSessionId, TransactionActions action, String stackTrace) {
        UserTransactionTrace trace = UserTransactionTrace.builder()
                .userSessionId(userSessionId)
                .action(action)
                .success(false)
                .stackTrace(stackTrace)
                .createdAt(LocalDateTime.now()).build();

        repository.save(trace);
    }

    @Override
    public List<UserTransactionTrace> getUserTransactions(String sessionId) {
        return repository.findAllByUserSessionIdOrderByCreatedAtDesc(sessionId);
    }
}
