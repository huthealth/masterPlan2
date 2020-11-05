package com.huttels.service;


import com.huttels.domain.project.Project;
import com.huttels.domain.project.ProjectRepository;
import com.huttels.domain.project.ProjectState;
import com.huttels.domain.user.User;
import com.huttels.domain.userProject.UserProject;
import com.huttels.domain.userProject.UserProjectRepository;
import com.huttels.web.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final UserService userService;



    @Transactional
    public ProjectState getState(Long projectId) {
        Project project = findById(projectId);
        return project.getState();
    }

    @Transactional
    public Project findById(Long projectId){
        return projectRepository.findById(projectId).orElse(null);
    }

    @Transactional
    public void changeState(Long projectId, ProjectState projectState) {
        Project project = findById(projectId);
        project.changeState(projectState);
    }

    @Transactional
    public Project save(Project project) {
        return projectRepository.save(project);
    }
}
