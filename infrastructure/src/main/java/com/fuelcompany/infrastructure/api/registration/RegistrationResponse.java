package com.fuelcompany.infrastructure.api.registration;

import com.fuelcompany.infrastructure.api.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegistrationResponse extends Response {
    private Record record;

    public RegistrationResponse(Record record) {
        this.record = record;
    }

    public RegistrationResponse(int errorCode, String errorMessage) {
        this.setError(errorCode, errorMessage);
    }
}