package com.fuelcompany.infrastructure;

import com.fuelcompany.domain.aggregateModels.purchase.PurchaseEntity;
import com.fuelcompany.domain.aggregateModels.purchase.IReportRepository;
import com.fuelcompany.infrastructure.repository.IReportDefaultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportRepositoryImpl implements IReportRepository {

    @Autowired
    private IReportDefaultRepository baseRepository;

    @Override
    public PurchaseEntity save(PurchaseEntity report) {
        return baseRepository.save(report);
    }
}