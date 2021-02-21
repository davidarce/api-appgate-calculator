package com.appgate.api.calculator.dtos.request;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class OperandRequest {

    @NotNull
    private String number;

    public OperandRequest() {
    }

    public OperandRequest(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperandRequest that = (OperandRequest) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "OperandRequest{" +
                "number='" + number + '\'' +
                '}';
    }
}
