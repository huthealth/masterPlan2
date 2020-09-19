package com.huttels.domain.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepository userRepository;

    @Test
    public void connect(){
        try(Connection connection = dataSource.getConnection()){
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            System.out.println(databaseMetaData.getURL());
            System.out.println(databaseMetaData.getDriverName());
            System.out.println(databaseMetaData.getUserName());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void create(){
        //User user = new User("lck","123","billy@naver.com");
        //User newUser = userRepository.save(user);
        //assertEquals(newUser.getUserId(),"lck");
    }

    @Test
    public void query(){
        User foundUser = userRepository.findByUserId("lck");
        assertEquals(foundUser.getUserId(),"lck");
    }

}