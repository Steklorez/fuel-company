package com.fuelcompany.infrastructure;

import com.fuelcompany.domain.entity.ReportEntity;
import com.fuelcompany.domain.entity.repository.IReportRepository;
import com.fuelcompany.infrastructure.repository.IReportDefaultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportRepositoryImpl implements IReportRepository {

    @Autowired
    private IReportDefaultRepository baseRepository;

    @Override
    public ReportEntity save(ReportEntity report) {
        return baseRepository.save(report);
    }
}