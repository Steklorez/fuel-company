package com.fuelcompany.domain.dao;

import com.fuelcompany.domain.aggregateModels.report.GroupByMonthItem;
import com.fuelcompany.domain.entity.PurchaseEntity;

import java.util.List;

public interface IPurchaseDAO {

    PurchaseEntity save(PurchaseEntity report);

    List<GroupByMonthItem> getTotalByMonth(Long driverId);
}
