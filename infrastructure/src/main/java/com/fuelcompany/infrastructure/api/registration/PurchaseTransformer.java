package com.fuelcompany.infrastructure.api.registration;

import com.fuelcompany.domain.aggregateModels.purchase.PurchaseItem;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseTransformer {
    public static PurchaseItem toDomain(ApiPurchase request) {
        return new PurchaseItem(
                request.getFuelType(),
                request.getPrice(),
                request.getDriverId(),
                request.getDate()
        );
    }

    public static ApiPurchase toREST(PurchaseItem savedPurchase) {
        return new ApiPurchase(
                savedPurchase.getId(),
                savedPurchase.getFuelType(),
                savedPurchase.getPrice(),
                savedPurchase.getDriverId(),
                savedPurchase.getDate());
    }

    public static List<PurchaseItem> toDomain(List<ApiPurchase> apiPurchaseList) {
        return apiPurchaseList.stream().map(PurchaseTransformer::toDomain).collect(Collectors.toList());
    }

    public static List<ApiPurchase> toREST(List<PurchaseItem> purchaseItemList) {
        return purchaseItemList.stream().map(PurchaseTransformer::toREST).collect(Collectors.toList());
    }
}