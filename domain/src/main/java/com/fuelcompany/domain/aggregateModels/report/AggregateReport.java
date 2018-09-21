package com.fuelcompany.domain.aggregateModels.report;

import com.fuelcompany.domain.ReportService;
import com.fuelcompany.domain.aggregateModels.report.entity.RecordByMonthEntity;
import com.fuelcompany.domain.aggregateModels.report.entity.TotalByMonthEntity;
import com.fuelcompany.domain.dao.IPurchaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AggregateReport implements ReportService {

    @Autowired
    private IPurchaseDAO purchaseDAO;

    @Override
    public List<TotalByMonthEntity> getAmountByMonths(Long driverId) {
        List<TotalByMonthEntity> totalByMonth = purchaseDAO.getAmountByMonths(driverId);
        for (TotalByMonthEntity item : totalByMonth) {
            item.setMonth(getMonthName(item.getMonth()));
        }
        return totalByMonth;
    }

    @Override
    public List<Month> getReportByMonth(int numberOfMonth, Long driverId, Integer year) {
        List<RecordByMonthEntity> reportByMonth = purchaseDAO.getReportByMonth(numberOfMonth, driverId, year);
        Map<Integer, List<RecordByMonthEntity>> yearsMap = reportByMonth.stream().collect(Collectors.groupingBy(RecordByMonthEntity::getYear));

        List<Month> months = yearsMap.keySet().stream()
                .map(yearKey -> new Month(yearKey, collectAllMonthsRecords(yearsMap.get(yearKey))))
                .sorted((o1, o2) -> o2.getYear().compareTo(o1.getYear()))
                .collect(Collectors.toList());
        return months;
    }

    private List<RecordByMonthItem> collectAllMonthsRecords(List<RecordByMonthEntity> entityList) {
        return entityList.stream()
                .map(record -> new RecordByMonthItem(record.getType(), record.getVolume(), record.getDate(), record.getPrice(), record.getTotalPrice(), record.getDriverId()))
                .collect(Collectors.toList());
    }

    private String getMonthName(String number) {
        return java.time.Month.of(Integer.valueOf(number)).name();
    }
}
