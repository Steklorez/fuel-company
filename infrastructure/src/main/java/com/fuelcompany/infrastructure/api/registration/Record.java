package com.fuelcompany.infrastructure.api.registration;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    private Long id;
    private String fuelType;
    private BigDecimal price;
    private Long driverId;
    private LocalDateTime date;
}