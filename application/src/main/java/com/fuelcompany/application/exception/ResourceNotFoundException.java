package com.fuelcompany.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends ApplicationException {
    public ResourceNotFoundException(int errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
