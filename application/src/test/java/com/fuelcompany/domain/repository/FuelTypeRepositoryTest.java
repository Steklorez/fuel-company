package com.fuelcompany.domain.repository;

import com.fuelcompany.application.SpringTestContainer;
import com.fuelcompany.domain.aggregateModels.purchase.entity.FuelTypeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FuelTypeRepositoryTest extends SpringTestContainer {

    @Autowired
    private FuelTypeRepository fuelTypeRepository;

    @Test
    public void findActiveByName() {
        final String fuelType = "D";
        Optional<FuelTypeEntity> activeByName = fuelTypeRepository.findActiveByName("");
        assertFalse(activeByName.isPresent());
        activeByName = fuelTypeRepository.findActiveByName("qweretertrty");
        assertFalse(activeByName.isPresent());
        activeByName = fuelTypeRepository.findActiveByName(fuelType);
        assertTrue(activeByName.isPresent());
        assertEquals(fuelType, activeByName.get().getName());
    }

    @Test
    public void findActiveByNameList() {
//        fuelTypeRepository.findActiveByName()
    }
}