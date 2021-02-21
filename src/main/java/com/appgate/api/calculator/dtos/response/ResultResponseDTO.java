package com.appgate.api.calculator.dtos.response;

import java.math.BigInteger;

public class ResultResponseDTO {

    private String result;

    public ResultResponseDTO(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultResponseDTO{" +
                "result=" + result +
                '}';
    }
}
