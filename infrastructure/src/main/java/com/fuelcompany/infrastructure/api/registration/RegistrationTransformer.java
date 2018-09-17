package com.fuelcompany.infrastructure.api.registration;

import com.fuelcompany.domain.entity.ReportEntity;

public class RegistrationTransformer {
    public static ReportEntity toDomain(Record record) {
        return new ReportEntity(
                record.getFuelType(),
                record.getPrice(),
                record.getDriverId(),
                record.getDate()
        );
    }

    public static Record toREST(ReportEntity registrated) {
        if (registrated == null) return null;
        return new Record(
                registrated.getId(),
                registrated.getFuelType(),
                registrated.getPrice(),
                registrated.getDriverId(),
                registrated.getDate());
    }
}