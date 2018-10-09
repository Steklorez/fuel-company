package com.fuelcompany.application;

import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


//tests runner for maven and IDE
@RunWith(JUnitPlatform.class)
//scan test-classes scope for JUnit Jupiter runner
@SelectPackages("com.fuelcompany")
public class SpringTestCase {

    @Autowired
    private WebApplicationContext wac;

    public MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
}
