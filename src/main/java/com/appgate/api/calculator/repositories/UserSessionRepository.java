package com.appgate.api.calculator.repositories;

import com.appgate.api.calculator.models.UserSession;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserSessionRepository extends MongoRepository<UserSession, String> {

    UserSession findBySessionId(String sessionId);
}
