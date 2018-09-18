package com.fuelcompany.domain.services;

import com.fuelcompany.domain.entity.FuelType;
import com.fuelcompany.domain.entity.PurchaseEntity;
import com.fuelcompany.domain.entity.repository.IReportRepository;
import com.fuelcompany.domain.error.DomainException;
import com.fuelcompany.domain.error.ErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * Registration new incoming record with fuel service
 */

@Service
public class PurchaseService {
    private static Logger logger = LoggerFactory.getLogger(PurchaseService.class);

    @Autowired
    private IReportRepository reportRepository;

    public PurchaseEntity save(PurchaseEntity report) throws DomainException {
        try {
            if (report.getDate() == null)
                throw new DomainException(ErrorMessages.DOMAIN_ERROR_1050);
            if (StringUtils.isEmpty(report.getFuelType()))
                throw new DomainException(ErrorMessages.DOMAIN_ERROR_E1051);
            if (report.getPrice() == null)
                throw new DomainException(ErrorMessages.DOMAIN_ERROR_E1052);
            if (report.getDriverId() == null)
                throw new DomainException(ErrorMessages.DOMAIN_ERROR_E1053);
            if (!FuelType.fuelTypeSet.contains(report.getFuelType()))
                throw new DomainException(ErrorMessages.DOMAIN_ERROR_E1054);

            return reportRepository.save(report);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Saving process error",e);
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_E9999);
        }
    }
}