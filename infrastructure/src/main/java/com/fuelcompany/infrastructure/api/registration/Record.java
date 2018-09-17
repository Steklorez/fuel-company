package com.fuelcompany.infrastructure.api.registration;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    private Long id;
    private String fuelType;
    private BigDecimal price;
    private Long driverId;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
}