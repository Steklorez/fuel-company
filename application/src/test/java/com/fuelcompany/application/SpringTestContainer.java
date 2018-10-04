package com.fuelcompany.application;

import com.fuelcompany.domain.repository.FuelTypeRepositoryTest;
import com.fuelcompany.inftastructure.ConnectionTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringJUnitConfig
@SpringBootTest(classes = {ConnectionTest.class,
        FuelTypeRepositoryTest.class})
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ComponentScan(basePackages = {"com.fuelcompany.application","com.fuelcompany.domain" })
@EntityScan(basePackages = "com.fuelcompany.domain")
@EnableJpaRepositories(basePackages = {"com.fuelcompany.infrastructure"})
public class SpringTestContainer {

    @Autowired
    private WebApplicationContext wac;

    public MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
}
