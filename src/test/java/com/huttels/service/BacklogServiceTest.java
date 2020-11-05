package com.huttels.service;

import com.huttels.domain.BackLog.Backlog;
import com.huttels.domain.BackLog.BacklogRepository;
import com.huttels.domain.BackLog.BacklogState;
import com.huttels.domain.project.Project;
import com.huttels.domain.userProject.UserProject;
import com.huttels.web.dto.BacklogDto;
import com.huttels.web.dto.ProjectDto;
import com.huttels.web.dto.UserSaveRequestDto;
import lombok.Data;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BacklogServiceTest {


    @Autowired
    private BacklogService backlogService;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserProjectService userProjectService;

    @Autowired
    private UserService userService;

    @Test
    public void saveBacklogs(){
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder().nickName("billy104").password("123123").passwordConfirm("123123").build();
        userService.save(userSaveRequestDto);
        ProjectDto projectDto = ProjectDto.builder().title("test").build();
        Long projectId = userProjectService.saveProject("billy104",projectDto);
        List<String> backlogs = new ArrayList<>();
        backlogs.add("hello");
        backlogs.add("hello2");
        backlogService.saveAll(backlogs,projectId);

        List<Backlog> dbBacklogs = backlogRepository.findAll();
        assertThat(dbBacklogs.isEmpty()).isEqualTo(false);
        for(int i = 0 ; i< backlogs.size(); i++) System.out.println(dbBacklogs.get(i).getTitle());
    }

    @Test
    public void findDoingStateBackLogDtoTest(){
        Project project = Project.builder().title("test").content("test").build();
        projectService.save(project);

        Backlog backlog = Backlog.builder().title("test").project(project).build();
        Backlog backlog2 = Backlog.builder().title("test2").project(project).build();
        Backlog backlog3 = Backlog.builder().title("test3").project(project).build();
        Backlog backlog4 = Backlog.builder().title("test4").project(project).build();

        backlog.changeState(BacklogState.DOING);
        backlog2.changeState(BacklogState.DOING);

        Backlog sb = backlogRepository.save(backlog);
        Backlog sb2 =backlogRepository.save(backlog2);
        Backlog sb3 =backlogRepository.save(backlog3);
        Backlog sb4 =backlogRepository.save(backlog4);

        assertThat(sb.getState()).isEqualTo(BacklogState.DOING);
        assertThat(sb2.getState()).isEqualTo(BacklogState.DOING);
        assertThat(sb3.getState()).isEqualTo(BacklogState.TODO);
        assertThat(sb4.getState()).isEqualTo(BacklogState.TODO);

        List<BacklogDto> doingBacklogDtos = backlogService.findDoingStateBackLogDto(project.getId());
        assertThat(doingBacklogDtos.size()).isEqualTo(2);
        for(BacklogDto bd : doingBacklogDtos){
            assertThat(bd.getTitle()).isNotEqualTo("test3");
            System.out.println(bd.getTitle());
        }
    }

}