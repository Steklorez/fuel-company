package com.fuelcompany.application.exception;


import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private int code;
    private String message;

    public ApplicationException(int errorCode, String errorMessage) {
        this.code = errorCode;
        this.message = errorMessage;
    }
}
