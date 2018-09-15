package com.fuelcompany.application;

import org.h2.jdbc.JdbcSQLException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application.properties")
public class ApplicationTests {


    @Test
    @Rollback
    @Transactional
    public void testDatabaseNoMem() throws SQLException {
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
}
