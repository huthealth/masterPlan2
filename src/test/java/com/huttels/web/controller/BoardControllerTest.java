package com.huttels.web.controller;

import com.huttels.service.BoardService;
import com.huttels.service.ProjectService;
import com.huttels.service.UserProjectService;
import com.huttels.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@WebMvcTest(ProjectController.class)
public class BoardControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectService projectService;

    @MockBean
    UserService mockUserService;

    @MockBean
    UserProjectService mockUserProjectService;

    @MockBean
    BoardService boardService;

    @Test
    public void openBoard() throws Exception {

        mockMvc.perform(get("/project/1/boards/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}