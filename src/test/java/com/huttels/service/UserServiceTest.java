package com.huttels.service;

import com.huttels.domain.user.User;
import com.huttels.domain.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void checkPassword(){
        String password = "123123";
        String hashPw = BCrypt.hashpw(password, BCrypt.gensalt());
        assertEquals(true,BCrypt.checkpw(password,hashPw));
    }

    @Test
    public void loginUser(){
    }


}