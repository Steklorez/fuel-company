package com.fuelcompany.application;

import com.fuelcompany.domain.dao.IPurchaseDAO;
import com.fuelcompany.domain.dao.PurchaseEntity;
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
        double mayVolume1 = 5.0;
        double mayVolume2 = 10.0;
        double mayVolume3 = 20.5;
        double mayVolume4 = 40.5;
        int price1 = 20, price2 = 20;
        int price3 = 10, price4 = 10;
        purchaseDAO.save(new PurchaseEntity("D", new BigDecimal(mayVolume1), new BigDecimal(price1), 1L, LocalDate.of(2017, 5, 1)));
        purchaseDAO.save(new PurchaseEntity("98", new BigDecimal(mayVolume2), new BigDecimal(price2), 1L, LocalDate.of(2017, 5, 20)));
        purchaseDAO.save(new PurchaseEntity("D", new BigDecimal(mayVolume3), new BigDecimal(price3), 2L, LocalDate.of(2017, 5, 3)));
        purchaseDAO.save(new PurchaseEntity("D", new BigDecimal(mayVolume4), new BigDecimal(price4), 2L, LocalDate.of(2017, 6, 20)));


        //test for total spent amount of money grouped by month
        //calculate total by two months in one year
        this.mockMvc.perform(get("/reports/total/amount"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("[0].year").value(2017))
                .andExpect(jsonPath("[0].month").value("MAY"))
                .andExpect(jsonPath("[0].total").value(mayVolume1 * price1 + mayVolume2 * price2 + mayVolume3 * price3))
                .andExpect(jsonPath("[1].month").value("JUNE"))
                .andExpect(jsonPath("[1].total").value(mayVolume4 * price4))
                .andDo(print());

        //test for total spent amount of money grouped by month
        //calculate total by driverId in one year
        this.mockMvc.perform(get("/reports/total/amount?driverId=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("[0].year").value(2017))
                .andExpect(jsonPath("[0].month").value("MAY"))
                .andExpect(jsonPath("[0].total").value(mayVolume1 * price1 + mayVolume2 * price2))
                .andExpect(jsonPath("[1].month").doesNotHaveJsonPath())
                .andExpect(jsonPath("[1].total").doesNotHaveJsonPath())
                .andDo(print());

        //list fuel consumption records for specified month (each row should contain: fuel type, volume, date, price, total price, driver ID)
        //get not exist month
        this.mockMvc.perform(get("/reports/total/months/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("[0].year").doesNotHaveJsonPath())
                .andDo(print());

        //list fuel consumption records for specified month (each row should contain: fuel type, volume, date, price, total price, driver ID)
        //get total by 5 month
        this.mockMvc.perform(get("/reports/total/months/5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("[0].year").value(2017))
                .andExpect(jsonPath("[0].records").isArray())
                .andExpect(jsonPath("[0].records[0].type").isNotEmpty())
                .andExpect(jsonPath("[0].records[0].volume").isNotEmpty())
                .andExpect(jsonPath("[0].records[0].date").isNotEmpty())
                .andExpect(jsonPath("[0].records[0].price").isNotEmpty())
                .andExpect(jsonPath("[0].records[0].totalPrice").isNotEmpty())
                .andExpect(jsonPath("[0].records[0].driverId").isNotEmpty())
                .andDo(print());

        //list fuel consumption records for specified month (each row should contain: fuel type, volume, date, price, total price, driver ID)
        // not exist month with real driverId
        this.mockMvc.perform(get("/reports/total/months/3?driverId=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("[0].year").doesNotHaveJsonPath());

        //list fuel consumption records for specified month (each row should contain: fuel type, volume, date, price, total price, driver ID)
        //get total by 5 month driverId=2
        this.mockMvc.perform(get("/reports/total/months/6?driverId=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("[0].year").value(2017))
                .andExpect(jsonPath("[0].records").isArray())
                .andExpect(jsonPath("[0].records[0].type").value("D"))
                .andExpect(jsonPath("[0].records[0].volume").value(40.5))
                .andExpect(jsonPath("[0].records[0].date").value("2017-06-20"))
                .andExpect(jsonPath("[0].records[0].price").value(10.0))
                .andExpect(jsonPath("[0].records[0].totalPrice").value(405))
                .andExpect(jsonPath("[0].records[0].driverId").value(2))
                .andExpect(jsonPath("[0].records[1].type").doesNotExist())
                .andExpect(jsonPath("[1].year").doesNotExist())
                .andDo(print());

        //list fuel consumption records for specified month (each row should contain: fuel type, volume, date, price, total price, driver ID)
        //check group by years with driverId=3
        int year2018 = 2018;
        int year2017 = 2017;
        purchaseDAO.save(new PurchaseEntity("D", new BigDecimal(40.5), new BigDecimal(10), 3L, LocalDate.of(year2017, 8, 20)));
        purchaseDAO.save(new PurchaseEntity("D", new BigDecimal(40.5), new BigDecimal(10), 3L, LocalDate.of(year2018, 8, 20)));
        this.mockMvc.perform(get("/reports/total/months/8?driverId=3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("[0].year").value(year2018))
                .andExpect(jsonPath("[0].records").isArray())
                .andExpect(jsonPath("[1].year").value(year2017))
                .andExpect(jsonPath("[1].records").isArray())
                .andDo(print());

    }
}