package com.fuelcompany.application;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuelcompany.infrastructure.api.registration.ApiPurchase;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class RegistrationTest extends SpringTestContainer {

    @Test
    public void registrationTest() throws Exception {
        ApiPurchase purchase = new ApiPurchase("D", new BigDecimal("3.25"), 1L, LocalDate.of(2000, 10, 19));
        String body = (new ObjectMapper()).valueToTree(purchase).toString();
        this.mockMvc.perform(post("/purchases")
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("fuelType").value("D"))
                .andExpect(jsonPath("price").value(3.25))
                .andExpect(jsonPath("driverId").value(1))
                .andExpect(jsonPath("date").value("2000-10-19"))
                .andDo(print());
    }


    @Test
    public void registrationErrors_1001_Test() throws Exception {
        ApiPurchase purchase = new ApiPurchase("D", new BigDecimal("3.25"), 1L, null);
        String body = (new ObjectMapper()).valueToTree(purchase).toString();
        this.mockMvc.perform(post("/purchases")
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(jsonPath("error.status").value(422))
                .andExpect(jsonPath("error.errorCode").value(1001))
                .andExpect(jsonPath("error.message").value("Field 'date' is empty"))
                .andDo(print());
    }

    @Test
    public void registrationErrors_1002_Test() throws Exception {
        ApiPurchase purchase = new ApiPurchase(null, new BigDecimal("3.25"), 1L, LocalDate.of(2000, 10, 19));
        String body = (new ObjectMapper()).valueToTree(purchase).toString();
        this.mockMvc.perform(post("/purchases")
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(jsonPath("error.status").value(422))
                .andExpect(jsonPath("error.errorCode").value(1002))
                .andExpect(jsonPath("error.message").value("Field 'fuelType' is empty"))
                .andDo(print());
    }

    @Test
    public void registrationErrors_1003_Test() throws Exception {
        ApiPurchase purchase = new ApiPurchase("D", null, 1L, LocalDate.of(2000, 10, 19));
        String body = (new ObjectMapper()).valueToTree(purchase).toString();
        this.mockMvc.perform(post("/purchases")
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(jsonPath("error.status").value(422))
                .andExpect(jsonPath("error.errorCode").value(1003))
                .andExpect(jsonPath("error.message").value("Field 'price' is empty"))
                .andDo(print());
    }

    @Test
    public void registrationErrors_1004_Test() throws Exception {
        ApiPurchase purchase = new ApiPurchase("D", new BigDecimal("3.25"), null, LocalDate.of(2000, 10, 19));
        String body = (new ObjectMapper()).valueToTree(purchase).toString();
        this.mockMvc.perform(post("/purchases")
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(jsonPath("error.status").value(422))
                .andExpect(jsonPath("error.errorCode").value(1004))
                .andExpect(jsonPath("error.message").value("Field 'driverId' is empty"))
                .andDo(print());
    }
}
