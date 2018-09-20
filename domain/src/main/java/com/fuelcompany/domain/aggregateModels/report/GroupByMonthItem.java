package com.fuelcompany.domain.aggregateModels.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupByMonthItem {
    private String year;

    @Setter
    private String month;
    private BigDecimal total;
}
