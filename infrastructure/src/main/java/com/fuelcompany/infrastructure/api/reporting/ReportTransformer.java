package com.fuelcompany.infrastructure.api.reporting;

import com.fuelcompany.domain.aggregateModels.report.GroupByMonthItem;

import java.util.List;
import java.util.stream.Collectors;

public class ReportTransformer {

    public static List<ApiTotalGroupByMonth> toREST(List<GroupByMonthItem> total) {
        return total.stream().map(ReportTransformer::toREST).collect(Collectors.toList());
    }

    private static ApiTotalGroupByMonth toREST(GroupByMonthItem item) {
        return new ApiTotalGroupByMonth(item.getMonth(), item.getTotal());
    }
}
