package com.fuelcompany.domain.error;


import lombok.Getter;

@Getter
public enum ErrorMessages {
    E1050(1050, "Field 'date' is empty"),
    E1051(1051, "Field 'fuelType' is empty"),
    E1052(1052, "Field 'price' is empty"),
    E1053(1053, "Field 'driverId' is empty"),
    E1054(1054, "Wrong fuel type"),
    ;

    private int code;
    private String message;

    ErrorMessages(int code, String message) {
        this.code = code;
        this.message = message;
    }
}