package com.fuelcompany.domain.aggregateModels.purchase;

import com.fuelcompany.domain.PurchaseService;
import com.fuelcompany.domain.dao.IPurchaseDAO;
import com.fuelcompany.domain.entity.PurchaseEntity;
import com.fuelcompany.domain.errors.DomainException;
import com.fuelcompany.domain.errors.ErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Aggregate root element - Save incoming PurchaseItem records
 */

@Service
public class Purchase implements PurchaseService {
    private static Logger logger = LoggerFactory.getLogger(Purchase.class);

    @Autowired
    private IPurchaseDAO purchaseRepository;

    @Override
    public PurchaseItem save(PurchaseItem purchaseItem) throws DomainException {
        try {
            validateFields(purchaseItem);
            return buildDomainModel(purchaseRepository.save(buildEntity(purchaseItem)));
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Saving process error", e);
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_E9999);
        }
    }

    @Override
    public List<PurchaseItem> save(List<PurchaseItem> purchaseList) throws DomainException {
        try {
            if (purchaseList.isEmpty())
                throw new DomainException(ErrorMessages.DOMAIN_ERROR_E1050);

            if (purchaseList.size() > 5000)
                throw new DomainException(ErrorMessages.DOMAIN_ERROR_E1051);

            purchaseList.forEach(this::validateFields);
            return purchaseList.stream()
                    .map(purch -> buildDomainModel(purchaseRepository.save(buildEntity(purch))))
                    .collect(Collectors.toList());

        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Saving process error", e);
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_E9999);
        }
    }

    private void validateFields(PurchaseItem purchaseItem) {
        if (purchaseItem.getDate() == null)
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_1001);
        if (StringUtils.isEmpty(purchaseItem.getFuelType()))
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_1002);
        if (purchaseItem.getPrice() == null)
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_1003);
        if (purchaseItem.getDriverId() == null)
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_1004);
        if (!FuelTypeItem.fuelTypeSet.contains(purchaseItem.getFuelType()))
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_1005);
    }

    private PurchaseEntity buildEntity(PurchaseItem item) {
        return new PurchaseEntity(item.getFuelType(), item.getPrice(), item.getDriverId(), item.getDate());
    }

    private PurchaseItem buildDomainModel(PurchaseEntity entity) {
        return new PurchaseItem(entity.getId(), entity.getFuelType(), entity.getPrice(), entity.getDriverId(), entity.getDate());
    }
}