package com.appgate.api.calculator.dtos.response;

import java.util.Objects;

public class SessionResponseDTO {

    private String sessionId;

    public SessionResponseDTO(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionResponseDTO that = (SessionResponseDTO) o;
        return Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }

    @Override
    public String toString() {
        return "SessionResponseDTO{" +
                "sessionId='" + sessionId + '\'' +
                '}';
    }
}
