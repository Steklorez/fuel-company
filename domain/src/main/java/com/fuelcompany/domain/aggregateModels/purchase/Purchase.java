package com.fuelcompany.domain.aggregateModels.purchase;

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
public class Purchase {
    private static Logger logger = LoggerFactory.getLogger(Purchase.class);

    @Autowired
    private IReportRepository reportRepository;

    public PurchaseItem save(PurchaseItem purchase) throws DomainException {
        try {
            if (purchase.getDate() == null)
                throw new DomainException(ErrorMessages.DOMAIN_ERROR_1050);
            if (StringUtils.isEmpty(purchase.getFuelType()))
                throw new DomainException(ErrorMessages.DOMAIN_ERROR_E1051);
            if (purchase.getPrice() == null)
                throw new DomainException(ErrorMessages.DOMAIN_ERROR_E1052);
            if (purchase.getDriverId() == null)
                throw new DomainException(ErrorMessages.DOMAIN_ERROR_E1053);
            if (!FuelType.fuelTypeSet.contains(purchase.getFuelType()))
                throw new DomainException(ErrorMessages.DOMAIN_ERROR_E1054);

            return reportRepository.save(PurchaseEntity.buildEntity(purchase)).buildDomainModel();
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Saving process error",e);
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_E9999);
        }
    }
}