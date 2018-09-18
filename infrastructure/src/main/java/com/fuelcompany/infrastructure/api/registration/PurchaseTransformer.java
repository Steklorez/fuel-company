package com.fuelcompany.infrastructure.api.registration;

import com.fuelcompany.domain.entity.PurchaseEntity;

public class PurchaseTransformer {
    public static PurchaseEntity toDomain(ApiPurchase apiPurchase) {
        return new PurchaseEntity(
                apiPurchase.getFuelType(),
                apiPurchase.getPrice(),
                apiPurchase.getDriverId(),
                apiPurchase.getDate()
        );
    }

    public static ApiPurchase toREST(PurchaseEntity savedPurchase) {
        if (savedPurchase == null) return null;
        return new ApiPurchase(
                savedPurchase.getId(),
                savedPurchase.getFuelType(),
                savedPurchase.getPrice(),
                savedPurchase.getDriverId(),
                savedPurchase.getDate());
    }
}