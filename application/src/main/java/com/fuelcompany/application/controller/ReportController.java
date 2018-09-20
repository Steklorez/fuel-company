package com.fuelcompany.application.controller;

import com.fuelcompany.domain.aggregateModels.report.Report;
import com.fuelcompany.domain.errors.DomainException;
import com.fuelcompany.infrastructure.api.reporting.ApiTotalGroupByMonth;
import com.fuelcompany.infrastructure.api.reporting.ReportTransformer;
import com.fuelcompany.infrastructure.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller with REST requests mapping
 * Registration incoming ApiPurchase
 */
@RestController
@RequestMapping("/reports/total")
@Transactional(readOnly = true)
public class ReportController {
    private static Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private Report reportService;

    @GetMapping(path = "/months", consumes = MediaType.TEXT_HTML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ApiTotalGroupByMonth> getTotalByMonth(@RequestParam(value = "driverId", required = false) Long driverId) {
        try {
            return ReportTransformer.toREST(reportService.getTotalByMonth(driverId));
        } catch (DomainException e) {
            logger.error("Domain exception", e);
            throw new ApiException(e);
        }
    }
}
