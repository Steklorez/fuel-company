package com.fuelcompany.infrastructure.api.registration;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiPurchase {
    private Long id;
    private String fuelType;
    private BigDecimal price;
    private Long driverId;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    public ApiPurchase(String fuelType, BigDecimal price, Long driverId, LocalDate date) {
        this.fuelType = fuelType;
        this.price = price;
        this.driverId = driverId;
        this.date = date;
    }
}