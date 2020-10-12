package com.huttels.web.controller;

import com.huttels.domain.BackLog.Backlog;
import com.huttels.domain.BackLog.BacklogRepository;
import com.huttels.domain.project.Project;
import com.huttels.domain.user.User;
import com.huttels.service.BacklogService;
import com.huttels.service.ProjectService;
import com.huttels.service.UserService;
import com.huttels.web.dto.ProjectDto;
import com.huttels.web.dto.UserSaveRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class SprintControllerTest {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Autowired
    private BacklogService backlogService;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    private Long projectId;

    @Before
    public void saveUser(){
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder().nickName("billy104").password("123123").passwordConfirm("123123").build();
        userService.save(userSaveRequestDto);
    }

    @Before
    public void saveProject(){
        ProjectDto projectDto = ProjectDto.builder().title("test").build();
        Long projectId = projectService.save("billy104",projectDto);
        this.projectId = projectId;
    }

    @Test
    public void saveBacklogs(){

        Map<String, List<String>> backlogMap = new HashMap<>();
        List<String> titles = new ArrayList<>();
        titles.add("hello");
        backlogMap.put("result", titles );
        String url  = "http://localhost:" + port + "/projects/+"+projectId+"/sprint/backlogs";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, backlogMap, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("백로그 저장 성공");

        List<Backlog> backlogs = backlogRepository.findAll();
        for(Backlog backlog : backlogs){
            assertThat(backlog.getTitle()).isEqualTo("hello");
        }

    }
    
}