package com.fuelcompany.domain.aggregateModels.purchase;

import com.fuelcompany.domain.IPurchaseDAO;
import com.fuelcompany.domain.PurchaseService;
import com.fuelcompany.domain.aggregateModels.purchase.entity.PurchaseEntity;
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
public class AggregatePurchase implements PurchaseService {
    private static Logger logger = LoggerFactory.getLogger(AggregatePurchase.class);

    @Autowired
    private IPurchaseDAO purchaseRepository;

    @Override
    public Purchase save(Purchase purchase) throws DomainException {
        try {
            validateFields(purchase);
            return buildDomainModel(purchaseRepository.save(buildEntity(purchase)));
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Saving process error", e);
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_E9999);
        }
    }

    @Override
    public List<Purchase> save(List<Purchase> purchaseList) throws DomainException {
        try {
            if (purchaseList.isEmpty())
                throw new DomainException(ErrorMessages.DOMAIN_ERROR_E1050);

            if (purchaseList.size() > 5000)
                throw new DomainException(ErrorMessages.DOMAIN_ERROR_E1051);

            purchaseList.forEach(this::validateFields);
            return purchaseList.stream()
                    .map(purchase -> buildDomainModel(purchaseRepository.save(buildEntity(purchase))))
                    .collect(Collectors.toList());

        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Saving process error", e);
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_E9999);
        }
    }

    private void validateFields(Purchase purchase) {
        if (purchase.getDate() == null)
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_1001);
        if (StringUtils.isEmpty(purchase.getFuelType()))
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_1002);
        if (purchase.getPrice() == null)
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_1003);
        if (purchase.getDriverId() == null)
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_1004);
        if (purchase.getVolume() == null)
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_1006);
        if (!FuelTypeItem.fuelTypeSet.contains(purchase.getFuelType()))
            throw new DomainException(ErrorMessages.DOMAIN_ERROR_1005);
    }

    private PurchaseEntity buildEntity(Purchase item) {
        return new PurchaseEntity(item.getFuelType(), item.getVolume(), item.getPrice(), item.getDriverId(), item.getDate());
    }

    private Purchase buildDomainModel(PurchaseEntity entity) {
        return new Purchase(entity.getId(), entity.getVolume(), entity.getFuelType(), entity.getPrice(), entity.getDriverId(), entity.getDate());
    }
}