package com.fuelcompany.application;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuelcompany.infrastructure.api.registration.ApiPurchase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class RegistrationTest extends SpringTestContainer {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    @Rollback
    public void registrationTest() throws Exception {
        ApiPurchase purchase = new ApiPurchase("D", new BigDecimal("3.25"), 1L, LocalDate.of(2000, 10, 19));
        String body = (new ObjectMapper()).valueToTree(purchase).toString();
        this.mockMvc.perform(post("/purchases")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("fuelType").value("D"))
                .andExpect(jsonPath("price").value(3.25))
                .andExpect(jsonPath("driverId").value(1))
                .andExpect(jsonPath("date").value("2000-10-19"))
                .andDo(print());
    }


    @Test
    @Rollback
    @Transactional
    public void registrationErrors_1001_Test() throws Exception {
        ApiPurchase purchase = new ApiPurchase("D", new BigDecimal("3.25"), 1L, null);
        String body = (new ObjectMapper()).valueToTree(purchase).toString();
        this.mockMvc.perform(post("/purchases")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("error.status").value(422))
                .andExpect(jsonPath("error.errorCode").value(1001))
                .andExpect(jsonPath("error.message").value("Field 'date' is empty"))
                .andDo(print());
    }

    @Test
    @Rollback
    @Transactional
    public void registrationErrors_1002_Test() throws Exception {
        ApiPurchase purchase = new ApiPurchase(null, new BigDecimal("3.25"), 1L, LocalDate.of(2000, 10, 19));
        String body = (new ObjectMapper()).valueToTree(purchase).toString();
        this.mockMvc.perform(post("/purchases")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("error.status").value(422))
                .andExpect(jsonPath("error.errorCode").value(1002))
                .andExpect(jsonPath("error.message").value("Field 'fuelType' is empty"))
                .andDo(print());
    }

    @Test
    @Rollback
    @Transactional
    public void registrationErrors_1003_Test() throws Exception {
        ApiPurchase purchase = new ApiPurchase("D", null, 1L, LocalDate.of(2000, 10, 19));
        String body = (new ObjectMapper()).valueToTree(purchase).toString();
        this.mockMvc.perform(post("/purchases")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("error.status").value(422))
                .andExpect(jsonPath("error.errorCode").value(1003))
                .andExpect(jsonPath("error.message").value("Field 'price' is empty"))
                .andDo(print());
    }

    @Test
    @Rollback
    @Transactional
    public void registrationErrors_1004_Test() throws Exception {
        ApiPurchase purchase = new ApiPurchase("D", new BigDecimal("3.25"), null, LocalDate.of(2000, 10, 19));
        String body = (new ObjectMapper()).valueToTree(purchase).toString();
        this.mockMvc.perform(post("/purchases")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("error.status").value(422))
                .andExpect(jsonPath("error.errorCode").value(1004))
                .andExpect(jsonPath("error.message").value("Field 'driverId' is empty"))
                .andDo(print());
    }

    @Test
    @Rollback
    @Transactional
    public void test() throws Exception {
        Assert.assertEquals(0,((Number)entityManager.createQuery("SELECT count(*) FROM PurchaseEntity p").getSingleResult()).intValue());
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/purchases/file")
                .file("file", Files.readAllBytes(new File("src/test/resources/multipart.json").toPath()))
                .param("name", "multipart.json")
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        )
                .andExpect(status().isCreated());

        Assert.assertEquals(3,((Number)entityManager.createQuery("SELECT count(*) FROM PurchaseEntity p").getSingleResult()).intValue());
    }
}
