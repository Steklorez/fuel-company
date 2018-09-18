package com.fuelcompany.domain.aggregateModels.purchase;

public interface IReportRepository {

    PurchaseEntity save(PurchaseEntity report);
}
