package com.fuelcompany.application.controller;

import com.fuelcompany.domain.aggregateModels.purchase.Purchase;
import com.fuelcompany.domain.error.DomainException;
import com.fuelcompany.infrastructure.api.registration.ApiPurchase;
import com.fuelcompany.infrastructure.api.registration.PurchaseTransformer;
import com.fuelcompany.infrastructure.exception.ApiException;
import com.fuelcompany.infrastructure.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controller with REST requests mapping
 * Registration incoming ApiPurchase
 */
@RestController
@RequestMapping("/purchases")
public class RegistrationController {
    private static Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private Purchase purchase;

    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ApiPurchase save(@RequestBody ApiPurchase request) {
        try {
            return PurchaseTransformer.toREST(purchase.save(PurchaseTransformer.toDomain(request)));
        } catch (DomainException e) {
            logger.error("Domain exception", e);
            throw new ApiException(e);
        }
    }

    @Transactional
    @PostMapping("/file")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public List<ApiPurchase> save(@RequestParam(value = "file") MultipartFile file) {
        try {
            return PurchaseTransformer.toREST(purchase.save(PurchaseTransformer.toDomain(FileUtil.convertToObjectList(file))));
        } catch (DomainException e) {
            logger.error("Domain exception", e);
            throw new ApiException(e);
        }
    }
}
