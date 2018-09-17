package com.fuelcompany.domain.services;

import com.fuelcompany.domain.entity.FuelType;
import com.fuelcompany.domain.entity.ReportEntity;
import com.fuelcompany.domain.entity.repository.IReportRepository;
import com.fuelcompany.domain.error.DomainException;
import com.fuelcompany.domain.error.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class RegistrationService {

    @Autowired
    private IReportRepository reportRepository;

    public ReportEntity registrate(ReportEntity report) throws DomainException {
        if (report.getDate() == null)
            throw new DomainException(ErrorMessages.E1050);
        if (StringUtils.isEmpty(report.getFuelType()))
            throw new DomainException(ErrorMessages.E1051);
        if (report.getPrice() == null)
            throw new DomainException(ErrorMessages.E1052);
        if (report.getDriverId() == null)
            throw new DomainException(ErrorMessages.E1053);
        if (!FuelType.fuelTypeSet.contains(report.getFuelType()))
            throw new DomainException(ErrorMessages.E1054);
        return reportRepository.save(report);
    }
}