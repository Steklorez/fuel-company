package com.fuelcompany.domain.error;


import lombok.Getter;


/**
* All business logic errors
*/

@Getter
public enum ErrorMessages {
    DOMAIN_ERROR_1050(1050, "Field 'date' is empty"),
    DOMAIN_ERROR_E1051(1051, "Field 'fuelType' is empty"),
    DOMAIN_ERROR_E1052(1052, "Field 'price' is empty"),
    DOMAIN_ERROR_E1053(1053, "Field 'driverId' is empty"),
    DOMAIN_ERROR_E1054(1054, "Wrong fuel type"),
    DOMAIN_ERROR_E9999(9999, "Internal server error"),
    ;

    private int code;
    private String message;

    ErrorMessages(int code, String message) {
        this.code = code;
        this.message = message;
    }
}