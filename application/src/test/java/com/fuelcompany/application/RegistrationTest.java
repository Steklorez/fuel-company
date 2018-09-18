package com.fuelcompany.application;


import com.fuelcompany.infrastructure.api.registration.PurchaseResponse;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



public class RegistrationTest extends SpringTestContainer {

    @Test
    public void registrationTest() throws Exception {
        this.mockMvc.perform(post("/purchases?date=09-09-2017&fuelType=D&price=3.25&driverId=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("purchase.id").value(1))
                .andExpect(jsonPath("purchase.fuelType").value("D"))
                .andExpect(jsonPath("purchase.price").value(3.25))
                .andExpect(jsonPath("purchase.driverId").value(1))
                .andExpect(jsonPath("purchase.date").value("2017-09-09"))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void registrationErrors_1050_Test() throws Exception {
        this.mockMvc.perform(post("/purchases").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("error.status").value(422))
                .andExpect(jsonPath("error.errorCode").value(1050))
                .andExpect(jsonPath("error.message").value("Field 'date' is empty"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void registrationErrors_1051_Test() throws Exception {
        this.mockMvc.perform(post("/purchases").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("error.status").value(422))
                .andExpect(jsonPath("error.errorCode").value(1050))
                .andExpect(jsonPath("error.message").value("Field 'date' is empty"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void registrationErrors_1052_Test() throws Exception {
        this.mockMvc.perform(post("/purchases").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("error.status").value(422))
                .andExpect(jsonPath("error.errorCode").value(1050))
                .andExpect(jsonPath("error.message").value("Field 'date' is empty"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void registrationErrors_1053_Test() throws Exception {
        this.mockMvc.perform(post("/purchases").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("error.status").value(422))
                .andExpect(jsonPath("error.errorCode").value(1050))
                .andExpect(jsonPath("error.message").value("Field 'date' is empty"))
                .andDo(MockMvcResultHandlers.print());
    }







    /*        Record record = new Record();
            assertError(reportController.registrate(record), 1050, "Field 'date' is empty");
            record.setDate(LocalDate.now());
            assertError(reportController.registrate(record), 1051, "Field 'fuelType' is empty");
            record.setFuelType("D-failed-fuel-type");
            assertError(reportController.registrate(record), 1052, "Field 'price' is empty");
            record.setPrice(new BigDecimal("3.27"));
            assertError(reportController.registrate(record), 1053, "Field 'driverId' is empty");
            record.setDriverId(1L);
            assertError(reportController.registrate(record), 1054, "Wrong fuel type");*/
    private void assertError(PurchaseResponse registrateResult, int code, String messge) {
        Assert.assertNotNull(registrateResult);
        Assert.assertNull(registrateResult.getPurchase());
        Assert.assertNotNull(registrateResult.getError());
        Assert.assertEquals(code, registrateResult.getError().getCode());
        Assert.assertEquals(messge, registrateResult.getError().getMessage());
    }
}
