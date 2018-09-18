package com.fuelcompany.domain.aggregateModels.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItem {
    private Long id;
    private String fuelType;
    private BigDecimal price;
    private Long driverId;
    private LocalDate date;

    public PurchaseItem(String fuelType, BigDecimal price, Long driverId, LocalDate date) {
        this.fuelType = fuelType;
        this.price = price;
        this.driverId = driverId;
        this.date = date;
    }
}
