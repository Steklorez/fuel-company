package com.fuelcompany.application.controller;

import com.fuelcompany.application.exception.ApplicationException;
import com.fuelcompany.application.exception.BadRequestException;

public class RestPreconditions {
    public static <T> T checkFound(T resource, int code) throws ApplicationException {
        if (resource == null) {
            throw new BadRequestException(code, "Incoming resource is null");
        }
        return resource;
    }
}
