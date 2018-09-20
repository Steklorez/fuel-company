package com.fuelcompany.infrastructure.api.reporting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiTotalGroupByMonth {
    private String month;
    private BigDecimal total;
}
