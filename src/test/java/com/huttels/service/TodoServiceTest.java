package com.huttels.service;

import com.huttels.domain.BackLog.Backlog;
import com.huttels.domain.BackLog.BacklogRepository;
import com.huttels.domain.Todo.Todo;
import com.huttels.domain.Todo.TodoRepository;
import com.huttels.domain.Todo.TodoState;
import com.huttels.domain.project.Project;
import com.huttels.domain.project.ProjectRepository;
import com.huttels.domain.user.User;
import com.huttels.domain.user.UserRepository;
import com.huttels.domain.userProject.UserProject;
import com.huttels.domain.userProject.UserProjectRepository;
import com.sun.org.apache.bcel.internal.generic.LXOR;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoServiceTest {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserProjectRepository userProjectRepository;
    @Autowired
    BacklogRepository backlogRepository;

    @Autowired
    TodoService todoService;

    @After
    public void deleteAll(){

        userProjectRepository.deleteAll();
        todoRepository.deleteAll();
        backlogRepository.deleteAll();
        projectRepository.deleteAll();
        userProjectRepository.deleteAll();

    }

    @Before
    public void saveAll(){
        User user = User.builder().nickName("billy104").password("123123").build();
        userRepository.save(user);
        Project project = Project.builder().title("project").build();
        projectRepository.save(project);
        UserProject userProject = UserProject.builder().project(project).user(user).build();
        userProjectRepository.save(userProject);
        Backlog backlog = Backlog.builder().project(project).title("backlog").build();
        backlogRepository.save(backlog);
        Long backlogId = backlog.getId();
        Todo todo1 = Todo.builder().backlog(backlog).period(5).content("todo1").build();
        Todo todo2 = Todo.builder().backlog(backlog).period(5).content("todo2").build();
        todoRepository.save(todo1);
        todoRepository.save(todo2);
    }

    @Test
    public void changAllTodoState(){

        List<Map<String, String>> result = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        Map<String,String> map2 = new HashMap<>();

        map.put("id","1");
        map.put("state","DONE");

        map2.put("id","2");
        map2.put("state","DONE");
        result.add(map);
        result.add(map2);
        todoService.changeAllState(result);

        List<Todo> todos = todoRepository.findAll();

        for(Todo todo : todos){
            System.out.println(todo.getId());
            assertThat(todo.getState()).isEqualTo(TodoState.DONE);
        }
    }

}