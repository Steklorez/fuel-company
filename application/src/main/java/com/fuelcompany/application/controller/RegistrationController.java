package com.fuelcompany.application.controller;

import com.fuelcompany.application.exception.BadRequestException;
import com.fuelcompany.domain.error.DomainException;
import com.fuelcompany.domain.services.RegistrationService;
import com.fuelcompany.infrastructure.api.registration.Record;
import com.fuelcompany.infrastructure.api.registration.RegistrationResponse;
import com.fuelcompany.infrastructure.api.registration.RegistrationTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/reports")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

//    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(method = RequestMethod.GET, produces = MediaType.MULTIPART_FORM_DATA_VALUE)

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public RegistrationResponse registrate(Record record) {
        try {
            RestPreconditions.checkFound(record, 1000);
            return new RegistrationResponse(RegistrationTransformer.toREST(registrationService.registrate(RegistrationTransformer.toDomain(record))));
        } catch (DomainException e) {
            e.printStackTrace();
            return new RegistrationResponse(e.getCode(), e.getMessage());
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new RegistrationResponse(e.getCode(), e.getMessage());
        }
    }
}
