package com.fuelcompany.domain;

import com.fuelcompany.domain.aggregateModels.report.FuelConsumption;
import com.fuelcompany.domain.aggregateModels.report.Month;
import com.fuelcompany.domain.aggregateModels.report.entity.TotalByMonthEntity;

import java.util.List;

public interface ReportService {

    List<TotalByMonthEntity> getAmountByMonths(Long driverId);

    List<Month> getReportByMonth(int numberOfMonth, Long driverId, Integer year);

    List<FuelConsumption> getFuelConsumption(Long driverId, Integer year);
}
