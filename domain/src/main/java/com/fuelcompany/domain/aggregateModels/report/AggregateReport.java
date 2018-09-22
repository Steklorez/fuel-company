package com.fuelcompany.domain.aggregateModels.report;

import com.fuelcompany.domain.IPurchaseDAO;
import com.fuelcompany.domain.ReportService;
import com.fuelcompany.domain.aggregateModels.report.entity.FuelConsumptionEntity;
import com.fuelcompany.domain.aggregateModels.report.entity.RecordByMonthEntity;
import com.fuelcompany.domain.aggregateModels.report.entity.TotalByMonthEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private String getMonthName(String number) {
        return java.time.Month.of(Integer.valueOf(number)).name();
    }

    @Override
    public List<Month> getReportByMonth(int numberOfMonth, Long driverId, Integer year) {
        List<RecordByMonthEntity> reportByMonth = purchaseDAO.getReportByMonth(numberOfMonth, driverId, year);
        Map<Integer, List<RecordByMonthEntity>> yearsMap = reportByMonth.stream().collect(Collectors.groupingBy(RecordByMonthEntity::getYear));

        return yearsMap.keySet().stream()
                .map(yearKey ->
                        new Month(yearKey, collectAllMonthsRecords(yearsMap.get(yearKey))))
                .sorted((o1, o2) -> o2.getYear().compareTo(o1.getYear()))
                .collect(Collectors.toList());
    }

    private List<RecordByMonthItem> collectAllMonthsRecords(List<RecordByMonthEntity> entityList) {
        return entityList.stream()
                .map(record ->
                        new RecordByMonthItem(record.getType(), record.getVolume(), record.getDate(), record.getPrice(), record.getTotalPrice(), record.getDriverId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FuelConsumption> getFuelConsumption(Long driverId, Integer year) {
        List<FuelConsumptionEntity> records = purchaseDAO.getFuelConsumption(driverId, year);
        Map<String, List<FuelConsumptionEntity>> yearsMonth = records.stream().collect(Collectors.groupingBy(r -> r.getYear() + ":" + r.getMonth()));
        List<FuelConsumption> result = new ArrayList<>();

        for (Map.Entry<String, List<FuelConsumptionEntity>> entry : yearsMonth.entrySet()) {
            List<FuelConsumptionFuelType> resultRecordList = entry.getValue().stream()
                    .map(e ->
                            new FuelConsumptionFuelType(e.getType(), e.getVolume(), e.getAveragePrice(), e.getTotalPrice()))
                    .collect(Collectors.toList());

            String[] yearMonth = entry.getKey().split(":");
            result.add(new FuelConsumption(Integer.valueOf(yearMonth[0]), yearMonth[1], resultRecordList));
        }
        result.sort(Comparator.comparing(FuelConsumption::getYear).reversed().thenComparing(FuelConsumption::getMonth));
        return result;
    }
}
