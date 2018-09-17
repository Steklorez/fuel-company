package com.fuelcompany.application.controller;

import com.fuelcompany.application.ApplicationException;

public class RestPreconditions {
    public static <T> T checkFound(T resource, int code) throws ApplicationException {
        if (resource == null) {
            throw new ApplicationException(code, "Incoming resource is null");
        }
        return resource;
    }
}
