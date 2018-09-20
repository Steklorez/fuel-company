package com.fuelcompany.domain.aggregateModels.report;

import com.fuelcompany.domain.ReportService;
import com.fuelcompany.domain.dao.IPurchaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;

@Service
public class Report implements ReportService {

    @Autowired
    private IPurchaseDAO purchaseDAO;

    @Override
    public List<GroupByMonthItem> getTotalByMonth(Long driverId) {
        List<GroupByMonthItem> totalByMonth = purchaseDAO.getTotalByMonth(driverId);
        for (GroupByMonthItem item : totalByMonth) {
            item.setMonth(getMonthName(item.getMonth()));
        }
        return totalByMonth;
    }

    private String getMonthName(String number) {
        return Month.of(Integer.valueOf(number)).name();
    }
}
