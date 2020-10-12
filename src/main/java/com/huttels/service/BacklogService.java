package com.huttels.service;

import com.huttels.domain.BackLog.Backlog;
import com.huttels.domain.BackLog.BacklogRepository;
import com.huttels.domain.project.Project;
import com.huttels.domain.project.ProjectState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class BacklogService {

    private final BacklogRepository backlogRepository;

    private final ProjectService projectService;

    @Transactional
    public boolean saveAll(List<String> backlogs,Long projectId){
        if(backlogs == null || backlogs.isEmpty()) return false;
        Project project = projectService.findById(projectId);
        if(project == null) return false;
        for(int i = 0 ; i< backlogs.size(); i++) {
            Backlog backlog = Backlog.builder().title(backlogs.get(i)).project(project).build();
            backlogRepository.save(backlog);
        }
        projectService.changeState(projectId, ProjectState.TODO);
        return true;
    }
}
