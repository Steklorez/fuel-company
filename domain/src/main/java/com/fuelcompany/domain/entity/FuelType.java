package com.fuelcompany.domain.entity;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum FuelType {
    DIESEL("D"),
    GASOLINE_95("95"),
    GASOLINE_98("98");

    private final String name;
    public static final Set<String> fuelTypeSet = Arrays.stream(FuelType.values()).map(FuelType::getName).collect(Collectors.toSet());

    FuelType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
