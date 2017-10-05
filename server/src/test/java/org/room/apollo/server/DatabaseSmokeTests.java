package org.room.apollo.server;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseSmokeTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment environment;

    @Test
    public void checkThatH2Database_IsOnline_WhileSpringContextIsRunning() throws SQLException {
        String databaseUrl = environment.getProperty("spring.datasource.url").split(";")[0];
        Connection connection = dataSource.getConnection();
        Assert.assertNotNull(connection);
        Assert.assertEquals(databaseUrl, connection.getMetaData().getURL());
    }
}
