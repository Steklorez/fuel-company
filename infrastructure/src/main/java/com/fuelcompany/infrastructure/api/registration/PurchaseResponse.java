package com.fuelcompany.infrastructure.api.registration;

import com.fuelcompany.infrastructure.api.Response;
import lombok.Getter;

@Getter
public class PurchaseResponse extends Response {
    private ApiPurchase purchase;

    public PurchaseResponse(ApiPurchase apiPurchase) {
        this.purchase = apiPurchase;
    }
}