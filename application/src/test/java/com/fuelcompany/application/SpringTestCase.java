package com.fuelcompany.application;

import com.fuelcompany.domain.repository.FuelTypeRepositoryTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;
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


//tests runner for maven and IDE
@RunWith(JUnitPlatform.class)

//scan test-classes scope for JUnit Jupiter runner
@SelectPackages("com.fuelcompany")

//Need for init nonstatic methods by @BeforeAll for JUnit Jupiter
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

/* Contain:
  - @ExtendWith(SpringExtension.class)" - extensions registrator(SpringExtension.class - extension for integrates Spring TestContext Framework into JUnit Jupiter).
  - @ContextConfiguration - is used to determine how to load and configure an ApplicationContext for integration tests */
@SpringJUnitConfig

//@SpringBootTest - load all needed springboot configuration builders from classes and xml
@SpringBootTest(classes = {FuelTypeRepositoryTest.class})

@ComponentScan(basePackages = {"com.fuelcompany.application", "com.fuelcompany.domain"})
@EntityScan(basePackages = "com.fuelcompany.domain")
@EnableJpaRepositories(basePackages = {"com.fuelcompany.infrastructure"})
@ActiveProfiles("test")
public class SpringTestCase {

    @Autowired
    private WebApplicationContext wac;

    public MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
}
