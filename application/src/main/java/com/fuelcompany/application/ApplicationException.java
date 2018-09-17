package com.fuelcompany.application;


import lombok.Getter;

@Getter
public class ApplicationException extends Exception{

    private int code;
    private String message;

    public ApplicationException(int errorCode, String errorMessage) {
        this.code = errorCode;
        this.message = errorMessage;
    }
}
