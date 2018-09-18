package com.fuelcompany.infrastructure.api.registration;

import com.fuelcompany.domain.aggregateModels.purchase.PurchaseItem;

public class PurchaseTransformer {
    public static PurchaseItem toDomain(ApiPurchase apiPurchase) {
        return new PurchaseItem(
                apiPurchase.getFuelType(),
                apiPurchase.getPrice(),
                apiPurchase.getDriverId(),
                apiPurchase.getDate()
        );
    }

    public static ApiPurchase toREST(PurchaseItem savedPurchase) {
        if (savedPurchase == null) return null;
        return new ApiPurchase(
                savedPurchase.getId(),
                savedPurchase.getFuelType(),
                savedPurchase.getPrice(),
                savedPurchase.getDriverId(),
                savedPurchase.getDate());
    }
}