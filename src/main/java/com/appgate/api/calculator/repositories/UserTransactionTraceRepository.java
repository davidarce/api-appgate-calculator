package com.appgate.api.calculator.repositories;

import com.appgate.api.calculator.models.UserTransactionTrace;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserTransactionTraceRepository extends MongoRepository<UserTransactionTrace, String> {

    List<UserTransactionTrace> findAllByUserSessionIdOrderByCreatedAtDesc(String userSessionId);
}
