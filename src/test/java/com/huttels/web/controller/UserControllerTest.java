package com.huttels.web.controller;

import com.huttels.domain.user.User;
import com.huttels.domain.user.UserRepository;
import com.huttels.web.dto.UserSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    TestRestTemplate restTemplate;

    @After
    public void terDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void validation(){

    }


    @Test
    public void register() {

        UserSaveRequestDto requestDto = UserSaveRequestDto.builder()
                .userId("billy104")
                .password("12341234")
                .passwordConfirm("12341234")
                .email("123@123")
                .build();

        String url  = "http://localhost:"+port +"/users";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getUserId()).isEqualTo("billy104");
        assertThat(all.get(0).getPassword()).isEqualTo("12341234");
    }

}