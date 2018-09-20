package com.fuelcompany.domain;

import com.fuelcompany.domain.aggregateModels.purchase.PurchaseItem;
import com.fuelcompany.domain.errors.DomainException;

import java.util.List;

public interface PurchaseService {

    PurchaseItem save(PurchaseItem purchaseItem) throws DomainException;

    List<PurchaseItem> save(List<PurchaseItem> purchaseList) throws DomainException;
}
