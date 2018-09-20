package com.fuelcompany.domain.aggregateModels.purchase;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum FuelTypeItem {
    DIESEL("D"),
    GASOLINE_95("95"),
    GASOLINE_98("98");

    private final String name;
    public static final Set<String> fuelTypeSet = Arrays.stream(FuelTypeItem.values()).map(FuelTypeItem::getName).collect(Collectors.toSet());

    FuelTypeItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
