package com.huttels.web.controller;

import com.huttels.domain.user.User;
import com.huttels.domain.user.UserRepository;
import com.huttels.service.UserService;
import com.huttels.web.dto.UserLoginRequestDto;
import com.huttels.web.dto.UserSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(UserController.class)
public class UserControllerTest {

  /*  @LocalServerPort
    private int port;*/

    @Autowired
    MockMvc mockMvc;

    @MockBean
   UserService mockUserService;

    /*@Autowired
    private UserRepository userRepository;

    @Autowired
    TestRestTemplate restTemplate;

    @After
    public void terDown() throws Exception {
        userRepository.deleteAll();
    }*/

    @Test
    public void registerForm() throws Exception {


        mockMvc.perform(get("/users/registerForm"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void register() throws Exception {

        mockMvc.perform(post("/users/register"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void loginForm() throws Exception {
        mockMvc.perform(get("/users/loginForm"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void login() throws Exception {
        //when(mockUserService.checkUser(new UserLoginRequestDto())).thenReturn(false);
        mockMvc.perform(post("/users/login"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}