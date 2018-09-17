package com.fuelcompany.infrastructure.api;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private Error error;

    protected void setError(int code, String message) {
        this.error = new Error(code, message);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class Error {
        private int code;
        private String message;
    }
}