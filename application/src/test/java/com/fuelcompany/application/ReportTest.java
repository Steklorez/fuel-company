package com.fuelcompany.application;

import com.fuelcompany.domain.dao.IPurchaseDAO;
import com.fuelcompany.domain.entity.PurchaseEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReportTest extends SpringTestContainer {

    @Autowired
    private IPurchaseDAO purchaseDAO;

    @Test
    @Transactional
    @Rollback
    public void getTotalByMonthAll() throws Exception {
        purchaseDAO.save(new PurchaseEntity("D", new BigDecimal(20), 1L, LocalDate.of(2017, 5, 1)));
        purchaseDAO.save(new PurchaseEntity("98", new BigDecimal(20), 1L, LocalDate.of(2017, 5,20)));
        purchaseDAO.save(new PurchaseEntity("D", new BigDecimal(10), 2L, LocalDate.of(2017, 5,3)));
        purchaseDAO.save(new PurchaseEntity("D", new BigDecimal(10), 2L, LocalDate.of(2017, 6,20)));

        this.mockMvc.perform(get("/reports/total/months")
                .contentType(MediaType.TEXT_HTML_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("[0].month").value("MAY"))
                .andExpect(jsonPath("[0].total").value(50))
                .andExpect(jsonPath("[1].month").value("JUNE"))
                .andExpect(jsonPath("[1].total").value(10))
                .andDo(print());

        this.mockMvc.perform(get("/reports/total/months?driverId=1")
                .contentType(MediaType.TEXT_HTML_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("[0].month").value("MAY"))
                .andExpect(jsonPath("[0].total").value(40))
                .andExpect(jsonPath("[1].month").doesNotHaveJsonPath())
                .andExpect(jsonPath("[1].total").doesNotHaveJsonPath())
                .andDo(print());
    }
}