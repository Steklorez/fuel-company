package com.fuelcompany.infrastructure.api.reporting;

import com.fuelcompany.domain.aggregateModels.report.Month;
import com.fuelcompany.domain.aggregateModels.report.RecordByMonthItem;
import com.fuelcompany.domain.aggregateModels.report.entity.TotalByMonthEntity;
import org.springframework.stereotype.Component;

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
        return totalInMonth.stream().map(month -> new ApiReportByMonth(month.getYear(), collectRecordsInMonth(month.getRecords()))).collect(Collectors.toList());
    }

    private List<ApiReportByMonthRecord> collectRecordsInMonth(List<RecordByMonthItem> months) {
        return months
                .stream()
                .map(r -> new ApiReportByMonthRecord(r.getType(), r.getVolume(), r.getDate(), r.getPrice(), r.getTotalPrice().stripTrailingZeros(), r.getDriverId()))
                .collect(Collectors.toList());
    }
}
