package com.fuelcompany.infrastructure.api.reporting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 *  total spent amount of money grouped by month
 */

@Getter
@AllArgsConstructor
public class ApiTotalGroupByMonth {
    private int year;
    private String month;
    private BigDecimal total;
}
