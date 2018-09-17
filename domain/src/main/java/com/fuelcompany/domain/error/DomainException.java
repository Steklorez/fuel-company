package com.fuelcompany.domain.error;

import lombok.Getter;

@Getter
public class DomainException extends Exception{

    private int code;
    private String message;

    public DomainException(ErrorMessages error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }
}