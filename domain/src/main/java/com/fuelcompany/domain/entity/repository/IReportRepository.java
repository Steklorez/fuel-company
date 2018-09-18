package com.fuelcompany.domain.entity.repository;

import com.fuelcompany.domain.entity.PurchaseEntity;

public interface IReportRepository {

    PurchaseEntity save(PurchaseEntity report);
}
