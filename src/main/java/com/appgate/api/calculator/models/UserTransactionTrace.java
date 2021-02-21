package com.appgate.api.calculator.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Optional;

@Document("user-transactions-traces")
public class UserTransactionTrace {

    @Indexed
    private String id;
    @Indexed
    private String userSessionId;
    private TransactionActions action;
    private boolean success;
    private String stackTrace;
    private LocalDateTime createdAt;

    public UserTransactionTrace() {
    }

    public UserTransactionTrace(UserTransactionTraceBuilder builder) {
        this.userSessionId = builder.userSessionId;
        this.action = builder.action;
        this.success = builder.success;
        this.stackTrace = builder.stackTrace;
        this.createdAt = Optional.ofNullable(builder.createdAt).orElse(LocalDateTime.now());
    }

    public static class UserTransactionTraceBuilder {
        private String userSessionId;
        private TransactionActions action;
        private boolean success;
        private String stackTrace;
        private LocalDateTime createdAt;

        private UserTransactionTraceBuilder() {
        }

        public UserTransactionTraceBuilder userSessionId(String userSessionId) {
            this.userSessionId = userSessionId;
            return this;
        }

        public UserTransactionTraceBuilder action(TransactionActions action) {
            this.action = action;
            return this;
        }

        public UserTransactionTraceBuilder success(boolean success) {
            this.success = success;
            return this;
        }

        public UserTransactionTraceBuilder stackTrace(String stackTrace) {
            this.stackTrace = stackTrace;
            return this;
        }

        public UserTransactionTraceBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserTransactionTrace build() {
            return new UserTransactionTrace(this);
        }
    }

    public static UserTransactionTraceBuilder builder() {
        return new UserTransactionTraceBuilder();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    public TransactionActions getAction() {
        return action;
    }

    public void setAction(TransactionActions action) {
        this.action = action;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
