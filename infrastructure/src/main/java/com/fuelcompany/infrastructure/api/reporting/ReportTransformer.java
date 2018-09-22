package com.fuelcompany.infrastructure.api.reporting;

import com.fuelcompany.domain.aggregateModels.report.FuelConsumption;
import com.fuelcompany.domain.aggregateModels.report.FuelConsumptionFuelType;
import com.fuelcompany.domain.aggregateModels.report.Month;
import com.fuelcompany.domain.aggregateModels.report.RecordByMonthItem;
import com.fuelcompany.domain.aggregateModels.report.entity.TotalByMonthEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportTransformer {

    public List<ApiTotalGroupByMonth> toREST(List<TotalByMonthEntity> total) {
        return total.stream().map(this::toREST).collect(Collectors.toList());
    }

    private ApiTotalGroupByMonth toREST(TotalByMonthEntity item) {
        return new ApiTotalGroupByMonth(item.getYear(), item.getMonth(), item.getTotal().stripTrailingZeros());
    }

    public List<ApiReportByMonth> toRESTByMonth(List<Month> totalInMonth) {
        if (totalInMonth == null) return Collections.emptyList();
        return totalInMonth.stream()
                .map(month ->
                        new ApiReportByMonth(month.getYear(), collectRecordsInMonth(month.getRecords())))
                .collect(Collectors.toList());
    }

    private List<ApiReportByMonthRecord> collectRecordsInMonth(List<RecordByMonthItem> months) {
        if (months == null) return Collections.emptyList();
        return months.stream()
                .map(r ->
                        new ApiReportByMonthRecord(r.getType(), r.getVolume(), r.getDate(), r.getPrice(), r.getTotalPrice().stripTrailingZeros(), r.getDriverId()))
                .collect(Collectors.toList());
    }

    public List<ApiFuelConsumption> toRESTFuelConsumption(List<FuelConsumption> fuelConsumption) {
        if (fuelConsumption == null) return Collections.emptyList();
        return fuelConsumption.stream()
                .map(f ->
                        new ApiFuelConsumption(f.getYear(), f.getMonth(), getFuelConsumptions(f.getFuelTypes())))
                .collect(Collectors.toList());
    }

    private List<ApiFuelConsumptionRecord> getFuelConsumptions(List<FuelConsumptionFuelType> list) {
        return list.stream()
                .map(r ->
                        new ApiFuelConsumptionRecord(r.getFuelType(), r.getVolume(), r.getAveragePrice(), r.getTotalPrice()))
                .collect(Collectors.toList());
    }

    private List<ApiFuelConsumptionRecord> getFuelConsumptionsFuelTypes(List<FuelConsumptionFuelType> fuelTypes) {
        if (fuelTypes == null) return Collections.emptyList();
        return fuelTypes.stream()
                .map(fuelType ->
                        new ApiFuelConsumptionRecord(fuelType.getFuelType(), fuelType.getVolume(), fuelType.getAveragePrice(), fuelType.getTotalPrice()))
                .collect(Collectors.toList());
    }
}
