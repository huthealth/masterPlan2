package com.huttels.domain.BackLog;

import com.huttels.domain.project.Project;
import com.huttels.domain.project.ProjectRepository;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.Data;
import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaAuditing
public class BacklogRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    BacklogRepository backlogRepository;


    @Test
    public void findDoingStateByProjectIdTest() {
        Project project = Project.builder().title("project1").content("project1").build();
        Project project2 = Project.builder().title("project2").content("project2").build();
        Project savedProject = projectRepository.save(project);
        projectRepository.save(project2);

        assertThat(project.getId()).isEqualTo(savedProject.getId());

        Backlog backlog = Backlog.builder().title("backlog1").project(project).build();
        Backlog backlog2 = Backlog.builder().title("backlog2").project(project).build();
        Backlog backlog3 = Backlog.builder().title("backlog3").project(project2).build();
        Backlog backlog4 = Backlog.builder().title("backlog4").project(project).build();
        backlog.changeState(BacklogState.DOING);
        backlog2.changeState(BacklogState.DONE);
        //backlog3.changeState(BacklogState.DOING);

        backlogRepository.save(backlog);
        backlogRepository.save(backlog2);
        backlogRepository.save(backlog3);
        backlogRepository.save(backlog4);

        /*List<Backlog> allBacklogs = backlogRepository.findAll();
        for(Backlog foundBacklog : allBacklogs) {
            System.out.println(foundBacklog.getTitle()+" "+foundBacklog.getState() + " " + foundBacklog.getProject().getId());
        }*/

        List<Backlog> doingStateBacklogs = backlogRepository.findByStateByProjectId(project.getId(),BacklogState.DOING);

        assertThat(doingStateBacklogs.size()).isEqualTo(1);
        for(Backlog foundBacklog : doingStateBacklogs) {
            assertThat(foundBacklog.getState()).isEqualTo(BacklogState.DOING);
        }
    }
}