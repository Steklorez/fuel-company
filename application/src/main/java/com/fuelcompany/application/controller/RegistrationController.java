package com.fuelcompany.application.controller;

import com.fuelcompany.application.exception.ApiException;
import com.fuelcompany.domain.error.DomainException;
import com.fuelcompany.domain.services.PurchaseService;
import com.fuelcompany.infrastructure.api.registration.ApiPurchase;
import com.fuelcompany.infrastructure.api.registration.PurchaseResponse;
import com.fuelcompany.infrastructure.api.registration.PurchaseTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller to work with REST requests
 * Registration incoming records
 */
@Controller
@RequestMapping("/purchases")
public class RegistrationController {

    @Autowired
    private PurchaseService purchaseService;

//    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(method = RequestMethod.GET, produces = MediaType.MULTIPART_FORM_DATA_VALUE)

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PurchaseResponse save(ApiPurchase apiPurchase) {
        try {
            return new PurchaseResponse(PurchaseTransformer.toREST(purchaseService.save(PurchaseTransformer.toDomain(apiPurchase))));
        } catch (DomainException e) {
            e.printStackTrace();
            throw new ApiException(e);
        }
    }
}
