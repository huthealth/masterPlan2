package com.huttels.service;

import com.huttels.domain.BackLog.Backlog;
import com.huttels.domain.BackLog.BacklogRepository;
import com.huttels.domain.userProject.UserProject;
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

    private Long projectId;

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

    @Test
    public void saveBacklogs(){
        List<String> backlogs = new ArrayList<>();
        backlogs.add("hello");
        backlogs.add("hello2");
        backlogService.saveAll(backlogs,projectId);

        List<Backlog> dbBacklogs = backlogRepository.findAll();
        Assertions.assertThat(dbBacklogs.isEmpty()).isEqualTo(false);
        for(int i = 0 ; i< backlogs.size(); i++) System.out.println(dbBacklogs.get(i).getTitle());
    }

}