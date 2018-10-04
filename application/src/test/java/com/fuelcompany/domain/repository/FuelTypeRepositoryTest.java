package com.fuelcompany.domain.repository;

import com.fuelcompany.application.SpringTestCase;
import com.fuelcompany.domain.aggregateModels.purchase.entity.FuelTypeEntity;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class FuelTypeRepositoryTest extends SpringTestCase {

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
        List<FuelTypeEntity> activeByName = fuelTypeRepository.findActiveByName(new HashSet<>());
        assertTrue(activeByName.isEmpty());
        activeByName = fuelTypeRepository.findActiveByName(Collections.singleton("qweretertrty"));
        assertTrue(activeByName.isEmpty());
        Set<String> fuelTypeNameSet = Sets.newSet("D", "98");
        activeByName = fuelTypeRepository.findActiveByName(fuelTypeNameSet);
        assertEquals(2, activeByName.size());
        boolean isAllFinded = activeByName.stream().map(FuelTypeEntity::getName).collect(Collectors.toSet()).containsAll(fuelTypeNameSet);
        assertTrue(isAllFinded);
    }
}