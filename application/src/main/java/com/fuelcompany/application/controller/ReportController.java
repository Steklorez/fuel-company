package com.fuelcompany.application.controller;

import com.fuelcompany.domain.ReportService;
import com.fuelcompany.domain.errors.DomainException;
import com.fuelcompany.infrastructure.api.reporting.ApiReportByMonth;
import com.fuelcompany.infrastructure.api.reporting.ApiTotalGroupByMonth;
import com.fuelcompany.infrastructure.api.reporting.ReportTransformer;
import com.fuelcompany.infrastructure.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private ReportService reportService;
    @Autowired
    private ReportTransformer transformer;

    @GetMapping(path = "/amount")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiTotalGroupByMonth> getAmountByMonths(@RequestParam(value = "driverId", required = false) Long driverId) {
        try {
            return transformer.toREST(reportService.getAmountByMonths(driverId));
        } catch (DomainException e) {
            logger.error("Domain exception", e);
            throw new ApiException(e);
        }
    }

    @GetMapping(path = "/months/{monthsNumber}")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiReportByMonth> getReportByMonth(@RequestParam(value = "driverId", required = false) Long driverId,
                                                  @RequestParam(value = "year", required = false) Integer year,
                                                  @PathVariable int monthsNumber) {
        try {
            return transformer.toRESTByMonth(reportService.getReportByMonth(monthsNumber, driverId, year));
        } catch (DomainException e) {
            logger.error("Domain exception", e);
            throw new ApiException(e);
        }
    }
}
