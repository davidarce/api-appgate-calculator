package com.appgate.api.calculator.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Document("user-sessions")
public class UserSession {

    @Indexed
    private String id;
    @Indexed(unique = true)
    private String sessionId;
    private List<BigDecimal> operands;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserSession() {
    }

    public UserSession(UserSessionBuilder builder) {
        this.sessionId = builder.sessionId;
        this.operands = builder.operands;
        this.createdAt = Optional.ofNullable(builder.createdAt).orElse(LocalDateTime.now());
        this.updatedAt = Optional.ofNullable(builder.updatedAt).orElse(LocalDateTime.now());
    }

    public static class UserSessionBuilder {
        private String sessionId;
        private List<BigDecimal> operands;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        private UserSessionBuilder() {
        }

        public UserSessionBuilder sessionId() {
            this.sessionId = UUID.randomUUID().toString();
            return this;
        }

        public UserSessionBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserSessionBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public UserSessionBuilder operands(final List<BigDecimal> operands) {
            this.operands = operands;
            return this;
        }

        public UserSession build() {
            return new UserSession(this);
        }
    }

    public static UserSessionBuilder builder() {
        return new UserSessionBuilder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<BigDecimal> getOperands() {
        return operands;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void addOperand(BigDecimal operand) {
        operands.add(operand);
    }

    public void setOperands(List<BigDecimal> operands) {
        this.operands = operands;
    }

    @Override
    public String toString() {
        return "UserCalculatorSession{" +
                "id='" + id + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", operands=" + operands +
                '}';
    }
}
