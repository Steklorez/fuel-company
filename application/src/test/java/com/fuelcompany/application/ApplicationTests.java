package com.fuelcompany.application;

import com.fuelcompany.application.controller.RegistrationController;
import com.fuelcompany.infrastructure.api.registration.Record;
import com.fuelcompany.infrastructure.api.registration.RegistrationResponse;
import org.h2.jdbc.JdbcSQLException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application.properties")
public class ApplicationTests {

    @Autowired
    private RegistrationController reportController;

    @Test
    @Rollback
    @Transactional
    public void testDatabaseMem() throws SQLException {
        testDatabase("jdbc:h2:mem:./h2mem");
    }

    private void testDatabase(String url) throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        Statement s = connection.createStatement();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("select * from PERSON");
        } catch (JdbcSQLException e) {
            System.out.println(e.getMessage());
        }
        Assert.assertNull(ps);

        s.execute("CREATE TABLE PERSON (ID INT PRIMARY KEY, FIRSTNAME VARCHAR(64), LASTNAME VARCHAR(64))");
        ps = connection.prepareStatement("select * from PERSON");
        ResultSet r = ps.executeQuery();
        Assert.assertFalse(r.next());

        s.execute("INSERT INTO PERSON(ID,FIRSTNAME, LASTNAME) VALUES (1,'123132','123123')");
        ps = connection.prepareStatement("select * from PERSON");
        r = ps.executeQuery();
        Assert.assertTrue(r.next());
        r.close();
        ps.close();
        s.close();
        connection.close();
    }

    @Test
    public void registrationErrorsTest() {
        RegistrationResponse registrateResult = reportController.registrate(null);
        assertError(registrateResult, 1000, "Incoming resource is null");

        Record record = new Record();
        registrateResult = reportController.registrate(record);
        assertError(registrateResult, 1050, "Field 'date' is empty");

        record.setDate(LocalDate.now());
        registrateResult = reportController.registrate(record);
        assertError(registrateResult, 1051, "Field 'fuelType' is empty");

        record.setFuelType("D-failed-fuel-type");
        registrateResult = reportController.registrate(record);
        assertError(registrateResult, 1052, "Field 'price' is empty");

        record.setPrice(new BigDecimal("3.27"));
        registrateResult = reportController.registrate(record);
        assertError(registrateResult, 1053, "Field 'driverId' is empty");

        record.setDriverId(1L);
        registrateResult = reportController.registrate(record);
        assertError(registrateResult, 1054, "Wrong fuel type");
    }

    private void assertError(RegistrationResponse registrateResult, int code, String messge) {
        Assert.assertNotNull(registrateResult);
        Assert.assertNull(registrateResult.getRecord());
        Assert.assertNotNull(registrateResult.getError());
        Assert.assertEquals(code, registrateResult.getError().getCode());
        Assert.assertEquals(messge, registrateResult.getError().getMessage());
    }

    @Test
    public void addRecord() {
        long driverId = 1L;
        LocalDate now = LocalDate.now();
        Record record = new Record();
        BigDecimal price = new BigDecimal("3.27");
        String fuelType = "D";

        record.setDate(now);
        record.setPrice(price);
        record.setDriverId(driverId);
        record.setFuelType(fuelType);
        RegistrationResponse registrateResult = reportController.registrate(record);
        Assert.assertNotNull(registrateResult);
        Assert.assertNull(registrateResult.getError());
        Assert.assertNotNull(registrateResult.getRecord());
        Assert.assertNotNull(registrateResult.getRecord().getId());
        Assert.assertNotNull(registrateResult.getRecord().getDate());
        Assert.assertEquals(now,registrateResult.getRecord().getDate());
        Assert.assertNotNull(registrateResult.getRecord().getDriverId());
        Assert.assertEquals(driverId,registrateResult.getRecord().getDriverId().longValue());
        Assert.assertNotNull(registrateResult.getRecord().getFuelType());
        Assert.assertEquals(fuelType,registrateResult.getRecord().getFuelType());
        Assert.assertNotNull(registrateResult.getRecord().getPrice());
        Assert.assertEquals(price,registrateResult.getRecord().getPrice());
    }
}
