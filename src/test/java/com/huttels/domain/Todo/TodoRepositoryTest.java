package com.huttels.domain.Todo;

import com.huttels.domain.BackLog.Backlog;
import com.huttels.domain.BackLog.BacklogRepository;
import com.huttels.domain.project.Project;
import com.huttels.domain.project.ProjectRepository;
import com.huttels.domain.user.User;
import com.huttels.domain.user.UserRepository;
import com.huttels.domain.userProject.UserProject;
import com.huttels.domain.userProject.UserProjectRepository;
import com.huttels.web.dto.ProjectDto;
import com.huttels.web.dto.UserSaveRequestDto;
import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TodoRepositoryTest {

    @Autowired TodoRepository todoRepository;

    @Autowired UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserProjectRepository userProjectRepository;
    @Autowired
    BacklogRepository backlogRepository;

    @After
    public void deleteAll(){

        userProjectRepository.deleteAll();
        todoRepository.deleteAll();
        backlogRepository.deleteAll();
        projectRepository.deleteAll();
        userProjectRepository.deleteAll();

    }

    @Test
    public void findAllByBacklogId(){

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
        Todo todo3 = todoRepository.save(todo1);
        Todo todo4 = todoRepository.save(todo2);


        //Todo todo = todoRepository.findById()

        List<Todo> todos = todoRepository.findByBacklogId(backlogId);
        for(Todo todo : todos) {
            assertThat(todo.getBacklog().getId()).isEqualTo(backlogId);
            System.out.println(todo.getId()+" : "+todo.getContent() +" : "+todo.getBacklog().getId());
        }
        assertThat(todos).isNotEmpty();
        assertThat(todos.get(0)).isSameAs(todo1);
        //assertThat(todos.get(1)).isSameAs(todo2);

    }

    @Test
    public void findNotDeleteStateTest(){
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
        todo2.changeState(TodoState.DELETE);
        Todo todo3 = todoRepository.save(todo1);
        Todo todo4 = todoRepository.save(todo2);

        List<Todo> todos = todoRepository.findNotDeleteStateByBacklogId(backlogId);
        assertThat(todos.size()).isEqualTo(1);
    }

}