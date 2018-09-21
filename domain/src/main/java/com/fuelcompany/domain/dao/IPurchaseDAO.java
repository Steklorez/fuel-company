package com.fuelcompany.domain.dao;

import com.fuelcompany.domain.aggregateModels.report.entity.RecordByMonthEntity;
import com.fuelcompany.domain.aggregateModels.report.entity.TotalByMonthEntity;

import java.util.List;

public interface IPurchaseDAO {

    PurchaseEntity save(PurchaseEntity report);

    List<TotalByMonthEntity> getAmountByMonths(Long driverId);

    List<RecordByMonthEntity> getReportByMonth(int monthsNumber, Long driverId, Integer year);
}
