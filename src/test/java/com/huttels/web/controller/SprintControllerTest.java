package com.huttels.web.controller;

import com.huttels.domain.BackLog.Backlog;
import com.huttels.domain.BackLog.BacklogRepository;
import com.huttels.domain.Todo.Todo;
import com.huttels.domain.Todo.TodoRepository;
import com.huttels.domain.project.Project;
import com.huttels.domain.project.ProjectRepository;
import com.huttels.domain.project.ProjectState;
import com.huttels.domain.user.User;
import com.huttels.domain.userProject.UserProjectRepository;
import com.huttels.service.BacklogService;
import com.huttels.service.ProjectService;
import com.huttels.service.UserProjectService;
import com.huttels.service.UserService;
import com.huttels.web.dto.ProjectDto;
import com.huttels.web.dto.UserSaveRequestDto;
import org.junit.After;
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
    private ProjectRepository projectRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserProjectService  userProjectService;

    @Autowired
    private UserService userService;

    private Long projectId;

    private Long backLogId;

    @Before
    public void saveUser(){
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder().nickName("billy104").password("123123").passwordConfirm("123123").build();
        userService.save(userSaveRequestDto);
    }

    @Before
    public void saveProject(){
        ProjectDto projectDto = ProjectDto.builder().title("test").build();
        Long projectId = userProjectService.saveProject("billy104",projectDto);
        this.projectId = projectId;
    }

    @After
    public void deleteAll(){

        userProjectRepository.deleteAll();
        todoRepository.deleteAll();
        backlogRepository.deleteAll();
        projectRepository.deleteAll();

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

    @Test
    public void saveTodos(){

        Backlog backlog = Backlog.builder().title("backlog1").project(projectService.findById(projectId)).build();
        Backlog backlog2 = Backlog.builder().title("backlog2").project(projectService.findById(projectId)).build();
        backlogRepository.save(backlog);
        backlogRepository.save(backlog2);




        Map<String, List<List<String>>> requestData = new HashMap<>();
        List<List<String>> todolist = new ArrayList<>();
        todolist.add(new ArrayList<>());
        todolist.add(new ArrayList<>());
        todolist.get(0).add(Long.toString(backlog.getId()));
        todolist.get(0).add("todo1");
        todolist.get(1).add(Long.toString(backlog2.getId()));
        todolist.get(1).add("todo2");

        requestData.put("result",todolist);

        String url  = "http://localhost:" + port + "/projects/+"+projectId+"/sprint/todos/5";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestData, String.class);

        ProjectState projectState =  projectService.findById(projectId).getState();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("투두 저장 성공");
        assertThat(projectState).isEqualTo(ProjectState.SCRUMBOARD);
        for (Todo todo : todoRepository.findAll()){
            System.out.println("========================================"+todo.getContent());
        }

    }

    @Test
    public void failedToSaveTodos(){

        Backlog backlog = Backlog.builder().title("backlog1").project(projectService.findById(projectId)).build();
        Backlog backlog2 = Backlog.builder().title("backlog2").project(projectService.findById(projectId)).build();
        backlogRepository.save(backlog);
        backlogRepository.save(backlog2);




        Map<String, List<List<String>>> requestData = new HashMap<>();
        List<List<String>> todolist = new ArrayList<>();
        todolist.add(new ArrayList<>());
        todolist.add(new ArrayList<>());
        todolist.add(new ArrayList<>());
        todolist.get(0).add(Long.toString(backlog.getId()));
        todolist.get(0).add("todo1");
        todolist.get(1).add(Long.toString(backlog2.getId()));
        todolist.get(1).add("todo2");
        todolist.get(1).add(Long.toString(100));
        todolist.get(1).add("todo3");

        requestData.put("result",todolist);

        String url  = "http://localhost:" + port + "/projects/+"+projectId+"/sprint/todos/5";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestData, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("투두 저장 실패");
        assertThat(todoRepository.findAll()).isEqualTo(null);

    }



}