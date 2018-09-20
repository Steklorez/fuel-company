package com.fuelcompany.domain;

import com.fuelcompany.domain.aggregateModels.report.GroupByMonthItem;

import java.util.List;

public interface ReportService {

    List<GroupByMonthItem> getTotalByMonth(Long driverId);
}
