package com.huttels.service;

import com.huttels.domain.BackLog.Backlog;
import com.huttels.domain.BackLog.BacklogRepository;
import com.huttels.domain.BackLog.BacklogState;
import com.huttels.domain.Todo.Todo;
import com.huttels.domain.Todo.TodoRepository;
import com.huttels.domain.Todo.TodoState;
import com.huttels.domain.project.Project;
import com.huttels.domain.project.ProjectRepository;
import com.huttels.domain.user.User;
import com.huttels.domain.user.UserRepository;
import com.huttels.domain.userProject.UserProject;
import com.huttels.domain.userProject.UserProjectRepository;
import com.huttels.web.dto.TodoDto;
import com.sun.org.apache.bcel.internal.generic.LXOR;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
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

    @Test
    public void reviewTodosTest(){
        Project project = Project.builder().title("project1").content("project1").build();

        projectRepository.save(project);

        Backlog backlog = Backlog.builder().title("backlog1").project(project).build();
        Backlog backlog2 = Backlog.builder().title("backlog2").project(project).build();
        Backlog backlog3 = Backlog.builder().title("backlog3").project(project).build();
        Backlog backlog4 = Backlog.builder().title("backlog4").project(project).build();
        backlog.changeState(BacklogState.DOING);
        backlog2.changeState(BacklogState.DOING);
        backlog3.changeState(BacklogState.DOING);

        backlogRepository.save(backlog);
        backlogRepository.save(backlog2);
        backlogRepository.save(backlog3);
        backlogRepository.save(backlog4);

        Todo todo = Todo.builder().content("todo1").period(5).endDate(LocalDateTime.now()).backlog(backlog).build();
        Todo todo2 = Todo.builder().content("todo2").period(5).endDate(LocalDateTime.now()).backlog(backlog2).build();
        Todo todo3 = Todo.builder().content("todo3").period(5).endDate(LocalDateTime.now()).backlog(backlog2).build();
        Todo todo4 = Todo.builder().content("todo4").period(5).endDate(LocalDateTime.now()).backlog(backlog3).build();

        todo.changeState(TodoState.DONE);
        todo2.changeState(TodoState.DONE);
        todo4.changeState(TodoState.DONE);

        todoRepository.save(todo);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        todoService.reviewTodos(project.getId());

        List<Backlog>  doneBacklogs = backlogRepository.findByStateByProjectId(project.getId(), BacklogState.DONE);

        for(Backlog saveBacklog : doneBacklogs){
            assertThat(saveBacklog.getState()).isEqualTo(BacklogState.DONE);
            assertThat(saveBacklog.getTitle()).isNotEqualTo("backlog2");
            System.out.println(saveBacklog.getTitle());

        }


        List<Backlog> backlogs = backlogRepository.findByProjectId(project.getId());
        for(Backlog saveBacklog : backlogs) {
            List<Todo> todos = todoRepository.findByBacklogId(saveBacklog.getId());
            for (Todo savedTodo : todos) {
                assertThat(savedTodo.getState()).isEqualTo(TodoState.DELETE);
            }
        }

    }

}