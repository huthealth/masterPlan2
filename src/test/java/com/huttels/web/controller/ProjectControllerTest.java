package com.huttels.web.controller;

import com.huttels.domain.user.User;
import com.huttels.domain.userProject.UserProject;
import com.huttels.service.ProjectService;
import com.huttels.service.UserProjectService;
import com.huttels.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

   @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectService projectService;

    @MockBean
    UserService mockUserService;

    @MockBean
    UserProjectService mockUserProjectService;

    @Test
    public void projectMainTest() throws Exception {

        when(mockUserService.checkLogin(any())).thenReturn(true);
        when(mockUserProjectService.isMatched(any(),any())).thenReturn(true);
        mockMvc.perform(get("/projects/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void searchUser() throws Exception {
        when(mockUserService.checkLogin(any())).thenReturn(true);
        mockMvc.perform(get("/projects/1/searchUser"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void addProjectMember() throws Exception {
        when(mockUserService.checkLogin(any())).thenReturn(true);
        when(mockUserProjectService.isMatched(any(),any())).thenReturn(true);
        when(mockUserService.findUserByNickName("billy104")).thenReturn(new User());
        mockMvc.perform(get("/projects/1/foundUser?userName=billy104"))
                .andExpect(status().isOk())
                .andDo(print());
    }





}